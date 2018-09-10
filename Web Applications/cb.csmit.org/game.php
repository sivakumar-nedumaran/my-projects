<?php

$posted = array();
if(!empty($_POST)) {
    //print_r($_POST);
  foreach($_POST as $key => $value) {    
    $posted[$key] = $value; 
	
  }
}
$ques_row="";
$quesno = $posted['qid'];
require_once('dbConnect.php');
$sql = "SELECT * FROM games WHERE gid=".$posted['gid'];
$game_res = mysqli_query($con,$sql);
$game_row = mysqli_fetch_array($game_res);
$gname = $game_row['gname'];
$sql = "SELECT ans FROM questions WHERE gid=".$posted['gid']." AND qid=".$posted['qid']." AND ans='".$posted['ans']."'";
$result = mysqli_query($con,$sql);
if($result && mysqli_num_rows($result)==1)
{
	$quesno = $quesno + 1;
	$sql = "UPDATE gamers SET cur_level=".$quesno." WHERE gid=".$posted['gid']." AND cbid='".$posted['cbid']."'";
	if(mysqli_query($con,$sql))
    {
    	$sql = "SELECT * FROM questions WHERE gid=".$posted['gid']." AND qid=".$quesno;
    	$result = mysqli_query($con,$sql);
        if($result && mysqli_num_rows($result)==1)
		{
			$ques_row=mysqli_fetch_assoc($result);
		}
		else
		{
			header('Location: leaderboard.php?gid=1');
		}
	}
}
else
{
	$sql = "SELECT cur_level FROM gamers WHERE gid=".$posted['gid']." AND cbid='".$posted['cbid']."'";
	$result=mysqli_query($con,$sql);
	if($result && mysqli_num_rows($result)==1)
	{
		$row=mysqli_fetch_assoc($result);
		$user_cur_level=$row["cur_level"];
	}
	else
	{
		$sql = "INSERT INTO gamers(cbid,gid,cur_level) VALUES('".$posted['cbid']."',".$posted['gid'].",1)";
		if(mysqli_query($con,$sql))
		{
			$user_cur_level=1;
		}
	}
	$sql = "SELECT * FROM questions WHERE gid=".$posted['gid']." AND qid=".$user_cur_level;
    $result = mysqli_query($con,$sql);
    if($result && mysqli_num_rows($result)==1)
	{
		$ques_row=mysqli_fetch_assoc($result);
	}
	else
	{
		header('Location: leaderboard.php?gid=1');
	}
}
$sql = "SELECT * FROM gamers WHERE gid=".$posted['gid']." ORDER BY cur_level DESC";
$leaderboard = mysqli_query($con,$sql);		        

?>

<!DOCTYPE HTML>
<html>

<head>
	<title>Carte Blanche 18 Online Event</title>
	<!-- Meta tags -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="keywords" content="Online Auto Booking Form Responsive Widget,Login form widgets, Sign up Web forms , Login signup Responsive web form,Flat Pricing table,Flat Drop downs,Registration Forms,News letter Forms,Elements"
	/>
	<script type="application/x-javascript">
		addEventListener("load", function () {
			setTimeout(hideURLbar, 0);
		}, false);

		function hideURLbar() {
			window.scrollTo(0, 1);
		}
	</script>
	<!-- //Meta tags -->
	<!-- Stylesheet -->
	<link href="css/wickedpicker.css" rel="stylesheet" type='text/css' media="all" />
	<link rel="stylesheet" href="css/jquery-ui.css" />
	<link href="css/style.css" rel='stylesheet' type='text/css' />
	<!-- //Stylesheet -->
	<!--fonts-->
	<link href="//fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">
	<link href="//fonts.googleapis.com/css?family=Montserrat+Alternates:200,400,500,600,700" rel="stylesheet">
	<!--//fonts-->
</head>

