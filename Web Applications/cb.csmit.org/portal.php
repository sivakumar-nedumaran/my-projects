<?php
  if (isset($_GET['logout'])) {
    if(isset($_COOKIE['email']))
    {
    	unset($_COOKIE['email']);
    	setcookie('email',null,-1,'/');
    	header('Location: ../home.html');
    }
  }
  
	if(!isset($_COOKIE['email']))
	{
		header('Location: ../home.html');
		echo "<script>alert('Log in access')</script>";
	}
	
	require_once('dbConnect.php');
	$sql = "SELECT * FROM registereduser WHERE email='".$_COOKIE['email']."'";
	$part_res = mysqli_query($con,$sql);
	$part_row = mysqli_fetch_array($part_res);
	$user_name = $part_row['fname']." ".$part_row['lname'];
	$user_cbid = $part_row['cbid'];
	
?>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Carte Blanche'18</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta charset="utf-8">
<meta name="keywords" content="New Party Responsive web template, Bootstrap Web Templates, Flat Web Templates, Android Compatible web template, 
Smartphone Compatible web template, free webdesigns for Nokia, Samsung, LG, SonyEricsson, Motorola web design" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<!-- bootstrap-css -->
<link href="css/bootstrap.css" rel="stylesheet" type="text/css" media="all" />
<!--// bootstrap-css -->
<!-- css -->
<link rel="stylesheet" href="css/style.css" type="text/css" media="all" />
<link href="css/jQuery.lightninBox.css" rel="stylesheet" type="text/css" media="all" />
<link rel="stylesheet" href="css/flexslider.css" type="text/css" media="screen" property="" />
<!-- animation -->
<link href="css/aos.css" rel="stylesheet" type="text/css" media="all" /><!-- //animation effects-css-->
<!-- //animation -->
<!--// css -->
<!-- font-awesome icons -->
<link href="css/font-awesome.css" rel="stylesheet"> 
<!-- //font-awesome icons -->
<!-- font -->
<link href="//fonts.googleapis.com/css?family=Montserrat:300,400,500,600,700,800" rel="stylesheet">
<link href="//fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
<link href="//fonts.googleapis.com/css?family=Oswald:400,500,600,700" rel="stylesheet">
<!-- //font -->
<script src="js/jquery-2.2.3.min.js"></script>
<script src="js/bootstrap.js"></script>
</head>
<body>

<form id="online_game1" action="games/game.php" method="post">
<?php
		echo "<input type='hidden' name='cbid' value='".$user_cbid."' size='64' />";
?>
<input type="hidden" name="gid" value="1" size="64" />
<input type="hidden" name="qid" value="1" size="64" />
<input type="hidden" name="ans" value="nil" size="64" />
</form>

<form id="online_game2" action="games/game.php" method="post">
<?php
		echo "<input type='hidden' name='cbid' value='".$user_cbid."' size='64' />";
?>
<input type="hidden" name="gid" value="2" size="64" />
<input type="hidden" name="qid" value="1" size="64" />
<input type="hidden" name="ans" value="nil" size="64" />
</form>

<form id="online_game3" action="games/game.php" method="post">
<?php
		echo "<input type='hidden' name='cbid' value='".$user_cbid."' size='64' />";
?>
<input type="hidden" name="gid" value="3" size="64" />
<input type="hidden" name="qid" value="1" size="64" />
<input type="hidden" name="ans" value="nil" size="64" />
</form>

<form id="online_game4" action="games/game.php" method="post">
<?php
		echo "<input type='hidden' name='cbid' value='".$user_cbid."' size='64' />";
