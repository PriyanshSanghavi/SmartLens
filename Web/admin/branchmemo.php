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
<title>Branch Memo</title>
</head>
<div class="main-grid">
	<div class="agile-grids">
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">Branch Memo</h2>
			</div>
			<div class="banner">
					<h2>
						<a href="home_admin.php">Home</a>
						<i class="fa fa-angle-right"></i>
						<span>Memo Report</span>
						<i class="fa fa-angle-right"></i>
						<span>Branch Memo</span>
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
										<th>Traffic Police Name</th>
										<th>Driver Name</th>
										<th>Vehical Number</th>
										<th>Title</th>
										<th>Amount</th>
										<th>Date</th>
										<th>Time</th>
										<th>Area</th>
										<th>City</th>
										<th>Payment</th>
								</thead>
									</tr>
									<tbody>
										<tr align="center">
										<?php
											$q ="select tp_id from traffic_police where ps_id = '$ans'";
											$re=mysqli_query($cn,$q);
											while($tp=mysqli_fetch_array($re))
											{
												$tp = $tp['tp_id'];
											$q1="select * from memo where tp_id = '$tp' && date between '$fromdate' and '$todate'";
											$r=mysqli_query($cn,$q1);
											while($row=mysqli_fetch_array($r))
											{
												$user_id = $row['user_id'];
												$memo_id = $row['m_id'];
												$tp_id = $row['tp_id'];
												$q = "select * from register_user where user_id = '$user_id'";
												$result =mysqli_query($cn,$q);
												$driver=mysqli_fetch_array($result);
												$q1 = "select * from traffic_police where tp_id = '$tp_id'";
												$result1 =mysqli_query($cn,$q1);
												$tpolice=mysqli_fetch_array($result1);
												$q2="select * from payment where m_id = '$memo_id'";
												$result2=mysqli_query($cn,$q2);
										?>
											<tr align="center">
												<td><?php print $tpolice['name'];?></td>
												<td><?php print $driver['name'];?></td>
												<td><?php print $row['vehical_no'];?></td>
												<td><?php print $row['title'];?></td>
												<td><?php print $row['amount'];?></td>
												<td><?php echo date( 'd/m/Y', strtotime($row['date'])); ?></td>
												<td><?php print $row['time'];?></td>
												<td><?php print $row['area'];?></td>
												<td><?php print $row['city'];?></td>
												<td><?php if(mysqli_fetch_array($result2)){echo "Recieved";} else {echo "Due";} ?></td>
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