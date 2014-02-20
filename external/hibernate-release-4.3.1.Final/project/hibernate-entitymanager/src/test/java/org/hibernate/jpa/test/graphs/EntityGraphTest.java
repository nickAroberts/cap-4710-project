/*
 * Hibernate, Relational Persistence for Idiomatic Java
 *
 * Copyright (c) 2013, Red Hat Inc. or third-party contributors as
 * indicated by the @author tags or express copyright attribution
 * statements applied by the authors.  All third-party contributions are
 * distributed under license by Red Hat Inc.
 *
 * This copyrighted material is made available to anyone wishing to use, modify,
 * copy, or redistribute it subject to the terms and conditions of the GNU
 * Lesser General Public License, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this distribution; if not, write to:
 * Free Software Foundation, Inc.
 * 51 Franklin Street, Fifth Floor
 * Boston, MA  02110-1301  USA
 */
package org.hibernate.jpa.test.graphs;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Subgraph;

import org.hibernate.Hibernate;
import org.hibernate.jpa.test.BaseEntityManagerFunctionalTestCase;
import org.hibernate.testing.TestForIssue;
import org.junit.Test;

/**
 * @author Christian Bauer
 * @author Brett Meyer
 */
public class EntityGraphTest extends BaseEntityManagerFunctionalTestCase {

	@Override
	protected Class<?>[] getAnnotatedClasses() {
		return new Class[] { Foo.class, Bar.class, Baz.class,
				Company.class, Employee.class, Manager.class, Location.class };
	}

	@Test
	@TestForIssue(jiraKey = "HHH-8857")
	public void loadMultipleAssociations() {
		EntityManager em = getOrCreateEntityManager();
		em.getTransaction().begin();

		Bar bar = new Bar();
		em.persist( bar );

		Baz baz = new Baz();
		em.persist( baz );

		Foo foo = new Foo();
		foo.bar = bar;
		foo.baz = baz;
		em.persist( foo );

		em.getTransaction().commit();
		em.clear();

		em.getTransaction().begin();

		EntityGraph<Foo> fooGraph = em.createEntityGraph( Foo.class );
		fooGraph.addAttributeNodes( "bar", "baz" );

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put( "javax.persistence.loadgraph", fooGraph );

		Foo result = em.find( Foo.class, foo.id, properties );

		assertTrue( Hibernate.isInitialized( result ) );
		assertTrue( Hibernate.isInitialized( result.bar ) );
		assertTrue( Hibernate.isInitialized( result.baz ) );

		em.getTransaction().commit();
		em.close();
	}

	/**
	 * JPA 2.1 spec: "Add a node to the graph that corresponds to a managed type with inheritance. This allows for
	 * multiple subclass subgraphs to be defined for this node of the entity graph. Subclass subgraphs will
	 * automatically include the specified attributes of superclass subgraphs."
	 */
	@Test
	@TestForIssue(jiraKey = "HHH-8640")
	public void inheritanceTest() {
		EntityManager em = getOrCreateEntityManager();
		em.getTransaction().begin();

		Manager manager = new Manager();
		em.persist( manager );
		Employee employee = new Employee();
		employee.friends.add( manager );
		employee.managers.add( manager );
		em.persist( employee );
		Company company = new Company();
		company.employees.add( employee );
		company.employees.add( manager );
		em.persist( company );

		em.getTransaction().commit();
		em.clear();

		em.getTransaction().begin();

		EntityGraph<Company> entityGraph = em.createEntityGraph( Company.class );
		Subgraph<Employee> subgraph = entityGraph.addSubgraph( "employees" );
		subgraph.addAttributeNodes( "managers" );
		subgraph.addAttributeNodes( "friends" );
		Subgraph<Manager> subSubgraph = subgraph.addSubgraph( "managers", Manager.class );
		subSubgraph.addAttributeNodes( "managers" );
		subSubgraph.addAttributeNodes( "friends" );

		Map<String, Object> properties = new HashMap<String, Object>();
		properties.put( "javax.persistence.loadgraph", entityGraph );

		Company result = em.find( Company.class, company.id, properties );

		assertTrue( Hibernate.isInitialized( result ) );
		assertTrue( Hibernate.isInitialized( result.employees ) );
		assertEquals( result.employees.size(), 2 );
		for (Employee resultEmployee : result.employees) {
			assertTrue( Hibernate.isInitialized( resultEmployee.managers ) );
			assertTrue( Hibernate.isInitialized( resultEmployee.friends ) );
		}

		em.getTransaction().commit();
		em.close();
	}

	@Entity
	private static class Foo {

		@Id
		@GeneratedValue
		public Integer id;

		@ManyToOne(fetch = FetchType.LAZY)
		public Bar bar;

		@ManyToOne(fetch = FetchType.LAZY)
		public Baz baz;
	}

	@Entity
	private static class Bar {

		@Id
		@GeneratedValue
		private Integer id;
	}

	@Entity
	private static class Baz {

		@Id
		@GeneratedValue
		private Integer id;
	}

}
