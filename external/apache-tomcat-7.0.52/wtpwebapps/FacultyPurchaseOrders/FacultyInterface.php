<?php




?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en-US" lang="en-US">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Faculty Purchase Requests</title> 
    <link rel="STYLESHEET" type="text/css" href="css/orderform.css" /> 
    <script type='text/javascript' src='scripts/Testing.js'></script>
        
</head>

<body>

		

<!-- Form Code Start -->
<form>
<fieldset>

<legend>Factulty Purchase Request</legend>


	
<div class='container'>
    <label for='name' >Requestor Name: </label><br/>
    <input type='text' name='name' id='name' size ="50" /> <br/>
</div>		

<div class='container'>
	<label for='email' >Requestor E-mail: </label><br/>
	<input type="text" name="email" size = "40"/>	<br/>
</div>

<div class='container'>
	<label for='accountNum' >Account: </label><br/>
	<input type="text" name="accountNum" size = "30"/>	<br/>
</div>
		
<div class='container'>
	<label for='isUrgent' >Check If Urgent: </label><br/>
	<input type = "checkbox" id = "isUrgent" name = "isUrgent"/>	<br/>
</div>

<div class='container'>
	<label for='isComputer' >Computer/Laptop? </label><br/>
	<input type = "checkbox" id = "isComputer" name = "isComputer"/>	<br/>
</div>

<div class='container'>
	<label for='vendor' >Vendor: </label><br/>
	<input type = "text" id = "vendor" name = "vendor"/>	<br/>
</div>

<div class='container'>
	<label for='comment' >Item Description (Include Quantities)  </label><br/>
	<textarea name="comment" id = "comment" rows="5" cols="40" ></textarea><br/>
</div>

<div class='container'>
	<label for='cost' >Total Purchase Amount: $</label><br/>
	<input type = "text" id = "cost" name = "cost"/><br/>
</div>

<div class='container'>
	<label for='file' >Attach File: </label><br/>
	<input type="file" name="file" id="file"/><br/>
</div>

<img id="captcha" src="/securimage/securimage_show.php" alt="CAPTCHA Image" />
	
<div class='container'>
	<label for ='captcha'>Enter Input Text to Validate: </label><br/>
	<input name='captcha' id = 'captcha' type="text"/>	<br/>
</div>
	
<div class='container'>	
	<input type="submit" name="submit" value="Submit"/><br/>
</div>
		
</fieldset>	
</form>

<script type='text/javascript'>
// <![CDATA[

    var here  = new writeNow();
    
    
// ]]>
</script>
	
		
</body>
</html>