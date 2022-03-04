<?php
include("header.php");
include("connect.php");
if(isset($_GET['tp_id']))
{
	$tp_id=$_GET['tp_id'];
	$qd="update traffic_police set ps_id = NULL where tp_id='$tp_id'";
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
				<h2 style=" font-family: Algerian;">TRAFFIC POLICE</h2>
			</div>
			<div class="banner">
					<h2>
						<a href="home_branch.php">Home</a>
						<i class="fa fa-angle-right"></i>
						<span>Traffic Police</span>
					</h2>
				</div><br>
				<div class="panel panel-widget forms-panel">
				<div class="forms">
					<div class="form-grids widget-shadow" data-example-id="basic-forms"> 
						<div class="form-body">
							<div style="text-align: center;">
								<?php
								 	if(@$_REQUEST['msg']=="success") 
								 		{echo "<span style='color: green;'>"."<B>Data update Successfully !!!</B>"."</span>";} 
								?>
							</div><br>
							<table class="table table-hover">
								<tr>
									<thead>
											<th>ID</th>
											<th>Name</th>
											<th>Gender</th>
											<th>Birth Date</th>
											<th>Join Date</th>
											<th>Email</th>
											<th>Contact No</th>
											<th>Address</th>
											<th>City</th>
											<th>State</th>
											<th>Edit</th>
									</thead>
								</tr>
								<tbody>
								<?php
									$q="select * from traffic_police";
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
										<td><?php print $row['email'];?></td>
										<td><?php print $row['contact_no'];?></td>
										<td><?php print $row['address'];?></td>
										<td><?php print $row['city'];?></td>
										<td><?php print $row['state'];?></td>
										<td><a href="edit_tpolice.php?tp_id=<?php print $row['tp_id'];?>"><i style="font-size:20px" class="fa fa-edit"></i></a></td>
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