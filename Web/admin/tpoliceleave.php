<?php
include("header.php");
include("connect.php");
$tp_id = @$_REQUEST['tp_id'];
$name = @$_REQUEST['tname'];
$ps = @$_REQUEST['ps'];
$fromdate = @$_REQUEST['fromdate'];
$todate = @$_REQUEST['todate'];
?>
<head>
<title>Traffic Police Leave</title>
</head>	
<div class="main-grid">
	<div class="agile-grids">
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">Traffic Police Leave</h2>
			</div>
			<div class="banner">
					<h2>
						<a href="home_admin.php">Home</a>
						<i class="fa fa-angle-right"></i>
						<span>Leave Report</span>
						<i class="fa fa-angle-right"></i>
						<span>Traffic Police Leave</span>
					</h2>
				</div><br>
			<div class="w3agile-validation w3ls-validation">
				<div class="panel panel-widget agile-validation">
					<div class="validation-grids widget-shadow" data-example-id="basic-forms">
						<div class="form-body form-body-info">
							<form method="post" action="tpoliceleave_code.php" data-toggle="validator" novalidate="true">
								<div class="form-group valid-form">
									<div style="text-align: center;">
										<?php
										 	if(@$_REQUEST['msg']=="failed") 
										 		{echo "<span style='color: red;'>"."<B>Oops! Traffic Police Data is Invalid.</B>"."</span><br>";}
										?>
									</div>
									<label >Traffic Police Detail</label><label style="color: red;">*</label>
									<div class="form-group">
										<input type="text" name="tpolice" value="<?php if(@$_REQUEST['msg'] != 'failed') echo @$_REQUEST['tpolice']; ?>" placeholder="Enter Traffic-Police ID or Email or Contact No." data-error="Please, Enter Valid Data" required="true" class="form-control">
									<span class="help-block with-errors"></span>
									</div>
								</div>
								<div class="row">
									<div class="form-group col-md-6">
											<label >From Date</label><label style="color: red;">*</label>
											<input type="Date" name="fromdate" value="<?php echo @$_REQUEST['fromdate']; ?>" class="form-control" required="true">
									</div>
									<div class="form-group col-md-6">
										<label >To Date</label><label style="color: red;">*</label>
										<input type="Date" name="todate" value="<?php echo @$_REQUEST['todate']; ?>" class="form-control" required="true">
									</div>
								</div>
								<div class="form-group">
									<button type="submit" class="btn btn-default w3ls-button">SHOW</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<?php
				if (isset($tp_id))
				{
			?>
				<div class="panel panel-widget forms-panel">
				<div class="forms">
					<div class="form-grids widget-shadow" data-example-id="basic-forms"> 
						<div class="form-body">
							<table class="table table-hover">
									<tr>
								<thead>
										<th>Name</th>
										<th>Police Station</th>
										<th>Reason</th>
										<th>Type</th>
										<th>Half/Full</th>
										<th>From Date</th>
										<th>To Date</th>
								</thead>
									</tr>
									<tbody>
										<?php
											$q="select * from tp_leave where tp_id = '$tp_id' && permission = 'approved' && (from_date between '$fromdate' and '$todate' || to_date between '$fromdate' and '$todate' || from_date <= '$fromdate' and  to_date>='$todate')";
											$rs=mysqli_query($cn,$q);
											while($row=mysqli_fetch_array($rs))
											{
										?>
										<tr align="center">
											<td><?php print $name; ?></td>
											<td><?php print $ps; ?></td>
											<td><?php print $row['reason'];?></td>
											<td><?php print $row['type'];?></td>
											<td><?php print $row['day_type'];?></td>
											<td><?php echo date( 'd/m/Y', strtotime($row['from_date'])); ?></td>
											<td><?php echo date( 'd/m/Y', strtotime($row['to_date'])); ?></td>
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
