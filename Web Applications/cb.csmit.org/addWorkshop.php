<?php 

 if($_SERVER['REQUEST_METHOD']=='POST')
{
 
 //Getting values
 $qrid = $_POST['qrid'];
 $workshopid = $_POST['workshopid']; 
 //Creating an sql query
 require_once('dbConnect.php');
 $sql = "SELECT $workshopid from workshops WHERE qrid=$qrid";
 $result = mysqli_query($con,$sql);
 $row = $result->fetch_assoc();
 $attended = $row[$workshopid];
 if($attended == 1)
{
    echo 'yes';
}
else
{
    echo 'no';
 }
 //Closing the database 
 mysqli_close($con);
 }