<?php
include("header.php");
include("connect.php");
?>
<head>
<title>View Driver</title>
</head>
<div class="main-grid">
	<div class="agile-grids">	
		<!-- input-forms -->
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">DRIVERS</h2>
			</div>
			<div class="banner">
					<h2>
						<a href="home_admin.php">Home</a>
						<i class="fa fa-angle-right"></i>
						<span>Drivers</span>
					</h2>
				</div><br>
				<div class="panel panel-widget forms-panel">
				<div class="forms">
					<div class="form-grids widget-shadow" data-example-id="basic-forms"> 
						<div class="form-body">
							<table class="table table-hover">
									<tr>
								<thead>
										<th>Name</th>
										<th>Gender</th>
										<th>Birth Date</th>
										<th>Email</th>
										<th>Contact No</th>
										<th>Address</th>
										<th>City</th>
										<th>State</th>
								</thead>
									</tr>
									<tbody>
									<?php
									$q="select * from register_user where email != ''";
									$rs=mysqli_query($cn,$q);
									while($row=mysqli_fetch_array($rs))
									{
								?>
									<tr align="center">
										<td><?php print $row['name'];?></td>
										<td><?php print $row['gender'];?></td>
										<td><?php echo date( 'd/m/Y', strtotime($row['birthdate'])); ?></td>
										<td><?php print $row['email'];?></td>
										<td><?php print $row['contact_no'];?></td>
										<td><?php print $row['address'];?></td>
										<td><?php print $row['city'];?></td>
										<td><?php print $row['state'];?></td>
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