?>
<input type="hidden" name="gid" value="4" size="64" />
<input type="hidden" name="qid" value="1" size="64" />
<input type="hidden" name="ans" value="nil" size="64" />
</form>


	<!-- w3-banner -->
	<div class="w3-banner jarallax">
		<div class="wthree-different-dot">
			<div class="head">
				<div class="container">
					<div class="navbar-top">
							<!-- Brand and toggle get grouped for better mobile display -->
							<div class="navbar-header">
							  <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
								<span class="sr-only">Toggle navigation</span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
								<span class="icon-bar"></span>
							  </button>
								 <div class="navbar-brand logo ">
									<h3><a href="#"><img src="images/csmit.png" style="width:80px;"></a></h3>
								</div>

							</div>

							<!-- Collect the nav links, forms, and other content for toggling -->
							<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
							 <ul class="nav navbar-nav link-effect-4">
								<li class="active first-list"><a href="index.html">Home</a></li>
								<li><a href="#about" class="scroll">Special Events</a></li>
								<li><a href="#event" class="scroll">Workshops</a></li>
								<li><a href="#gallery" class="scroll">Online events</a></li>  
								<li><a href="accomodation.html">Accomodation</a></li>
								<li><a href="#services" class="scroll">Contact</a></li>
								<li><a href="portal.php?logout=true">Log out</a></li>
								
							  </ul>
							</div><!-- /.navbar-collapse -->
						</div>
				</div>
			</div>
			<!-- banner -->
			<div class="banner">
				<div class="container">
					<div class="slider">
						
						<script src="js/responsiveslides.min.js"></script>
						<script>
								// You can also use "$(window).load(function() {"
								$(function () {
								// Slideshow 4
									$("#slider3").responsiveSlides({
										auto: true,
										pager: true,
										nav: true,
										speed: 500,
										namespace: "callbacks",
										before: function () {
											$('.events').append("<li>before event fired.</li>");
										},
										after: function () {
											$('.events').append("<li>after event fired.</li>");
										}
									 });				
								});
						</script>
						<script type="text/javascript">
						function getCookie(cname) {
    var name = cname + "=";
    var decodedCookie = decodeURIComponent(document.cookie);
    var ca = decodedCookie.split(';');
    for(var i = 0; i <ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == ' ') {
            c = c.substring(1);
        }
        if (c.indexOf(name) == 0) {
            return c.substring(name.length, c.length);
        }
    }
    return "";
}

function paylink(workshopid)
{
	var emailval = getCookie('email');
	document.getElementById(workshopid).href = document.getElementById(workshopid).href + emailval;
	return true;
}

function regret()
{
	alert('With regards to lots of requests for onsite registration, we have decided to release all seats for registration onsite!! So please feel free to come register for the workshop/events directly onsite on the day of the event!');
}
						</script>
						
						<div  id="top" class="callbacks_container-wrap">
							<ul class="rslides" id="slider3">
								<li>
									<div class="slider-info" data-aos="fade-left">
									<img src="images/cb.png" style="width:100px;"><br><br>
									<h6>XPLORE THE FREEDOM</h6>
										<h3>Carte Blanche'18</h3>
										<p >National-level Inter-college Tech Festival of MIT</p>
										<div class="more-button">
											<a href="#" data-toggle="modal" data-target="#myModal">More Info</a>
										</div>
									</div>
								</li>
								<li>
									<div class="slider-info" data-aos="fade-left">
									<img src="images/cb.png" style="width:100px;"><br><br>
									<h6>XPLORE THE FREEDOM</h6>
										<h3>Even Bigger</h3>
										<p>National-level Inter-college Tech Festival of MIT</p>
										<div class="more-button">
											<a href="#" data-toggle="modal" data-target="#myModal">More Info</a>
										</div>
									</div>
								</li>
								<li>
									<div class="slider-info" data-aos="fade-left">
									<img src="images/cb.png" style="width:100px;"><br><br>
									<h6>XPLORE THE FREEDOM</h6>
										<h3>Even Better</h3>
										<p>National-level Inter-college Tech Festival of MIT</p>
										<div class="more-button">
											<a href="#" data-toggle="modal" data-target="#myModal">More Info</a>
										</div>
									</div>
								</li>
							</ul>
						</div>
					</div>
				</div>
			</div>
			<!-- //banner -->
		</div>
	</div>
	<!-- //w3-banner -->
	<!-- modal -->
	<div class="modal about-modal fade" id="myModal" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header"> 
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>						
						<h4 class="modal-title">About Us</h4>
					</div> 
					<div class="modal-body">
					<div class="agileits-w3layouts-info">
						<img src="images/g1.jpg" alt="" />
						<p>The Computer Society of MIT - Anna University was founded in 1983 improving the knowledge of students in the field of computer science. Being the inter-disciplinary technical student body of MIT, CSMIT comprises of 9 departments and has been synonymous with bringing new, exciting and innovative ideas of the students to the forefront of our college community. Over the years, the society has evolved, from being just an organization for imparting knowledge, to an active participant in the evolution and promotion of Free Open Source Software (FOSS). The major event associated with this area is the conducting of the national level inter college free software festival Carte Blanche every year.</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal about-modal fade" id="myModal2" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header"> 
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>						
						<h4 class="modal-title">Hackathon</h4>
					</div> 
					<div class="modal-body">
					<div class="agileits-w3layouts-info">
						<img src="images/hackathon.jpg" height="300px" width="400px" alt="" />
						<p style="color: black;">The time has come for you developers to show your progress with the clock racing against you. With unique and interesting domains to work on and over 8 hours to code, this is your platform to transform your ideas into innovative solutions. Get set for 8 hours of non-stop developing in Carte Blanche Hackathon 2018! Remember to bring your ketchups as the hackathon ends with Pizza !! Register now to book your slots. Participate and stand a chance to earn exciting prizes and internships at CB'18. </p><br>
