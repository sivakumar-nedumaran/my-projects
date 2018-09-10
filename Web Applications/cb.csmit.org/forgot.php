<?php
	include('dbconnection.php');
	$email=$_GET['email'];
	$qry="select * from registereduser where email='$email'";
	$result=mysqli_query($conn,$qry);
	if($result && mysqli_num_rows($result)==1)
	{
		$row=mysqli_fetch_assoc($result);
		$password=$row["password"];
		$fname=$row['fname'];
		$lname=$row['lname'];
		$msg = "Greetings $fname $lname, Your password for CB registration is '$password' . You can try logging in now.";
				$msg = wordwrap($msg,80);
				mail($email,"Forget Password for Carte Blanche 18 Portal",$msg);
				header( "refresh:1;url=home.html" );
				echo "<script type='text/javascript'>alert('We have sent you an email with your password. Please login using the received password.');</script>";		
	}
	else
	{
		header( "refresh:1;url=home.html" );
		echo "<script type='text/javascript'>alert('Email ID not registered. Please enter registered email ID or register now!');</script>";
	}
?>