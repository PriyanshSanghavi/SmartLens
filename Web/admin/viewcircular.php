<?php
include("header.php");
include("connect.php");
?>
<head>
<title>View Circular</title>
</head>
<div class="main-grid">
	<div class="agile-grids">	
		<!-- input-forms -->
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">CIRCULAR</h2>
			</div>
			<div class="banner">
					<h2>
						<a href="home_admin.php">Home</a>
						<i class="fa fa-angle-right"></i>
						<span>Circular</span>
						<i class="fa fa-angle-right"></i>
						<span>View</span>
					</h2>
				</div><br>
				<div class="panel panel-widget forms-panel">
				<div class="forms">
					<div class="form-grids widget-shadow" data-example-id="basic-forms"> 
						<div class="form-body">
							<table class="table table-hover">
								<tr>
									<thead>
										<th>Title</th>
										<th>File Link</th>
										<th>Date</th>
									</thead>
								</tr>
								<tbody>
									<?php
										$q="select * from circular ORDER BY c_id DESC";
										$rs=mysqli_query($cn,$q);
										while($row=mysqli_fetch_array($rs))
										{
									?>
										<tr align="center">
											<td><?php print $row['title'];?></td>
											<td><a href="<?php print $row['file']; ?>" target="_blank">File</a></td>
											<td><?php echo date( 'd/m/Y', strtotime($row['date'])); ?></td>
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