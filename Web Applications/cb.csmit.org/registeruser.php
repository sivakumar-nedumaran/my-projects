<?php
	include('dbconnection.php');
	$fname=$_POST['Firstname'];
	$lname=$_POST['Lastname'];
	$gender=$_POST['Gender'];
	$colg=$_POST['College'];
	$year=$_POST['Year'];
	$dept=$_POST['Department'];
	$degree=$_POST['Degree'];
	$contact=$_POST['Contact'];
	$email=$_POST['Email'];
	$password=$_POST['Password'];
	$qry="select count from count";
	$result=mysqli_query($conn,$qry);
	if($result && mysqli_num_rows($result)>0)
	{
		$row=mysqli_fetch_assoc($result);
		$counter=$row["count"];
		$counter=$counter+1;
		$qry1="update count set count='$counter'";
		$result=mysqli_query($conn,$qry1);
		$cbid="CB".$counter;
		if(mysqli_affected_rows($conn)<=0)
		{
			echo "Failed to Register 1";
		}
		else
		{
			$qry2="insert into registereduser values('$cbid','$fname','$lname','$gender','$colg','$year','$dept','$degree',$contact,'$email','$password')";
			$result=mysqli_query($conn,$qry2);
			if($result)
			{
				
				$msg = "Hello '$name', Thanks for registering. your event id is ".$cbid;
				$msg = wordwrap($msg,70);
				mail($email,"Registration for CarteBlanche 18",$msg);
				header( "refresh:1;url=home.html" );
				echo "<script type='text/javascript'>alert('You have been signed up! Please note your CB ID: ".$cbid.". You can log in now');</script>";
			}
			else
			{
				echo "Failed to Register 2";
			}
		}
		
	}
	else
	{
		echo "Failed to meet requirements";
	}
?>