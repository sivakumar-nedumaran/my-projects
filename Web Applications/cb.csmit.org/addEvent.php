<?php 

 if($_SERVER['REQUEST_METHOD']=='POST')
{
 
 //Getting values
 $qrid = $_POST['qrid'];
 $eventid = $_POST['eventid']; 
 //Creating an sql query
 require_once('dbConnect.php');
 $sql = "SELECT $eventid from participants WHERE qrid=$qrid";
 $result = mysqli_query($con,$sql);
 $row = $result->fetch_assoc();
 $attended = $row[$eventid];
 if($attended == 1)
{
    echo 'Already attended this event';
}
else
{
    $sql = "UPDATE participants SET $eventid=1 WHERE qrid=$qrid";
    if(mysqli_query($con,$sql)){
        if(mysqli_affected_rows($con) == 1)
            echo 'Enrolled Successfully';
        else
            echo 'Sorry! No such participant available';
    } else{
        echo 'Sorry! Could not enroll';
    }
 }
 //Closing the database 
 mysqli_close($con);
 }