<Strong>Entry fee:</strong> Rs.750 per team<br>
<Strong>Team size:</strong> Maximum of 3<br>
<Strong>Date:</strong> Feb 9th, 2018 @ 9am<br><Br>
<Strong>Rules:</strong><Br>
<ul style="margin: 20px">
<li>All teams are asked to bring their laptops with ethernet ports. </li>
<li>Teams can develop their app in any platform, be it for mobile or PC.</li>
<li>Themes and topics will be announed on the spot.</li>
</ul>
<center><Strong>Prizes</strong> <br><Br>
Winning team - 2k per team<br>
Running team - 1.5k per team<br></center>
						<div class="w3layouts_more">
						      <center><!--a href="payment.php?product=wh&email=" id="wh" onclick="return paylink('wh')"--><a href="#" onclick="regret()">Register now</a></center><br>
					    </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal about-modal fade" id="myModal3" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header"> 
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>						
						<h4 class="modal-title">Paper Presentation</h4>
					</div> 
					<div class="modal-body">
					<div class="agileits-w3layouts-info">
						<img src="images/presentation.jpg" height="300px" width="400px" alt="" />
						<br><br><strong>Abstract</strong><br>
						<p style="color: black;">The abstract should be in 2 pages and must contain the following:  Presenter Name, Department, College, Year of study, Contact, Email ID, No. of authors, Author names<br>
						The abstracts will be screened and those selected will be called for presentation. All candidates will receive an email update on their selection status.</p><br>
						<strong>Rules for presentation</strong><br><br>
						<ul style="margin: 20px">
						<li>Maximum 3 members per team</li>
						<li>The presentation should last a maximum of 7 minutes. There will be a 3 minutes query and answer session following it.</li>
						<li>Each team should bring two hardcopies of the paper that they are going to present.</li>
						<li>All participants should have to do a general registration on the desk.</li>
						<li>Presenters will have to carry their presentation in pdf and ppt format in a pendrive.</li>
						<li>There would be two rounds : The first will be screening and the second will be final presention on the same day.</li>
						<li>The decision of the judges will be final.</li>
						<li>The decision of the judges will be final.</li>
						</ul>
						<br>
						<strong>Deadline for submission of abstract</strong> : Feb 2nd, 2018<br>
						<strong>Contact</strong> : Arun - 7092486100  |  Fathima - 7200258412<br>

						<div class="w3layouts_more">
						      <center><a href="https://goo.gl/forms/6h4LFWSTuUszyHoG3" target="_blank">Register now</a></center><br>
					    </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div class="modal about-modal fade" id="myModal4" tabindex="-1" role="dialog">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-header"> 
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>						
						<h4 class="modal-title">CBKO 18</h4>
					</div> 
					<div class="modal-body">
					<div class="agileits-w3layouts-info">
						<img src="images/cubing.jpg" height="300px" width="400px" alt="" />
						<br><br><strong>REGISTRATION DETAILS</strong>
<br><br>
<strong>Fee Structure:</strong> <p style="color: black;">ONLY ONLINE REGISTRATION AND PAYMENT IS AVAILABLE. ON THE SPOT REGISTRATION IS NOT AN OPTION.<br>
A base fee of INR 250 (which is inclusive for taking up any 3 events) is mandatory and additional fees of INR 50 per event is applicable. <br>
For example, if you register for 6 events, your fees would be 250 + (3 x 50 )= INR 400 </p><br>

