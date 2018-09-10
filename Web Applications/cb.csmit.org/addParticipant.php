<?php 

 if($_SERVER['REQUEST_METHOD']=='POST')
{
 
 //Getting values
 $qrid = $_POST['qrid'];
 $cbid = $_POST['cbid']; 
 //Creating an sql query
 $sql = "INSERT INTO participants (qrid,cbid) VALUES ($qrid,'$cbid')";
 
 //Importing our db connection script
 require_once('dbConnect.php');
 //Executing query to database
 if(mysqli_query($con,$sql)){
 echo 'Registered Successfully';
 }else{
 echo 'Sorry! Could not register';
 }
 
 //Closing the database 
 mysqli_close($con);

 }