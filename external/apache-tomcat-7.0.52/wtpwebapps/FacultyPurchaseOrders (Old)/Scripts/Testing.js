
function Testing()
{
	
	}


function writeNow() {

	document.write("<h1>Hello world</h1>");

}

function testValidEntry()
{
	orderForm = document.PurchaseRequest;
	if ( (orderForm.name.value == "") || ( orderForm.email.value == "" ) ||
		 (orderForm.accountNum.value == "") || ( orderForm.vendor.value == "" )	||
		 (orderForm.comment.value == "") || ( orderForm.cost.value == "" )	)
		{
		alert("Please fill in all required (*) fields");
		return false;
		}
	else
		return true;
}