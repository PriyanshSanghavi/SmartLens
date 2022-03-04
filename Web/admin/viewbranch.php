<?php
include("header.php");
include("connect.php");
$delete=0;
$tp=0;
$branch=0;
if(isset($_GET['ps_id']))
{
	$ps_id=$_GET['ps_id'];
	$q = "select * from traffic_police where ps_id = '$ps_id'";
	$res=mysqli_query($cn,$q);
	$tp=mysqli_num_rows($res);
	if($tp==0)
	{
		$qd="delete from police_station where ps_id='$ps_id'";
		mysqli_query($cn,$qd);
		$branch=1;
	}
	else
	{
		$delete = 1;	
	}
}
?>
<head>
<title>View Branch</title>
</head>
<div class="main-grid">
	<div class="agile-grids">
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">BRANCHES</h2>
			</div>
			<div class="banner">
				<h2>
					<a href="home_admin.php">Home</a>
					<i class="fa fa-angle-right"></i>
					<span>Branch</span>
					<i class="fa fa-angle-right"></i>
					<span>View</span>
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
								 	if($delete==1) 
								 		{echo "<span style='color: red;'>"."<B>$tp Traffic Police Work in this Branch, First transfer this Police to Other Branch.</B>"."</span>";}  
								 	if($branch==1) 
								 		{echo "<span style='color: red;'>"."<B>Branch Remove Successfully.</B>"."</span>";} 
								?>
							</div><br>
							<table class="table table-hover">
									<tr>
								<thead>
										<th>Branch ID</th>
										<th>Name</th>
										<th>Email</th>
										<th>Contact No</th>
										<th>Address</th>
										<th>City</th>
										<th>State</th>
										<th>Edit</th>
										<th>Remove</th>
								</thead>
									</tr>
									<tbody>
										<?php
										$q="select * from police_station";
										$rs=mysqli_query($cn,$q);
										while($row=mysqli_fetch_array($rs))
										{
										?>	
										<tr align="center">
											<td><?php print $row['ps_id'];?></td>
											<td><?php print $row['name'];?></td>
											<td><?php print $row['email'];?></td>
											<td><?php print $row['contact_no'];?></td>
											<td><?php print $row['address'];?></td>
											<td><?php print $row['city'];?></td>
											<td><?php print $row['state'];?></td>
											<td><a href="edit_branch.php?ps_id=<?php print $row['ps_id'];?>"><i style="font-size:20px" class="fa fa-edit"></i></a></td>
											<td><a href="viewbranch.php?ps_id=<?php print $row['ps_id'];?>"><i class="fa fa-minus-circle" style="font-size:24px;color:red"></i></a></td>
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
	</div>
</div>
		<!-- footer -->
<?php
include("footer.php");
?>