<?php
include('dbconnection.php');
$product=$_GET['product'];
$email=$_GET['email'];
$qry="select * from registereduser where email='$email'";
$result=mysqli_query($conn,$qry);
	if($result && mysqli_num_rows($result)==1)
	{
		$row=mysqli_fetch_assoc($result);
		$password=$row["password"];
		$fname=$row['fname'];
		$lname=$row['lname'];
		$contact=$row['contact'];
	}
	else
	{
		header( "refresh:1;url=../home.html");
		echo "<script type='text/javascript'>alert('Email ID not registered. Please enter registered email ID or register now!');</script>";
	}
$qry="select * from workshopamt where wid='$product'";
$result=mysqli_query($conn,$qry);
	if($result && mysqli_num_rows($result)==1)
	{
		$row=mysqli_fetch_assoc($result);
		$amount=$row["amount"];
    }
    else
	{
		header( "refresh:1;url=portal.php");
		echo "<script type='text/javascript'>alert('Error! Try again later');</script>";
	}
$SUCCESS_URL = "http://cb.csmit.org/portal/success.php";
$FAILURE_URL = "http://cb.csmit.org/portal/failure.php";
$MERCHANT_KEY = "kAvznGn9";
$SALT = "0TlvuTUIaG";
// Merchant Key and Salt as provided by Payu.

//$PAYU_BASE_URL = "https://sandboxsecure.payu.in";		// For Sandbox Mode
$PAYU_BASE_URL = "https://secure.payu.in";			// For Production Mode

$action = '';

$posted = array();

if(!empty($_POST)) {
    //print_r($_POST);
  foreach($_POST as $key => $value) {    
    $posted[$key] = $value; 
	
  }
}

$posted['amount']=$amount;
$posted['productinfo']=$product;
$posted['firstname']=$fname;
$posted['email']=$email;
$posted['phone']=$contact;
$posted['surl']=$SUCCESS_URL;
$posted['furl']=$FAILURE_URL;
$posted['key']=$MERCHANT_KEY;
$posted['txnid']='';
$posted['hash']='';
$posted['service_provider']='payu_paisa';
$formError = 0;

if(empty($posted['txnid'])) {
  // Generate random transaction id
  $txnid = substr(hash('sha256', mt_rand() . microtime()), 0, 20);
  $posted['txnid'] = $txnid;
} else {
  $txnid = $posted['txnid'];
}
$hash = '';
// Hash Sequence
$hashSequence = "key|txnid|amount|productinfo|firstname|email|udf1|udf2|udf3|udf4|udf5|udf6|udf7|udf8|udf9|udf10";
if(empty($posted['hash']) && sizeof($posted) > 0) {
  if(
          empty($posted['key'])
          || empty($posted['txnid'])
          || empty($posted['amount'])
          || empty($posted['firstname'])
          || empty($posted['email'])
          || empty($posted['phone'])
          || empty($posted['productinfo'])
          || empty($posted['surl'])
          || empty($posted['furl'])
          || empty($posted['service_provider'])
  ) {
    $formError = 1;
  } else {
    //$posted['productinfo'] = json_encode(json_decode('[{"name":"tutionfee","description":"","value":"500","isRequired":"false"},{"name":"developmentfee","description":"monthly tution fee","value":"1500","isRequired":"false"}]'));
	$hashVarsSeq = explode('|', $hashSequence);
    $hash_string = '';	
	foreach($hashVarsSeq as $hash_var) {
      $hash_string .= isset($posted[$hash_var]) ? $posted[$hash_var] : '';
      $hash_string .= '|';
    }

    $hash_string .= $SALT;


    $hash = strtolower(hash('sha512', $hash_string));
    $action = $PAYU_BASE_URL . '/_payment';
  }
} elseif(!empty($posted['hash'])) {
  $hash = $posted['hash'];
  $action = $PAYU_BASE_URL . '/_payment';
}
?>
<html>
  <head>
  <script>
    var hash = '<?php echo $hash ?>';
    function submitPayuForm() {
      if(hash == '') {
        return;
      }
      var payuForm = document.forms.payuForm;
      payuForm.submit();
    }
  </script>
  </head>
  <body onload="submitPayuForm()">
    <h2>PayU Form</h2>
    <br/>
    <?php if($formError) {
	      if(empty($posted['key']))
	            echo "Key empty";      
          if(empty($posted['txnid']))
          	            echo "Txnid empty";      
          if(empty($posted['amount']))
	            echo "amt empty";      
          if(empty($posted['firstname']))
	            echo "name empty";      
          if(empty($posted['email']))
	            echo "email empty";      
          if(empty($posted['phone']))
	            echo "phone empty";      
          if(empty($posted['productinfo']))
	            echo "pdt empty";      
          if(empty($posted['surl']))
	            echo "surl empty";      
          if(empty($posted['furl']))
	            echo "furl empty";      
    } ?>
    <form action="<?php echo $action; ?>" method="post" name="payuForm" id="payuForm">
      <input type="hidden" name="key" value="<?php echo $MERCHANT_KEY ?>" />
      <input type="hidden" name="hash" value="<?php echo $hash ?>"/>
      <input type="hidden" name="txnid" value="<?php echo $txnid ?>" />
      <table>
        <tr>
          <td><b>Mandatory Parameters</b></td>
        </tr>
        <tr>
          <td>Amount: </td>
          <td><input name="amount" value="<?php echo (empty($posted['amount'])) ? '' : $posted['amount'] ?>" /></td>
          <td>First Name: </td>
          <td><input name="firstname" id="firstname" value="<?php echo (empty($posted['firstname'])) ? '' : $posted['firstname']; ?>" /></td>
        </tr>
        <tr>
          <td>Email: </td>
          <td><input name="email" id="email" value="<?php echo (empty($posted['email'])) ? '' : $posted['email']; ?>" /></td>
          <td>Phone: </td>
          <td><input name="phone" value="<?php echo (empty($posted['phone'])) ? '' : $posted['phone']; ?>" /></td>
        </tr>
        <tr>
          <td>Product Info: </td>
          <td colspan="3"><textarea name="productinfo"><?php echo (empty($posted['productinfo'])) ? '' : $posted['productinfo'] ?></textarea></td>
        </tr>
        <tr>
          <td>Success URI: </td>
          <td colspan="3"><input name="surl" value="<?php echo (empty($posted['surl'])) ? '' : $posted['surl'] ?>" size="64" /></td>
        </tr>
        <tr>
          <td>Failure URI: </td>
          <td colspan="3"><input name="furl" value="<?php echo (empty($posted['furl'])) ? '' : $posted['furl'] ?>" size="64" /></td>
        </tr>

        <tr>
          <td colspan="3"><input type="hidden" name="service_provider" value="payu_paisa" size="64" /></td>
        </tr>
        <tr>
          <?php if(!$hash) { ?>
            <td colspan="4"><input type="submit" value="Submit" /></td>
          <?php } ?>
        </tr>
      </table>
    </form>
  </body>
</html>
