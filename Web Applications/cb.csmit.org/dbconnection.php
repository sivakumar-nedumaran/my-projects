<?php
$mysql_hostname = "mysql.cb.csmit.org";
$mysql_user = "cb16";
$mysql_password = "csMit2k17";
$mysql_database = "cb16";
$prefix = "";
$conn = @mysqli_connect($mysql_hostname, $mysql_user,$mysql_password) or die("Could not connect database");
mysqli_select_db($conn,$mysql_database) or die("<h1>Could not select database<h1>");
?>
