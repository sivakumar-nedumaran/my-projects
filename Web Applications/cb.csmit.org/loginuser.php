<?php
	session_start();
	include('dbconnection.php');
	$email=$_POST['Email'];
	$password=$_POST['Password'];
	$_SESSION['email']=$email;
	$qry="select * from registereduser where email='$email' and password='$password' LIMIT 1";
	$result=mysqli_query($conn,$qry);
	if($result && mysqli_num_rows($result)>0)
	{
		setcookie('email', $email, time() + (86400 * 60), "/");
		header( "refresh:1;url=portal/portal.php" );
		echo "<script type='text/javascript'>alert('You have been logged in successfully! Please wait');</script>";
	}
	else
	{
		echo "Failed to Login";
	}
?>
