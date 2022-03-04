<?php
include("header.php");
include("connect.php");
$id=$_SESSION['ps_id'];
if($_SERVER["REQUEST_METHOD"] == "POST")
{
	$fromdate = $_POST['fromdate'];
	$todate = $_POST['todate']; 
}
?>
<head>
	<title>Traffic Police Attendance</title>
</head>		
<div class="main-grid">
	<div class="agile-grids">
		<div class="w3l-chart events-chart">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">ATTENDANCE</h2>
			</div>
			<div class="banner">
				<h2>
					<a href="home_branch.php">Home</a>
					<i class="fa fa-angle-right"></i>
					<span>Attendance Report</span>
				</h2>
			</div><br>
			<div class="w3agile-validation w3ls-validation">
				<div class="panel panel-widget agile-validation">
					<div class="validation-grids widget-shadow" data-example-id="basic-forms">
						<div class="form-body form-body-info">
							<form method="post" action="#" data-toggle="validator" novalidate="true">
								<div class="form-group valid-form">
									<label >Traffic Police</label><label style="color: red;">*</label>
									<select name="tpolice" class="form-control" id="tpolice" data-error="Please, Select Traffic Police from the list." data-required=" " required="true">
										<option value=""></option>
									<?php
									$q="select * from traffic_police where ps_id = '$id'";
									$res=mysqli_query($cn,$q);	
									while($tpolice=mysqli_fetch_array($res))
										{ ?>
											<option value="<?php print $tpolice['tp_id']; ?>"<?php
											if ($_SERVER["REQUEST_METHOD"] == "POST") { 
											echo (@$_POST['tpolice']==$tpolice['tp_id']) ? "selected = 'selected'" : '';} ?>><?php print $tpolice['name'];?></option>
									<?php	} ?>
									</select>
									<span class="help-block with-errors"></span>
								</div>
								<div class="row">		
									<div class="form-group col-md-6">
											<label >From Date</label><label style="color: red;">*</label>
											<input type="Date" name="fromdate" value="<?php if($_SERVER["REQUEST_METHOD"] == "POST") {echo $_POST['fromdate']; }?>" class="form-control" required="true">
									</div>
									<div class="form-group col-md-6">
										<label >To Date</label><label style="color: red;">*</label>
										<input type="Date" name="todate" value="<?php if($_SERVER["REQUEST_METHOD"] == "POST") {echo $_POST['todate']; }?>" class="form-control" required="true">
									</div>	
								</div>	
								<div class="form-group">
									<button type="submit" value="submit" class="btn btn-default w3ls-button">SHOW</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<?php
			if ($_SERVER["REQUEST_METHOD"] == "POST")
			{
				$ans=$_POST['tpolice'];
			?>
			<div class="panel panel-widget forms-panel">
				<div class="forms">
					<div class="form-grids widget-shadow" data-example-id="basic-forms"> 
						<div class="form-body">
							<table class="table table-hover">
									<tr>
								<thead>
										<th>Name</th>
										<th>Date</th>
										<th>In Time</th>
										<th>In Time Latitude</th>
										<th>In Time Longitude</th>
										<th>Out Time</th>
										<th>out Time Latitude</th>
										<th>out Time Longitude</th>
								</thead>
									</tr>
									<tbody>
										<?php
											$q1="select * from tp_attendance where tp_id = '$ans' && date between '$fromdate' and '$todate'";
											$rs=mysqli_query($cn,$q1);
											while($row=mysqli_fetch_array($rs))
											{
												$q="select * from traffic_police where tp_id = '$ans'";
												$res=mysqli_query($cn,$q);
												$tpolice=mysqli_fetch_array($res);

										?>
										<tr align="center">
											<td><?php print $tpolice['name'];?></td>
											<td><?php echo date( 'd/m/Y', strtotime($row['date'])); ?></td>
											<td><?php print $row['in_time'];?></td>
											<td><?php print $row['in_latitude'];?></td>
											<td><?php print $row['in_longitude'];?></td>
											<td><?php print $row['out_time'];?></td>
											<td><?php print $row['out_latitude'];?></td>
											<td><?php print $row['out_longitude'];?></td>
										</tr>
										<?php
											}
										?>
									</tbody>
							</table>
						</div>
					</div>
				</div>
			</div>
			<?php
				}
			?>
		</div>
	</div>
</div>
<?php
	include("footer.php");
?>
<!-- input-forms -->
		<script type="text/javascript" src="js/valida.2.1.6.min.js"></script>
		<script type="text/javascript" >
			$(document).ready(function() {
				$('.valida').valida();
			});
		</script>
		<!-- //input-forms -->
		<!--validator js-->
<script src="js/validator.min.js"></script>
		<!--//validator js-->