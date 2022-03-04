<?php
include("connect.php");
session_start();
if(!isset($_SESSION['ad_id']))
{
	header("location:login.php");
}
$uri = $_SERVER['REQUEST_URI'];
$_SESSION['url']=$uri;
$q="select * from document";
$rs=mysqli_query($cn,$q);	
$document = mysqli_num_rows($rs);
$q="select * from document where verification != 'pending'";
$rs=mysqli_query($cn,$q);	
$vdocument = mysqli_num_rows($rs);
$q="select * from document where verification = 'pending'";
$rs=mysqli_query($cn,$q);	
$pdocument = mysqli_num_rows($rs);
$q="select * from register_user where email != ''";
$rs=mysqli_query($cn,$q);	
$driver = mysqli_num_rows($rs);
$q="select * from ratting";
$rs=mysqli_query($cn,$q);	
$ratting = mysqli_num_rows($rs);
function get_percentage($total, $number)
{
  	if ( $total > 0 )
  	{
  		$p = ($number *100)/$total;
  		return round($p,2);
  	}
   	else
   	{
    	return 0;
  	}
}
?>
<!DOCTYPE html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<!-- Add icon Link -->
<link rel="icon" href="images/logo.png" type="image/png">
<!-- //Add icon Link -->
<!-- bootstrap-css -->
<link rel="stylesheet" href="css/bootstrap.css">
<!-- //bootstrap-css -->
<!-- Custom CSS -->
<link rel='stylesheet' href="css/style.css" type='text/css' />
<!-- font CSS -->
<link rel="stylesheet" href="css/font.css" type="text/css"/>
<!-- font-awesome icons -->
<link rel="stylesheet" href="css/font-awesome.css" type="text/css"/>
<link rel="stylesheet" href="css/all.css" type="text/css"/>
<!-- //font-awesome icons -->
<script src="js/jquery2.0.3.min.js"></script>
<script src="js/modernizr.js"></script>
<script src="js/jquery.cookie.js"></script>
<script src="js/screenfull.js"></script>
</head>
<body class="dashboard-page">
	<nav class="main-menu">
		<ul>
			<li>
				<a href="home_admin.php">
					<i style="padding:20px;font-size:20px" class="fa fa-home"></i>
					<span class="nav-text">
					Home
					</span>
				</a>
			</li>
			<li class="has-subnav">
				<a href="javascript:;">
				<i style="padding:20px;font-size:20px" class="fa fa-building"></i>
				<span class="nav-text">
					Branch
				</span>
				<i class="icon-angle-right"></i><i class="icon-angle-down"></i>
				</a>
				<ul>
					<li>
					<a class="subnav-text" href="addbranch.php">
					Add
					</a>
					</li>
					<li>
					<a class="subnav-text" href="viewbranch.php">
					Edit OR View
					</a>
					</li>
				</ul>
			</li>
			<li class="has-subnav">
				<a href="javascript:;">
				<i style="padding:20px;font-size:20px" class="fa fa-envelope"></i>
				<span class="nav-text">
				Circular
				</span>
				<i class="icon-angle-right"></i><i class="icon-angle-down"></i>
				</a>
				<ul>
					<li>
						<a class="subnav-text" href="sendcircular.php">Send</a>
					</li>
					<li>
						<a class="subnav-text" href="viewcircular.php">View</a>
					</li>
				</ul>
			</li>
			<li class="has-subnav">
				<a href="javascript:;">
					<i class="fa fa-check-circle" style="padding:20px;font-size:20px"></i>	
					<span class="nav-text">Attendance Report</span>
					<i class="icon-angle-right"></i><i class="icon-angle-down"></i>
				</a>
				<ul>
					<li>
						<a class="subnav-text" href="tpoliceattendance.php">
							Traffic-Police
						</a>
					</li>
					<li>
						<a class="subnav-text" href="branchattendance.php">
							Branch 
						</a>
					</li>
				</ul>
			</li>
			<li class="has-subnav">
				<a href="javascript:;">
					<i class="fa fa-edit" style="padding:20px;font-size:20px" aria-hidden="true"></i>
						<span class="nav-text">Memo Report</span>
					<i class="icon-angle-right"></i><i class="icon-angle-down"></i>
				</a>
				<ul>
					<li>
						<a class="subnav-text" href="tpolicememo.php">
							Traffic-Police 
						</a>
					</li>
					<li>
						<a class="subnav-text" href="branchmemo.php">
							Branch 
						</a>
					</li>
				</ul>
			</li>
			<li class="has-subnav">
				<a href="javascript:;">
					<i class="glyphicon glyphicon-list-alt" style="padding:20px;font-size:20px" aria-hidden="true"></i>
						<span class="nav-text">Leave Report</span>
					<i class="icon-angle-right"></i><i class="icon-angle-down"></i>
				</a>
				<ul>
					<li>
						<a class="subnav-text" href="tpoliceleave.php">
							Traffic-Police
						</a>
					</li>
					<li>
						<a class="subnav-text" href="branchleave.php">
							Branch 
						</a>
					</li>
				</ul>
			</li>
			<li>
				<a href="uploaded_documents.php">
					<i class="fa fa-address-card" style="padding:20px;font-size:20px" aria-hidden="true"></i>
					<span class="nav-text">
						Verify Documents
					</span>
				</a>
			</li>
			<li>
				<a href="viewdriver.php">
					<i class="fa fa-car" style="padding:20px;font-size:20px" aria-hidden="true"></i>
					<span class="nav-text">
					Driver
					</span>
				</a>
			</li>
			<li class="has-subnav">
				<a href="javascript:;">
					<i class="fa fa-list-ul" aria-hidden="true"></i>
					<span class="nav-text">Extras</span>
					<i class="icon-angle-right"></i><i class="icon-angle-down"></i>
				</a>
				<ul>
					<li>
						<a class="subnav-text" href="viewtrafficpolice.php">Traffic Police</a>
					</li>
					<li>
						<a class="subnav-text" href="rating.php">Application Rating</a>
					</li>
					<li>
						<a class="subnav-text" href="faqs.php">FAQ</a>
					</li>
					<li>
						<a class="subnav-text" href="aboutus.php">About Us</a>
					</li>
				</ul>
			</li>
		</ul>
		<ul class="logout">
			<li>
			<a href="logout.php">
			<i class="icon-off nav-icon"></i>
			<span class="nav-text">
			Logout
			</span>
			</a>
			</li>
		</ul>
	</nav>
	<section class="wrapper scrollable">
		<nav class="user-menu">
			<a href="javascript:;" class="main-menu-access">
			<i class="icon-proton-logo"></i>
			<i class="icon-reorder"></i>
			</a>
		</nav>
		<section class="title-bar">
			<div class="logo">
				<h1><a style="color: #11A8BB; font-family: Algerian;" href="aboutus.php"><img src="images/logo.png" style="margin-left: auto; margin-right: auto; display: block; width: 50%;" height="100px" Width="100px" alt="logo"/> Smart Lens</a></h1>
			</div>
			<div class="w3l_search">
				<form action="search.php" method="post">
					<input type="text" name="search" placeholder="Search" required="">
					<button class="btn btn-default" type="submit"><i class="fa fa-search" aria-hidden="true"></i></button>
				</form><br>
				<span style="color: red"><?php if(@$_REQUEST['msg']=='fail'){echo "Sorry, Result not Found.";} ?></span>
			</div>
			<div class="header-right">
				<div class="profile_details_left">
					<div class="header-right-left">
						<!--notifications of menu start -->
						<ul class="nofitications-dropdown">
						<li class="dropdown head-dpdn">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i><span class="badge">3</span></a>
								<ul class="dropdown-menu anti-dropdown-menu w3l-msg">
									<li>
										<div class="notification_header">
											<h3>Latest Circular</h3>
										</div>
									</li>
									<?php
										$q="select * from circular ORDER BY c_id DESC";
										$rs=mysqli_query($cn,$q);
										for($i=0;$i<3&&$row=mysqli_fetch_array($rs);$i++)
										{
									?>
											<li>
												<a href="<?php print $row['file']; ?>" target="_blank">
											  		<div class="user_img"><i class="fa fa-envelope-square" style="color: #32C8A3; margin-top: 10px; font-size:30px"></i></div>
											   			<div class="notification_desc">
														<p><?php if(strlen($row['title'])<18){print $row['title'];} else {print substr($row['title'],0,17) . "..." ;}?></p>
														<p><span><?php echo date( 'd/m/Y', strtotime($row['date'])); ?></span></p>
													</div>
											   		<div class="clearfix"></div>	
												</a>
											</li>	
									<?php
										}
									?>
									<div class="notification_bottom">
										<a href="viewcircular.php">See all circular</a>
									</div>
								</ul>
							</li>	
							<li class="dropdown head-dpdn">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false"><i class="fa fa-tasks"></i><span class="badge blue1">3</span></a>
								<ul class="dropdown-menu anti-dropdown-menu agile-task">
									<li><a href="ratting.php">
										<div class="task-info">
											<span class="task-desc">Application Ratting</span><span class="percentage"><?php echo get_percentage($driver,$ratting).'%'; ?></span>
											<div class="clearfix"></div>	
										</div>
										<div class="progress progress-striped active">
											<div class="bar blue" style="width:<?php echo get_percentage($driver,$ratting).'%'; ?>;"></div>
										</div>
									</a></li>
									<li><a href="#">
										<div class="task-info">
											<span class="task-desc">Verified Documents</span><span class="percentage"><?php echo get_percentage($document,$vdocument).'%'; ?></span>
										   <div class="clearfix"></div>	
										</div>
										<div class="progress progress-striped active">
											 <div class="bar green" style="width:<?php echo get_percentage($document,$vdocument).'%'; ?>;"></div>
										</div>
									</a></li>
									<li><a href="uploaded_documents.php">
										<div class="task-info">
											<span class="task-desc">Unverified Documents</span><span class="percentage"><?php echo get_percentage($document,$pdocument).'%'; ?></span>
											<div class="clearfix"></div>	
										</div>
									   <div class="progress progress-striped active">
											 <div class="bar red" style="width: <?php echo get_percentage($document,$pdocument).'%'; ?>;"></div>
										</div>
									</a></li>
								</ul>
							</li>	
							<div class="clearfix"> </div>
						</ul>
					</div>	
					<div class="profile_details">		
						<ul>
							<li class="dropdown profile_details_drop">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
									<div class="profile_img">	
										<span class="prfil-img"><i class="fa fa-user" aria-hidden="true"></i></span> 
										<div class="clearfix"></div>	
									</div>	
								</a>
								<ul class="dropdown-menu drp-mnu">
									<li> <a href="Profile.PHP"><i class="fa fa-user"></i> Profile</a> </li> 
									<li> <a href="logout.php"><i class="icon-off"></i> Logout</a> </li>
								</ul>
							</li>
						</ul>
					</div>
					<div class="clearfix"> </div>
				</div>
			</div>
			<div class="clearfix"> </div>
		</section>
