<?php
include("header.php");
include("connect.php");
	$ps_id = $_SESSION['ps_id'];
 	if(@$_REQUEST['permission']=="approved")
 	{
		$a=$_REQUEST['leave'];
		$q="update tp_leave set permission='approved' where l_id='$a'";
		mysqli_query($cn,$q);
 	}
 	if(@$_REQUEST['permission']=="reject")
 	{
 		$a=$_REQUEST['leave'];
 		$q="update tp_leave set permission='reject' where l_id='$a'";
		mysqli_query($cn,$q);
 	}
?>
<head>
	<title>Leave Request</title>
</head>		
<div class="main-grid">
	<div class="agile-grids">	
		<!-- reply-request -->
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">Leave Request</h2>
			</div>
			<div class="banner">
					<h2>
						<a href="home_branch.php">Home</a>
						<i class="fa fa-angle-right"></i>
						<span>Leave Report</span>
						<i class="fa fa-angle-right"></i>
						<span>Reply Request</span>
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
										<th>Type</th>
										<th>Half/Full</th>
										<th>From Date</th>
										<th>TO Date</th>
										<th>Reason</th>
										<th>Approved</th>
										<th>Reject</th>
									</thead>
								</tr>
								<?php
									$q="select * from traffic_police where ps_id='$ps_id'";
									$r=mysqli_query($cn,$q);
									while($tpolice=mysqli_fetch_array($r))
									{
										$tp=$tpolice['tp_id'];
										$q1="select * from tp_leave where permission = 'pending' && tp_id = '$tp' ORDER BY from_date ASC";
										$rs=mysqli_query($cn,$q1);
										while($row=mysqli_fetch_array($rs))
										{
											$leave=$row['l_id'];
									
								?>
								<tbody>
									<tr align="center">

										<td><?php print $tpolice['name'];?></td>
										<td><?php print $row['type'];?></td>
										<td><?php print $row['day_type'];?></td>
										<td><?php print date( 'd/m/Y', strtotime($row['from_date']));?></td>
										<td><?php print date( 'd/m/Y', strtotime($row['to_date']));?></td>
										<td><?php print $row['reason'];?></td>
										<td><a href="reply_request.php?permission=approved&leave=<?php echo $leave; ?>"><i class="fa fa-check-circle" style="font-size:24px;color:green"></i></a></td>
										<td><a href="reply_request.php?permission=reject&leave=<?php echo $leave; ?>"><i class="fa fa-minus-circle" style="font-size:24px;color:red"></i></a></td>
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
		</div>
		<!-- //reply-request -->
	</div>
</div>
		<!-- footer -->
<?php
include("footer.php");
?>