<strong>Steps to Register:</strong><br><br>
<ol>
<li>Register on the WCA website using this LINK: https://www.worldcubeassociation.org/competitions/CarteBlancheKubeOpen2018/register </li>
<li>Pay the registration fee by clicking HERE: https://www.cubelelo.com/competitions/carte-blanche-kube-open-2018/event-registration-carte-blanche-kube-open-2018 </li>
<li>Online Registration closes on 3rd February 2018.</li>
</ol>
<br>
<strong>Note:</strong><br><Br>
1. Registrations will be accepted on WCA only after the payment of the fee through the above-given link is made.<br>
2. Once payment is made by the competitor, it might take few days for acceptance of the registration on WCA.<br><br>
						<div class="w3layouts_more">
						      <center><a href="https://www.worldcubeassociation.org/competitions/CarteBlancheKubeOpen2018#general-info" target="_blank">Register now</a></center><br>
					    </div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- //modal -->
	<div class="banner-bottom" id="about">
		<div class="container">
		<p><strong>
		<?php
		echo "Welcome ".$user_name."! Your CB ID : ".$user_cbid.".";
		?> </p></strong><br><br>
			<h3 class="heading-agileinfo" data-aos="zoom-in">Special Events<span>For those who crave more</span></h3>
			<div class="w3ls_banner_bottom_grids">
				<div class="col-md-4 agileits_services_grid" data-aos="fade-right">
					<div class="w3_agile_services_grid1">
						<img src="images/g3.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<h3 style="color: #808080" >HACKATHON</h3>
					<p>An exclusive full day hackathon for those insomniac developers. And guess what, it ends with pizza night!</p>
					
					<div class="w3layouts_more">
						<a href="#" data-toggle="modal" data-target="#myModal2">Know more</a>
					</div>
				</div>
				<div class="col-md-4 agileits_services_grid" data-aos="fade-up">
					<div class="w3_agile_services_grid1">
						<img src="images/g2.png" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<h3 style="color: #808080" >PAPER PRESENTATION</h3>
					<p>Got some awesome ideas or projects? Pen them down into a paper and bring it on to present and win exciting prizes!</p>
					
					<div class="w3layouts_more">
						<a href="#" data-toggle="modal" data-target="#myModal3">Know more</a>
					</div>
				</div>
				<div class="col-md-4 agileits_services_grid" data-aos="fade-left">
					<div class="w3_agile_services_grid1">
						<img src="images/cube.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<h3 style="color: #808080" >CBKO'18</h3>
					<p>For all those ardent cubers out there...  Behold Carte Blanche Kube Open'18 organized by the World Cube Association themselves!</p>
					
					<div class="w3layouts_more">
						<a href="#" data-toggle="modal" data-target="#myModal4">Know more</a>
					</div>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>	
	<!-- event schedule -->
                <div class="event-time " id="event">
                    <div class="container">
						<h3  style="color: #eee" class="heading-agileinfo" data-aos="zoom-in">Our Workshops<span>For those waiting to upgrade themselves</span></h3>
                        <div class="testi-info">
                            <!-- Nav tabs -->
                            <ul class="nav nav-tabs" role="tablist">
                                <li role="presentation" class="active">
                                    <a href="#testi" aria-controls="testi" role="tab" data-toggle="tab">Algo</a>
                                </li>
                                <li role="presentation">
                                    <a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Elex</a>
                                </li>
                                <li role="presentation">
                                    <a href="#messages" aria-controls="messages" role="tab" data-toggle="tab">Mech</a>
                                </li>
                            </ul>
                            <!-- Tab panes -->
                            <div class="tab-content">
                                <div role="tabpanel" class="tab-pane active" id="testi">
                                    <div class="eventmain-info">
                                        <div class="event-subinfo">
										<div class="col-md-4 agileits_services_grid" data-aos="fade-right">
					<div class="w3_agile_services_grid1">
						<img src="images/a1.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<div class="pr-ta">
						<h3>Ethical Hacking and Cyber Security</h3>
						<p>Learn to hack for good at Carte Blanche '18. Book your slot now!</p>
						<span class="moto-color1_3"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp; February 9, 2018</span>
						<div class="tabl-erat">
							<div class="col-md-5 ratt">
								<h6>₹ 800</h6>
							</div>
							<div class="col-md-7 tag">
								<a href="payment.php?product=w1&email=" id="w1" onclick="return paylink('w1')"><!--a href="#" onclick="regret()"-->Book Now</a>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				
				<div class="col-md-4 agileits_services_grid" data-aos="fade-up">
					<div class="w3_agile_services_grid1">
						<img src="images/a2.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<div class="pr-ta">
						<h3>Deep Learning and Machine Intelligence</h3>
						<p>Participate to predict the future at Carte Blanche'18. Book your slot now!</p>
						<span class="moto-color1_3"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp; February 9, 2018</span>
						<div class="tabl-erat">
							<div class="col-md-5 ratt">
								<h6>₹ 800</h6>
							</div>
							<div class="col-md-7 tag">
								<a href="payment.php?product=w2&email=" id="w2" onclick="return paylink('w2')"><!--a href="#" onclick="regret()"-->Book Now</a>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				
				<div class="col-md-4 agileits_services_grid" data-aos="fade-up">
					<div class="w3_agile_services_grid1">
						<img src="images/a3.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<div class="pr-ta">
						<h3>Nougat Development Workshop</h3>
						<p>Enough of using apps. Let's build one at Carte Blanche'18. Book your slot now!</p>
						<span class="moto-color1_3"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp; February 10, 2018</span>
						<div class="tabl-erat">
							<div class="col-md-5 ratt">
								<h6>₹ 700</h6>
							</div>
							<div class="col-md-7 tag">
								<a href="payment.php?product=w3&email=" id="w3" onclick="return paylink('w3')"><!--a href="#" onclick="regret()"-->Book Now</a>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				
                                            <!--<div class="col-md-6  w3-latest-grid">
                                                <div class="col-md-6 col-xs-6 event-right eventtxt-right" data-aos="fade-down">
                                                   <img src="images/g7.jpg" class="img-responsive" alt="">
                                                </div>
                                                <div class="col-md-6 col-xs-6 event-left" data-aos="fade-right">
                                                    <h5>31 Dec,2016.</h5>

                                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit
                                                       </p>
                                                    <h6>
                                                        <span class="icon-event" aria-hidden="true">venue:</span> Madison Avenue</h6>
                                                    <a href="#" data-toggle="modal" data-target="#myModal">view details</a>
                                                </div>
                                                <div class="clearfix"> </div>
                                            </div> --
                                            <div class="col-md-6 w3-latest-grid">
                                                <div class="col-md-6 col-xs-6 event-right" data-aos="fade-up">
                                                    <img src="images/g1.jpg" class="img-responsive" alt="">
                                                </div>
                                                <div class="col-md-6 col-xs-6 event-left in-news" data-aos="fade-right">
                                                     <h5>31 Dec,2016.</h5>

                                                    <p>Lorem ipsum dolor sit amet, consectetur adipisicing elit
                                                       </p>
                                                    <h6>
                                                        <span class="icon-event" aria-hidden="true">venue:</span> Madison Avenue</h6>
                                                    <a href="#" data-toggle="modal" data-target="#myModal">view details</a>
                                                </div>
                                                <div class="clearfix"> </div>
                                            </div>
                                            <div class="clearfix"> </div>-->
                                        </div>
                                        
                                    </div>
                                </div>
                                <div role="tabpanel" class="tab-pane" id="profile">
                                    <div class="eventmain-info">
                                        <div class="event-subinfo">
                                            <div class="col-md-4 agileits_services_grid" data-aos="fade-up">
					<div class="w3_agile_services_grid1">
						<img src="images/e1.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<div class="pr-ta">
						<h3>Internet of Things and Networking</h3>
						<p>Book your slot now!</p>
						<span class="moto-color1_3"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp; February 10, 2018</span>
						<div class="tabl-erat">
							<div class="col-md-5 ratt">
								<h6>₹ 800</h6>
							</div>
							<div class="col-md-7 tag">
								<!--a href="payment.php?product=w4&email=" id="w4" onclick="return paylink('w4')"--><a href="#" onclick="regret()">Book Now</a>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				
				<div class="col-md-4 agileits_services_grid" data-aos="fade-up">
					<div class="w3_agile_services_grid1">
						<img src="images/e2.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<div class="pr-ta">
						<h3>Robotics in Defence and Defence Electronics</h3>
						<p>Book your slot now!</p>
						<span class="moto-color1_3"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp; February 9, 2018</span>
						<div class="tabl-erat">
							<div class="col-md-5 ratt">
								<h6>₹ 1000</h6>
							</div>
							<div class="col-md-7 tag">
								<!--a href="payment.php?product=w5&email=" id="w5" onclick="return paylink('w5')"--><a href="#" onclick="regret()">Book Now</a>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				
				<div class="col-md-4 agileits_services_grid" data-aos="fade-up">
					<div class="w3_agile_services_grid1">
						<img src="images/g5.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<div class="pr-ta">
						<h3>Embedded C programming in PIC using Proteus</h3>
						<p>Book your slot now!</p>
						<span class="moto-color1_3"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp; February 10, 2018</span>
						<div class="tabl-erat">
							<div class="col-md-5 ratt">
								<h6>₹ 750</h6>
							</div>
							<div class="col-md-7 tag">
								<!--a href="payment.php?product=w6&email=" id="w6" onclick="return paylink('w6')"--><a href="#" onclick="regret()">Book Now</a>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
                                        </div>
                                        
                                    </div>
                                </div>
                                <div role="tabpanel" class="tab-pane" id="messages">
                                    <div class="eventmain-info">
                                        <div class="event-subinfo">
                                            <div class="col-md-4 agileits_services_grid" data-aos="fade-up">
					<div class="w3_agile_services_grid1">
						<img src="images/m1.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<div class="pr-ta">
						<h3>Live surgery of IC Engines</h3>
						<p>Book your slot now!</p>
						<span class="moto-color1_3"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp; February 9, 2018</span>
						<div class="tabl-erat">
							<div class="col-md-5 ratt">
								<h6>₹ 750</h6>
							</div>
							<div class="col-md-7 tag">
								<!--a href="payment.php?product=w7&email=" id="w7" onclick="return paylink('w7')"--><a href="#" onclick="regret()">Book Now</a>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				
				<div class="col-md-4 agileits_services_grid" data-aos="fade-up">
					<div class="w3_agile_services_grid1">
						<img src="images/m2.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<div class="pr-ta">
						<h3>Automotive Sketching & Aerodynamics Modelling</h3>
						<p>Book your slot now!</p>
						<span class="moto-color1_3"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp; February 10, 2018</span>
						<div class="tabl-erat">
							<div class="col-md-5 ratt">
								<h6>₹ 800</h6>
							</div>
							<div class="col-md-7 tag">
								<!--a href="payment.php?product=w8&email=" id="w8" onclick="return paylink('w8')"--><a href="#" onclick="regret()">Book Now</a>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				
				<div class="col-md-4 agileits_services_grid" data-aos="fade-up">
					<div class="w3_agile_services_grid1">
						<img src="images/m3.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<div class="pr-ta">
						<h3>Mechanical materials with industrial visit</h3>
						<p>Book your slot now!</p>
						<span class="moto-color1_3"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp; February 11, 2018</span>
						<div class="tabl-erat">
							<div class="col-md-5 ratt">
								<h6>₹ 800</h6>
							</div>
							<div class="col-md-7 tag">
								<!--a href="payment.php?product=w9&email=" id="w9" onclick="return paylink('w9')"--><a href="#" onclick="regret()">Book Now</a>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
                                        </div>
                                        
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
     
    <!-- //event schedule --
	<div class="pricing" id="pricing">
		<div class="container">
			<h3 class="heading-agileinfo" data-aos="zoom-in">Pricing<span style="color: white;">Events Is A Professionally Managed Event</span></h3>
			<div class="w3ls_banner_bottom_grids">
				<div class="col-md-4 agileits_services_grid" data-aos="fade-right">
					<div class="w3_agile_services_grid1">
						<img src="images/g4.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<div class="pr-ta">
						<h3>Table deposit</h3>
						<p>Save the spot and be the first to party.</p>
						<span class="moto-color1_3"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp; December 28, 2017</span>
						<div class="tabl-erat">
							<div class="col-md-5 ratt">
								<h6>$99.55</h6>
							</div>
							<div class="col-md-7 tag">
								<a href="#book" class="scroll">Book Now</a>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				<div class="col-md-4 agileits_services_grid" data-aos="fade-up">
					<div class="w3_agile_services_grid1">
						<img src="images/g5.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<div class="pr-ta">
						<h3>Table deposit</h3>
						<p>Save the spot and be the first to party.</p>
						<span class="moto-color1_3"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp; December 28, 2017</span>
						<div class="tabl-erat">
							<div class="col-md-5 ratt">
								<h6>$99.55</h6>
							</div>
							<div class="col-md-7 tag">
								<a href="#book" class="scroll">Book Now</a>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				<div class="col-md-4 agileits_services_grid" data-aos="fade-left">
					<div class="w3_agile_services_grid1">
						<img src="images/g6.jpg" alt=" " class="img-responsive">
						<div class="w3_blur"></div>
					</div>
					<div class="pr-ta">
						<h3>Table deposit</h3>
						<p>Save the spot and be the first to party.</p>
						<span class="moto-color1_3"><i class="fa fa-calendar-check-o" aria-hidden="true"></i>&nbsp; December 28, 2017</span>
						<div class="tabl-erat">
							<div class="col-md-5 ratt">
								<h6>$99.55</h6>
							</div>
							<div class="col-md-7 tag">
								<a href="#book" class="scroll">Book Now</a>
							</div>
							<div class="clearfix"></div>
						</div>
					</div>
				</div>
				<div class="clearfix"> </div>
			</div>
		</div>
	</div>
