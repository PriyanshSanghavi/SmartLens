<?php
include("header.php");
include("connect.php");
$tp_id = @$_REQUEST['tp_id'];
$name = @$_REQUEST['tname'];
$fromdate = @$_REQUEST['fromdate'];
$todate = @$_REQUEST['todate'];
?>
<head>
<title>Traffic Police Memo</title>
</head>	
<div class="main-grid">
	<div class="agile-grids">
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">Traffic Police Memo</h2>
			</div>
			<div class="banner">
					<h2>
						<a href="home_admin.php">Home</a>
						<i class="fa fa-angle-right"></i>
						<span>Memo Report</span>
						<i class="fa fa-angle-right"></i>
						<span>Traffic Police Memo</span>
					</h2>
				</div><br>
			<div class="w3agile-validation w3ls-validation">
				<div class="panel panel-widget agile-validation">
					<div class="validation-grids widget-shadow" data-example-id="basic-forms">
						<div class="form-body form-body-info">
							<form method="post" action="tpolicememo_code.php" data-toggle="validator" novalidate="true">
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
											$q1="select * from memo where tp_id = '$tp_id' && date between '$fromdate' and '$todate'";
											$rs=mysqli_query($cn,$q1);
											while($row=mysqli_fetch_array($rs))
											{
												$user = $row['user_id']; 
												$memo_id = $row['m_id'];
												$q2="select * from register_user where user_id = '$user' ";
												$res=mysqli_query($cn,$q2);
												$user =mysqli_fetch_array($res);
												$q2="select * from payment where m_id = '$memo_id'";
												$result2=mysqli_query($cn,$q2);
										?>
											<tr align="center">
												<td><?php print $name;?></td>
												<td><?php print $user['name'];?></td>
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
