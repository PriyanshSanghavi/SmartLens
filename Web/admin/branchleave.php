<?php
include("header.php");
include("connect.php");
if($_SERVER["REQUEST_METHOD"] == "POST")
{
	$fromdate = $_POST['fromdate'];
	$todate = $_POST['todate']; 
}
?>
<head>
<title>Branch Leave</title>
</head>	
<div class="main-grid">
	<div class="agile-grids">	
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">Branch Leave</h2>
			</div>
			<div class="banner">
					<h2>
						<a href="home_admin.php">Home</a>
						<i class="fa fa-angle-right"></i>
						<span>Leave Report</span>
						<i class="fa fa-angle-right"></i>
						<span>Branch Leave</span>
					</h2>
				</div><br>
				<div class="w3agile-validation w3ls-validation">
					<div class="panel panel-widget agile-validation">
						<div class="validation-grids widget-shadow" data-example-id="basic-forms">
							<div class="form-body form-body-info">
								<form data-toggle="validator" novalidate="true" action="#" method="post">
								<div class="form-group valid-form">
									<label >Branch</label><label style="color: red;">*</label>
									<select name="branch" class="form-control" id="branch" data-error="Please, Select Branch from the list." data-required=" " required="true">
										<option value=""></option>
									<?php
									$q="select * from police_station";
									$res=mysqli_query($cn,$q);	
									while($branch=mysqli_fetch_array($res))
										{ ?>
											<option value="<?php print $branch['ps_id']; ?>"<?php
											if ($_SERVER["REQUEST_METHOD"] == "POST") { 
											echo (@$_POST['branch']==$branch['ps_id']) ? "selected = 'selected'" : '';} ?>><?php print $branch['name'];?></option>
									<?php	} ?>
									</select>
									<span class="help-block with-errors"></span>
								</div>		
								<div class="row">
									<div class="form-group col-md-6">
											<label >From Date</label><label style="color: red;">*</label>
											<input type="Date" name="fromdate" value="<?php if($_SERVER["REQUEST_METHOD"] == "POST") {echo $_POST['fromdate']; }?>" class="form-control" required="">
									</div>
									<div class="form-group col-md-6">
										<label >To Date</label><label style="color: red;">*</label>
										<input type="Date" name="todate" value="<?php if($_SERVER["REQUEST_METHOD"] == "POST") {echo $_POST['todate']; }?>" class="form-control" required="">
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
				$ans=$_POST['branch'];
			?>
			<div class="panel panel-widget forms-panel">
				<div class="forms">
					<div class="form-grids widget-shadow" data-example-id="basic-forms"> 
						<div class="form-body">
							<table class="table table-hover">
									<tr>
								<thead>
										<th>Name</th>
										<th>Reason</th>
										<th>Type</th>
										<th>Half/Full</th>
										<th>From Date</th>
										<th>To Date</th>
								</thead>
									</tr>
									<tbody>
										<?php
											$q ="select tp_id from traffic_police where ps_id = '$ans'";
											$re=mysqli_query($cn,$q);
											while($tp=mysqli_fetch_array($re))
											{
												$tp_id = $tp['tp_id'];
											$q="select * from tp_leave where tp_id = '$tp_id' && permission = 'approved' && (from_date between '$fromdate' and '$todate' || to_date between '$fromdate' and '$todate' || from_date <= '$fromdate' and  to_date>='$todate')";
											$rs=mysqli_query($cn,$q);
											while($row=mysqli_fetch_array($rs))
											{
												$tp_id = $row['tp_id'];
												$q1="select * from traffic_police where tp_id = '$tp_id'";
												$r=mysqli_query($cn,$q1);
												$tpolice=mysqli_fetch_array($r);
										?>
										<tr align="center">
											<td><?php print $tpolice['name'];?></td>
											<td><?php print $row['reason'];?></td>
											<td><?php print $row['type'];?></td>
											<td><?php print $row['day_type'];?></td>
											<td><?php echo date( 'd/m/Y', strtotime($row['from_date'])); ?></td>
											<td><?php echo date( 'd/m/Y', strtotime($row['to_date'])); ?></td>
										</tr>
										<?php
											}}
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
		<!-- footer -->
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