<!--pricing-->
<!-- Portfolio section -->
	<section class="portfolio-agileinfo" id="gallery">
		<div class="container">
			<h3 class="heading-agileinfo" data-aos="zoom-in">Online Events<span>An event for you at your place</span></h3>
				<div class="gallery-grids">
					<div class="tab_img">
						<div class="col-md-3 col-sm-6 col-xs-6 portfolio-grids" data-aos="zoom-in">
							<a href="https://www.facebook.com/carteblanche.csmit" >
								<img src="images/o2.jpg" class="img-responsive" alt="w3ls"/>
								<div class="b-wrapper">
									<h4 style="color: #eee" >Mannequin Challenge</h4>
									<br><p>Ongoing</p><br>
									<button>Enter Now</button>
								</div>
							</a>
						</div>
						<div class="col-md-3 col-sm-6 col-xs-6 portfolio-grids" data-aos="zoom-in">
							<a href="#">
								<img src="images/o3.jpg" class="img-responsive" alt="w3ls"/>
								<div class="b-wrapper">
									<h4 style="color: #eee" >Qurukshetra</h4>
									<br><p>Event ended</p><br>
									<!-- <button>See results</button> -->
								</div>
							</a>
						</div>
						<div class="col-md-3 col-sm-6 col-xs-6 portfolio-grids" data-aos="zoom-in">
							<a href="https://www.facebook.com/carteblanche.csmit" target="_blank">
								<img src="images/o5.jpeg" class="img-responsive" alt="w3ls"/>
								<div class="b-wrapper">
									<h4 style="color: #eee" >Photography</h4>
									<br><p>Ongoing</p><br>
									<button>Enter Now</button>
								</div>
							</a>
						</div>
						<div class="col-md-3 col-sm-6 col-xs-6 portfolio-grids" data-aos="zoom-in">
							<a href="javascript:;" onclick="document.getElementById('online_game2').submit();">
								<img src="images/o6.jpg" class="img-responsive" alt="w3ls"/>
								<div class="b-wrapper">
									<h4 style="color: #eee" >Connexions</h4>
									<br><p>Event started! Click to enter...</p><br>
									<button>Enter Now</button>
								</div>
							</a>
						</div>
						<div class="col-md-3 col-sm-6 col-xs-6 portfolio-grids" data-aos="zoom-in">
							<a href="#" >
								<img src="images/o8.jpg" class="img-responsive" alt="w3ls"/>
								<div class="b-wrapper">
									<h4 style="color: #eee" >OLPC</h4>
									<br><p>Event ended</p><br>
									<!-- <button>Enter Now</button> -->
								</div>
							</a>
						</div>
						
						<div class="clearfix"> </div>
					</div>
				
			</div>
		</div>
