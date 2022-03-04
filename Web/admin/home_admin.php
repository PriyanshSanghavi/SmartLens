<?php
include("header.php");
include("connect.php");
$branch = $trafficpolice = $total = 0;
$attendance = $leave = $circular = $uploaddocuments = 0;
$amount = $amount_due = $amount_received = 0;
$q="select * from document where verification = 'approved'";
$rs=mysqli_query($cn,$q);	
$adocument = mysqli_num_rows($rs);
$q="select * from document where verification = 'reject'";
$rs=mysqli_query($cn,$q);	
$rdocument = mysqli_num_rows($rs);
$pdoc = 100 - get_percentage($document,$adocument) - get_percentage($document,$rdocument);
$q="select * from police_station";
$rs=mysqli_query($cn,$q);	
$branch = mysqli_num_rows($rs);
$q="select * from traffic_police where email != ''";
$rs=mysqli_query($cn,$q);	
$trafficpolice = mysqli_num_rows($rs);
$total = $branch + $trafficpolice + $driver;
$q="select * from tp_attendance";
$rs=mysqli_query($cn,$q);	
$attendance = mysqli_num_rows($rs);
$q="select * from tp_leave where permission ='approved'";
$rs=mysqli_query($cn,$q);	
$leave = mysqli_num_rows($rs);
$q="select * from circular";
$rs=mysqli_query($cn,$q);	
$circular = mysqli_num_rows($rs);
$q="select * from memo";
$rs=mysqli_query($cn,$q);	
$total_memo = mysqli_num_rows($rs);
while($row=mysqli_fetch_array($rs))
{
	$amount = $amount + $row['amount'];
}
$q="select * from memo where p_status=1";
$rs=mysqli_query($cn,$q);	
while($row=mysqli_fetch_array($rs))
{
	$amount_received =$amount_received + $row['amount'];
}
$amount_due = $amount - $amount_received ; 
?>
<head>
<title>Admin Home</title>
</head>
<div class="main-grid">
	<div class="agile-grids">
		<div class="w3l-chart events-chart">
			<div class="banner">
				<h2>
					<a href="#">Home</a>
				</h2>
			</div><br>
			<div class="codes">
				<div class="agile-container">
					<div class="grid_3 grid_4">
						<div class="panel panel-widget forms-panel">
						<div class="grid_3 grid_5 wow fadeInUp animated" data-wow-delay=".5s">
							<div class="row">
								<div class="col-md-6">
									<div align="center" style="margin-top: 5%; margin-bottom: 2%;">
										<h1 style="color: #14C1d7; font-family: Algerian;"><B>Users</B></h1>
									</div>
									<div style="margin-left: 10px; padding-right: 10px;"><br>
										<table class="table table-bordered">
											<thead>
												<tr>
													<th style="font-family: Arial Black; font-style: bold;">Users</th>
													<th style="font-family: Arial Black; font-style: bold;">Count</th>
												</tr>
											</thead>
											<tbody>
												<tr>
													<td style="font-family: Arial;"><a href="viewbranch.php" style="color: black;">Branches</a></td>
													<td><span class="badge" style="color: white; background-color: #40E0D0;"><?php echo $branch; ?></span></td>
												</tr>
												<tr>
													<td style="font-family: Arial;"><a href="viewtrafficpolice.php" style="color: black;">Traffic-Police</a></td>
													<td><span class="badge" style="color: white; background-color: #F5B041;"><?php echo $trafficpolice; ?></span></td>
												</tr>
												<tr>
													<td style="font-family: Arial;"><a href="viewdriver.php" style="color: black;">Drivers</a></td>
													<td><span class="badge" style="color: white; background-color: #3498DB;"><?php echo $driver; ?></span></td>
												</tr>
												<tr>
													<td style="font-family: Arial; color: black;">Total User</td>
													<td><span class="badge" style="color: white; background-color: #2ECC71;"><?php echo $total; ?></span></td>
												</tr>
											</tbody>
										</table>                    
									</div>
								</div>
								<div class="col-md-6">
									<div align="center" style=" margin-top: 5%; margin-bottom: 2%;">
										<h1 style="color: #14C1d7; font-family: Algerian;"><B>Activitys</B></h1>
									</div>
									<div>
										<div class="list-group list-group-alternate"  style="margin-left: 10px; margin-right: 10px;"> 
											<a href="tpoliceattendance.php" class="list-group-item"><span class="badge" style="color: white; font-family: Arial; background-color: #2ECC71;"><?php echo $attendance; ?></span>Attendance</a> 
											<a href="tpoliceleave.php" class="list-group-item"><span class="badge" style="color: white; font-family: Arial; background-color: #FF5D25;"><?php echo $leave; ?></span>Leave</a> 
											<a href="viewcircular.php" class="list-group-item"><span class="badge" style="color: white; font-family: Arial; background-color: #F1C40F;"><?php echo $circular; ?></span>Circlar</a> 
											<a href="upload_documents.php" class="list-group-item"><span class="badge" style="color: white; font-family: Arial; background-color: #40E0D0;"><?php echo $document; ?></span>Uploaded Documents</a> 
											<a href="tpolicememo.php" class="list-group-item"><span class="badge" style="color: white; font-family: Arial; background-color: #8E44AD;"><?php echo $total_memo; ?></span>Memo</a> 
											<a href="ratting.php" class="list-group-item"><span class="badge" style="color: white; font-family: Arial; background-color: #3498DB;"><?php echo $ratting; ?></span>Ratting</a> 
										</div>
									</div>
								</div>
							</div>
						    <div class="clearfix"> </div>
						</div>
							<div class="grid_3 grid_5 wow fadeInUp animated" data-wow-delay=".5s">
								<h2 class="hdg" style="margin-left: 50px; padding-top: 50px; font-family: Algerian; color: #14C1d7;" ><b>Uploaded Documents :</b></h2><br>
								<div class="tab-content">
									<div class="tab-pane active" style="margin-right: 50px; margin-left: 50px;" id="domprogress">
										<div class="progress" style="height: 20px;">
											<div class="progress-bar progress-bar-success" style="width: <?php echo get_percentage($document,$adocument).'%'; ?>"><span><?php echo get_percentage($document,$adocument).'%'; ?> Verified Documents</span></div>
											<div class="progress-bar progress-bar-warning" style="width: <?php echo $pdoc.'%'; ?>"><span><?php echo $pdoc.'%'; ?> Pending</span></div>
											<div class="progress-bar progress-bar-danger" style="width: <?php echo get_percentage($document,$rdocument).'%'; ?>"><span><?php echo get_percentage($document,$rdocument).'%'; ?> Rejected Documents</span></div>
										</div>
									</div>
								</div>
							</div>
							<div class="form-body" style="padding-top: 50px;" align="center">
								<div class="row" align="left" style="color: #14C1d7; font-family: Algerian; margin-top: 30px;"><h2 style=" margin-left: 50px;"><b>Memo :</b></h2>
								</div><br>
								<div class="row">
									<div class="col-md-4">
										<div style="color: #7A67BB; font-family: Times New Roman;"><h1><b>Total Memo</b></h1></div>
										<div class="count" style="font-size: 50px; color: #7A67BB;"><?php echo $total_memo;?></div>
									</div>
									<div class="col-md-4">
										<div style="color: #47D1AF; font-family: Times New Roman;"><h1><b>Payment Received</b></h1></div>
										<div  style="font-size: 50px; color: #47D1AF;">
											<div class="col-md-6" align="right">Rs.</div>
											<div class="count col-md-6" align="left"><?php echo $amount_received ?></div>
										</div>
									</div>
									<div class="col-md-4">
										<div style="color: #FF7444; font-family: Times New Roman;"><h1><b>Payment Due</b></h1></div>
										<div class="row" style="font-size: 50px; color: #FF7444;">
											<div class="col-md-6" align="right">Rs.</div>
											<div class="count col-md-6" align="left"><?php echo $amount_due; ?></div>
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
<script type="text/javascript">
	$('.count').each(function () {
	    $(this).prop('Counter',0).animate({
	        Counter: $(this).text()
	    }, {
	        duration: 4000,
	        easing: 'swing',
	        step: function (now) {
	            $(this).text(Math.ceil(now));
	        }
	    });
	});
</script>
	<!-- footer -->
<?php
	include("footer.php");
?>