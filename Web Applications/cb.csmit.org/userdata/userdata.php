<?php
$posted = array();
$work_row = array();
$work_row['w1']="";
$work_row['w2']="";
$work_row['w3']="";
$work_row['w4']="";
$work_row['w5']="";
$work_row['w6']="";
$work_row['w7']="";
$work_row['w8']="";
$work_row['w9']="";
$work_row['wh']="";
$posted['cbid']="";
$posted['E-mail']="";
if(!empty($_POST)) {
    //print_r($_POST);
  foreach($_POST as $key => $value) {    
    $posted[$key] = $value; 
	
  }
}

require_once('dbConnect.php');
if($posted['cbid'])
	$sql = "SELECT * FROM registereduser WHERE cbid='".$posted['cbid']."'";
else
	$sql = "SELECT * FROM registereduser WHERE email='".$posted['E-mail']."'";

$user_res = mysqli_query($con,$sql);
$user_row = mysqli_fetch_array($user_res);

if($user_row)
{
	$sql = "SELECT * FROM workshops WHERE cbid='".$user_row['cbid']."'";
	$work_res = mysqli_query($con,$sql);
	if($work_res)
		$work_row = mysqli_fetch_array($work_res);
}
?>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Sales Inquiry Form Responsive Widget Template| Home :: W3layouts</title>
<!-- Meta tag Keywords -->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="Sales Inquiry Form Responsive Widget Template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false);
function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- Meta tag Keywords -->
<!-- css files -->
<link href="css/style.css" rel="stylesheet" type="text/css" media="all">
<!-- //css files -->
<!-- online-fonts -->
<link href="//fonts.googleapis.com/css?family=Roboto:100,100i,300,300i,400,400i,500,500i,700,700i,900,900i&subset=cyrillic,cyrillic-ext,greek,greek-ext,latin-ext,vietnamese" rel="stylesheet">
<script>
function resetform()
{
	oFormObject = document.forms['form1'];
	for (i = 0; i < 11; i++) 
	{
	    	oFormObject.elements[i].value="";
	}	
	oFormObject.elements["submit"].value="submit";
	oFormObject.elements["reset"].value="reset";
	document.getElementById("qrcode").style.visibility = "hidden"; 	
	for (i = 0; i < 10; i++) 
	{
	    	document.getElementById("table").rows[1].cells[i].innerHTML="";
	}
	return false;
}
</script>
<style>
table.minimalistBlack {
  border: 3px solid #000000;
  width: 100%;
  text-align: center;
  border-collapse: collapse;
}
table.minimalistBlack td, table.minimalistBlack th {
  border: 1px solid #000000;
  padding: 5px 4px;
}
table.minimalistBlack tbody td {
  font-size: 13px;
}
table.minimalistBlack thead {
  background: #CFCFCF;
  background: -moz-linear-gradient(top, #dbdbdb 0%, #d3d3d3 66%, #CFCFCF 100%);
  background: -webkit-linear-gradient(top, #dbdbdb 0%, #d3d3d3 66%, #CFCFCF 100%);
  background: linear-gradient(to bottom, #dbdbdb 0%, #d3d3d3 66%, #CFCFCF 100%);
  border-bottom: 3px solid #000000;
}
table.minimalistBlack thead th {
  font-size: 15px;
  font-weight: bold;
  color: #000000;
  text-align: left;
}
table.minimalistBlack tfoot {
  font-size: 14px;
  font-weight: bold;
  color: #000000;
  border-top: 3px solid #000000;
}
table.minimalistBlack tfoot td {
  font-size: 14px;
}
</style>
</head>
<body>
	<!--header-->
	<div class="agile-header">
		<h1>Registered User Data</h1>
	</div>
	<!--//header-->
	<!--main-->
	<div class="agileits-main">
		<div class="wrap">
		<form name="form1" action="userdata.php" method="post">
			<ul>
				<li class="text">E-mail  :  </li>
				<li><?php echo "<input name='E-mail' value='".$user_row['email']."' type='text'>"; ?></li>
			</ul>
			<ul>
				<li class="text">CBID  :  </li>
				<li><?php echo "<input name='cbid' value='".$user_row['cbid']."' type='text'>"; ?></li>
			</ul>
			
			<div class="agile-submit">
				<input name="submit" type="submit" value="submit">
				<input name="reset" type="submit" value="reset" onclick="return resetform()">
			</div>
			
			<ul><li class="text"> </li></ul>
			<ul><li class="text"> </li></ul>
			<ul><li class="text"> </li></ul>
			<ul><li class="text"> </li></ul>
			<ul><li class="text"> </li></ul>
			<ul><li class="text"> </li></ul>
			<ul><li class="text"> </li></ul>
			<ul><li class="text"> </li></ul>
			<ul>
				<li class="text">name  :  </li>
				<li><?php echo "<input name='name' value='".$user_row['fname']." ".$user_row['lname']."' type='text'>"; ?></li>
			</ul>
			<ul>
				<li class="text">gender  :  </li>
				<li><?php echo "<input name='gender' value='".$user_row['gender']."' type='text'>"; ?></li>
			</ul>
			<ul>
				<li class="text">college  :  </li>
				<li><?php echo "<input name='college' value='".$user_row['college']."' type='text'>"; ?></li>
			</ul>
			<ul>
				<li class="text">department  :  </li>
				<li><?php echo "<input name='department' value='".$user_row['department']."' type='text'>"; ?></li>
			</ul>
			<ul>
				<li class="text">degree  :  </li>
				<li><?php echo "<input name='degree' value='".$user_row['degree']."' type='text'>"; ?></li>
			</ul>
			<ul>
				<li class="text">year  :  </li>
				<li><?php echo "<input name='year' value='".$user_row['year']."' type='text'>"; ?></li>
			</ul>
			<ul>
				<li class="text">mobile no  :  </li>
				<li><?php echo "<input name='contact' value='".$user_row['contact']."' type='text'>"; ?></li>
			</ul>
			<ul>
				<li class="text"> </li>
			</ul>			
			</form>
			<font color="white">
			<center>
			<table id="table" class="minimalistBlack" border="1" border-color="white">
			<thead>
			<tr>
				<th>Ethical Hacking</th>
				<th>Deep Learning</th>
				<th>Android</th>
				<th>IOT</th>
				<th>Robotics</th>
				<th>Embedded C</th>
				<th>IC Engine</th>
				<th>Automotive sketch</th>
				<th>Mech Materials</th>
				<th>Hackathon</th>
			</tr></thead><tbody>
			<tr>
				<td><?php echo $work_row['w1'] ?></td>
				<td><?php echo $work_row['w2'] ?></td>
				<td><?php echo $work_row['w3'] ?></td>
				<td><?php echo $work_row['w4'] ?></td>
				<td><?php echo $work_row['w5'] ?></td>
				<td><?php echo $work_row['w6'] ?></td>
				<td><?php echo $work_row['w7'] ?></td>
				<td><?php echo $work_row['w8'] ?></td>
				<td><?php echo $work_row['w9'] ?></td>
				<td><?php echo $work_row['wh'] ?></td>
			</tr></tbody>
			</table></center></font>
			<div class="clear"></div><div class="clear"></div><br>
			<center><?php if($posted['E-mail'] || $posted['cbid']) echo "<img id='qrcode' src='https://chart.googleapis.com/chart?chs=200x200&cht=qr&chl=".$user_row['cbid']."&choe=UTF-8'>"; ?></center>
			<div class="clear"></div>
		</div>	
	</div>
<!--//main-->
</body>
</html>