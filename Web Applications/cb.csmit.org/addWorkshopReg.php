<?php 

 if($_SERVER['REQUEST_METHOD']=='POST')
{
 
 //Getting values
 $qrid = $_POST['qrid'];
 $cbid = $_POST['cbid']; 
 $wlist = $_POST['wlist']; 
  //Creating an sql query
 $sql = "SELECT * FROM workshops WHERE cbid='$cbid'";
 
 //Importing our db connection script
 require_once('dbConnect.php');
 $result = mysqli_query($con,$sql);

 //Executing query to database
 if($result && mysqli_num_rows($result)==1){
 
    $sql = "UPDATE workshops SET qrid=$qrid WHERE cbid='$cbid'";
    $brake = 0;
    if(mysqli_query($con,$sql))
    {
       for ($i = 0; $i < strlen($wlist); $i++) 
       {
          $column = 'w'.$wlist[$i];    
          $sql = "UPDATE workshops SET $column=1 WHERE cbid='$cbid'";
          if(!mysqli_query($con,$sql))
          {
            $brake = 1;
            break;
          }
       }
       if($brake == 0)
          echo 'Already registered. Successfully updated.';
       else
          echo 'Please try again';
    }
 }
 else {

    $sql = "INSERT INTO workshops (qrid,cbid) values($qrid,'$cbid')";
    $brake = 0;
    if(mysqli_query($con,$sql))
    {
       for ($i = 0; $i < strlen($wlist); $i++) 
       {
          $column = 'w'.$wlist[$i];    
          $sql = "UPDATE workshops SET $column=1 WHERE cbid='$cbid'";
          if(!mysqli_query($con,$sql))
          {
            $brake = 1;
            break;
          }
       }
       if($brake == 0)
          echo 'Successfully registered to selected workshops';
       else
          echo 'Please try again';
    }
 }
 
 //Closing the database 
 mysqli_close($con);

 }