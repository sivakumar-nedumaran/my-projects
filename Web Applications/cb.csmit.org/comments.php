<?php
	$name=$_POST['Name'];
	$email=$_POST['Email'];
	$phone=$_POST['Phone Number'];
	$comment=$_POST['Message'];
	$msg = "Name : ".$name."\nEmail : ". $email."\nPhone number : ". $phone . "\nmessage : ".$comment;
	mail("cbqueries@csmit.org","CB participant message",$msg);
	header( "refresh:2;url=home.html" );
	echo "<script type='text/javascript'>alert('Your query has been sent! Please await reply');</script>";
?>
