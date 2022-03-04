<?php
include("header.php");
include("connect.php");
date_default_timezone_set("Asia/Calcutta");
if(isset($_GET['tp_id']))
{
	$date = date('Y-m-d');
	$tp_id=$_GET['tp_id'];
	$qd="delete from tp_leave where tp_id='$tp_id' && ('$date'<from_date || '$date'<to_date)";
	mysqli_query($cn,$qd);
	$qd="Update traffic_police set ps_id=0,password='',email='', contact_no='' where tp_id='$tp_id'";
	mysqli_query($cn,$qd);
}
?>
<head>
<title>View Traffic Police</title>
</head>	
<div class="main-grid">
	<div class="agile-grids">	
		<!-- input-forms -->
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">Traffic Police</h2>
			</div>
			<div class="banner">
					<h2>
						<a href="home_admin.php">Home</a>
						<i class="fa fa-angle-right"></i>
						<span>Traffic Police</span>
					</h2>
				</div><br>
				<div class="panel panel-widget forms-panel">
				<div class="forms">
					<div class="form-grids widget-shadow" data-example-id="basic-forms"> 
						<div class="form-body">
							<table class="table table-hover">
									<tr>
								<thead>
										<th>ID</th>
										<th>Name</th>
										<th>Gender</th>
										<th>Birth Date</th>
										<th>Join Date</th>
										<th>Branch</th>							
										<th>Email</th>
										<th>Contact No</th>
										<th>Address</th>
										<th>City</th>
										<th>State</th>
										<th>Remove<th>
								</thead>
									</tr>
									<tbody>
									<?php
									$q="select * from traffic_police where email != ''";
									$rs=mysqli_query($cn,$q);
									while($row=mysqli_fetch_array($rs))
									{
								?>
									<tr align="center">
										<td><?php print $row['tp_id'];?></td>
										<td><?php print $row['name'];?></td>
										<td><?php print $row['gender'];?></td>
										<td><?php echo date( 'd/m/Y', strtotime($row['birthdate'])); ?></td>
										<td><?php echo date( 'd/m/Y', strtotime($row['joindate'])); ?></td>
										<td><?php $b=$row['ps_id']; 
										$q1 = "select * from police_station where ps_id = '$b'";
										 $r=mysqli_query($cn,$q1); 
										 $branch=mysqli_fetch_array($r); 
										 print $branch['name'];?></td>
										<td><?php print $row['email'];?></td>
										<td><?php print $row['contact_no'];?></td>
										<td><?php print $row['address'];?></td>
										<td><?php print $row['city'];?></td>
										<td><?php print $row['state'];?></td>
										<td><a href="viewtrafficpolice.php?tp_id=<?php print $row['tp_id'];?>"><i class="fa fa-minus-circle" style="font-size:24px;color:red"></i></a></td>
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
		</div>
		<!-- //input-forms -->
	</div>
</div>
		<!-- footer -->
<?php
include("footer.php");
?>