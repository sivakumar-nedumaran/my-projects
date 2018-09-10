<?php
	include('dbconnection.php');
	$email=$_POST['email'];
	$qry="insert into subscribelist values('$email')";
	$result=mysqli_query($conn,$qry);
	if($result && mysqli_num_rows($result)>0)
	{
		echo json_encode("success");
		echo "<script type='text/javascript'>alert('You have been subscribed to our mail list!');</script>";
	}
	else
	{
		echo "Failed";
	}
	echo "<script type='text/javascript'>alert('You have been subscribed to our mail list!');</script>";
?>