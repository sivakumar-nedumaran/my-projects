<?php

$gid = $_GET['gid'];
require_once('dbConnect.php');
$sql = "SELECT * FROM games WHERE gid=".$gid;
$game_res = mysqli_query($con,$sql);
$game_row = mysqli_fetch_array($game_res);
$max_level = $game_row['max_levels'];
$sql = "SELECT * FROM gamers WHERE gid=".$gid." ORDER BY cur_level DESC, time_stamp";
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
	<link rel="icon" type="image/png" href="images/icons/favicon.ico"/>
	<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="fonts/font-awesome-4.7.0/css/font-awesome.min.css">
	<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
	<link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
	<link rel="stylesheet" type="text/css" href="vendor/perfect-scrollbar/perfect-scrollbar.css">
	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
	<!-- //Stylesheet -->
	<!--fonts-->
	<link href="//fonts.googleapis.com/css?family=Roboto:300,400,500,700" rel="stylesheet">
	<link href="//fonts.googleapis.com/css?family=Montserrat+Alternates:200,400,500,600,700" rel="stylesheet">
	<!--//fonts-->
</head>

<body>
	<!--background--><Br><br>
	<h1 style="color: white"> <span><?php echo $game_row['gname'][0] ?></span><?php echo substr($game_row['gname'],1) ?>  <span>L</span>eaderboard Top 30</h1><br><br>
	
	<center>
				<a href="../portal.php" class="myButton">Back to portal</a>
			</center>
			
	<div class="bg-agile">
				
		<div class="container-table100">
			<div class="wrap-table100">

				<div class="table100 ver5 m-b-110">
					<div class="table100-head">
						<table>
							<thead>
								<tr class="row100 head">
									<th class="cell100 column1">Rank</th>
									<th class="cell100 column2">Participant</th>
									<th class="cell100 column3">College</th>
									<th class="cell100 column4">Level</th>
									<th class="cell100 column5">Timestamp</th>
								</tr>
							</thead>
						</table>
					</div>

					<div class="table100-body js-pscroll">
						<table>
							<tbody>
							
							<?php
							
								if(mysqli_num_rows($leaderboard)<30)
									$max = mysqli_num_rows($leaderboard);
								else
									$max = 30;
								$count = 1;
								while($row_users = mysqli_fetch_array($leaderboard)) 
								{
    								//output a row here
    								$sql = "SELECT * FROM registereduser WHERE cbid='".$row_users['cbid']."'";
									$part_res = mysqli_query($con,$sql);
									$part_row = mysqli_fetch_array($part_res);
    								echo "<tr class='row100 body'><td class='cell100 column1'>".$count."</td><td class='cell100 column2'>".$part_row['fname']." ".$part_row['lname']."</td><td class='cell100 column3'>".$part_row['college']."</td><td class='cell100 column4'>".$row_users['cur_level']."</td><td class='cell100 column5'>".$row_users['time_stamp']."</td></tr>";
    								$count = $count + 1;
    								if($count > $max)
    									break;
								}
			
							?>
							</tbody>
						</table>
					</div>
				</div>
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
		<script src="vendor/select2/select2.min.js"></script>
			<script src="js/main.js"></script>

	<script src="vendor/perfect-scrollbar/perfect-scrollbar.min.js"></script>
	<script>
		$('.js-pscroll').each(function(){
			var ps = new PerfectScrollbar(this);

			$(window).on('resize', function(){
				ps.update();
			})
		});
			
		
	</script>
			</div>
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