<body>
	<!--background-->
	<h1> <span>C</span>arte <span>B</span>lanche <span>1</span>8 <span>O</span>nline <span>E</span>vent</h1>
	<div class="bg-agile">
		<div class="left-agileits-w3layouts-img">
			<h3>Don't miss out !</h3><div style="color: white;">Our workshops</div><br>
			<?php
				$adimg = ($ques_row['qid']+5) % 6;
				$adimg = $adimg + 1;
				echo "<a href='../portal.php' target='_blank'>";
				echo "<img src='images/w".$adimg."a.png'><br>";
				echo "<img src='images/w".$adimg."b.png'><br></a>";
			?>
			<p> Register now and book your slots! </p><br><br>
		</div>
		<div class="book-appointment"><br>
			<h2 id="event_name"> <?php echo $gname ?> </h2><br>
			<div class="book-agileinfo-form">
				<form action="game.php" method="post">
					<h3 style="color: white;">Question <span id="q-no"><?php echo $ques_row['qid'] ?></span></h3><br>
					<div class="wthree-text">
					<?php
						if($ques_row['type']=='text')
							echo "<h6 style='color: white;' id='ques-text'>".$ques_row['ques']."</h6>";
						else
							echo "<img src='".$ques_row['ques']."'>";
					?>
						<br><br>
						<div class="main-agile-sectns">
							<div class="agileits-btm-spc form-text1">
								<center><input type="text" name="ans" placeholder="Your Answer" required=""></center><br><br>
								<?php
									echo "<p style='color: white;'> Hint: <br><br>".$ques_row['hint']."</p>";
								?>
								<input type="hidden" name="cbid" value="<?php echo $posted['cbid'] ?>" size="64" />
								<input type="hidden" name="gid" value="<?php echo $ques_row['gid'] ?>" size="64" />
								<input type="hidden" name="qid" value="<?php echo $ques_row['qid'] ?>" size="64" />
							</div>
						</div>
						<div class="clear"></div>
					</div>
					<input type="submit" value="Submit">
					<input type="reset" value="Reset">
					<div class="clear"></div>
				</form>
			</div>

		</div>
		<div class="left-agileits-w3layouts-img">
			<h3>Leaderboard</h3><br>
			<table id="leaderboard">
			<tr>
				<th>Rank</th>
				<th>Participant</th>
				<th>Level</th>
			</tr>
			<?php
			if(mysqli_num_rows($leaderboard)<15)
				$max = mysqli_num_rows($leaderboard);
			else
				$max = 15;
			$count = 1;
			while($row_users = mysqli_fetch_array($leaderboard)) 
			{
    			//output a row here
    			$sql = "SELECT * FROM registereduser WHERE cbid='".$row_users['cbid']."'";
				$part_res = mysqli_query($con,$sql);
				$part_row = mysqli_fetch_array($part_res);
    			echo "<tr><td>".$count."</td><td>".$part_row['fname']." ".$part_row['lname']."</td><td>".$row_users['cur_level']."</td></tr>";
    			$count = $count + 1;
    			if($count > $max)
    				break;
			}
			
			?>
			</table>
			<p> Get your name up here! </p>
		</div>
	</div>
	<!--copyright-->
	<div class="copy-w3layouts">
		<p>&copy; 2018. Carte Blanche 18 Online Event. All Rights Reserved | Design by CSMIT Web Team</p>
	</div>
	<!--//copyright-->
	<script type="text/javascript" src="js/jquery-2.2.3.min.js"></script>
	<!-- Time -->
	<script type="text/javascript" src="js/wickedpicker.js"></script>
	<script type="text/javascript">
		$('.timepicker').wickedpicker({
			twentyFour: false
		});
	</script>
	<!--// Time -->
	<!-- Calendar -->
	<script src="js/jquery-ui.js"></script>
	<script>
		$(function () {
			$("#datepicker,#datepicker1,#datepicker2,#datepicker3").datepicker();
		});
	</script>
	<!-- //Calendar -->

</body>

</html>