</section>
<!-- /Portfolio section -->	 
<!-- services -->
<div class="services jarallax" id="services">
		<h3 style="color: #eee" class="heading-agileinfo" data-aos="zoom-in">Contact Us<span class="thr">Drop us a line when you need us</span></h3>
	<div class="container">
		
			<div class="col-md-4 ser-1" data-aos="fade-right">
				<div class="grid1">
					
					<h4>Onsite Events<br>Coordinator</h4>
					<p>Jawahar S<br>jawaharsundarajan@gmail.com<br>+91 9488669732</p>
				</div>
			</div>
			<div class="col-md-4 ser-1" data-aos="fade-down">
				<div class="grid1">
					
					<h4>Online Events<br>Coordinator</h4>
					<p>Arun Kumar D<br>mohanapani5@gmail.com<br>+91 9677230203</p>
				</div>
			</div>
			<div class="col-md-4 ser-1" data-aos="fade-left">
				<div class="grid1">
					
					<h4>Workshop<br>Coordinator</h4>
					<p>Abi Vignesh<br>abikrish007@gmail.com<br>+91 9791174686</p>
				</div>
			</div>
			
			<div class="col-md-4 ser-1" data-aos="fade-right">
				<div class="grid1">
					
					<h4>Hackathon<br>Coordinator</h4>
					<p>Abishek K<br>abishekkumar2@gmail.com<br>+91 9443913699</p>
				</div>
			</div>
			<div class="col-md-4 ser-1" data-aos="fade-down">
				<div class="grid1">
					
					<h4>Paper Presentation<br>Coordinator</h4>
					<p>Arun M<br>arunjayamani07@gmail.com<br>+91 7010568744</p>
				</div>
			</div>
			<div class="col-md-4 ser-1" data-aos="fade-left">
				<div class="grid1">
					
					<h4>CBKO 18<br>Coordinator</h4>
					<p>Adithyaa M A<br>adithyaa.ma@gmail.com<br>+91 9003163490</p>
				</div>
			</div>
		
			<div class="col-md-4 ser-1" data-aos="fade-right">
				<div class="grid1">
					
					<h4>School Events<br>Coordinator</h4>
					<p>Siva Mugesh<br>sivamugesh4@gmail.com<br>+91 8148384050</p>
				</div>
			</div>
			<div class="col-md-4 ser-1" data-aos="fade-up">
				<div class="grid1">
					
					<h4>Accomodation and Hospitality</h4>
					<p>Veeramani SB<br>sbveeramani97@gmail.com<br>+91 9976424971</p>
				</div>
			</div>
			<div class="col-md-4 ser-1" data-aos="fade-left">
				<div class="grid1">
					
					<h4>General<br>Enquiry</h4>
					<p>CB Queries<br>cbqueries@csmit.org<br>+91 8489811104</p>
				</div>
			</div>
			<div class="clearfix"></div>
		
	</div>
</div>
<!-- //services -->

	<!-- footer -->
	<div class="footer">
			<p class="copyright" data-aos="zoom-in">© 2018 Carte Blanche'18. All Rights Reserved | Design by <span title="Shiva | Bently Nixon">CSMIT Web Team</span></p>
	</div>
	<!-- //footer -->

<!-- js for portfolio lightbox -->
<script src="js/jQuery.lightninBox.js"></script>
<script type="text/javascript">
	$(".lightninBox").lightninBox();
</script>
<!-- /js for portfolio lightbox -->
<!-- flexSlider -->
				<script defer src="js/jquery.flexslider.js"></script>
				<script type="text/javascript">
					$(window).load(function(){
					  $('.flexslider').flexslider({
						animation: "slide",
						start: function(slider){
						  $('body').removeClass('loading');
						}
					  });
					});
				</script>
<!-- //flexSlider -->

<script type="text/javascript" src="js/move-top.js"></script>
	<script type="text/javascript" src="js/easing.js"></script>

	<script src="js/jarallax.js"></script>
	<script src="js/SmoothScroll.min.js"></script>
	<script type="text/javascript">
		/* init Jarallax */
		$('.jarallax').jarallax({
			speed: 0.5,
			imgWidth: 1366,
			imgHeight: 768
		})
	</script><!-- here stars scrolling icon -->
	<script type="text/javascript">
		$(document).ready(function() {
			/*
				var defaults = {
				containerID: 'toTop', // fading element id
				containerHoverID: 'toTopHover', // fading element hover id
				scrollSpeed: 1200,
				easingType: 'linear' 
				};
			*/
								
			$().UItoTop({ easingType: 'easeOutQuart' });
								
			});
	</script>
	<!-- //here ends scrolling icon -->
<!-- scrolling script -->
<script type="text/javascript">
	jQuery(document).ready(function($) {
		$(".scroll").click(function(event){		
			event.preventDefault();
			$('html,body').animate({scrollTop:$(this.hash).offset().top},1000);
		});
	});
</script> 
<!-- //scrolling script -->
<!-- animation effects-js files-->
	<script src="js/aos.js"></script><!-- //animation effects-js-->
	<script src="js/aos1.js"></script><!-- //animation effects-js-->
<!-- animation effects-js files-->

</body>	
</html>