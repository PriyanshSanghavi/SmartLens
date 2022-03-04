<?php
	include_once("header.php");
	include("connect.php");
	$q="select * from ratting where star = '1'";
	$r=mysqli_query($cn,$q);
	$s1 = mysqli_num_rows($r);
	$q="select * from ratting where star = '2'";
	$r=mysqli_query($cn,$q);
	$s2 = mysqli_num_rows($r);
	$q="select * from ratting where star = '3'";
	$r=mysqli_query($cn,$q);
	$s3 = mysqli_num_rows($r);
	$q="select * from ratting where star = '4'";
	$r=mysqli_query($cn,$q);
	$s4 = mysqli_num_rows($r);
	$q="select * from ratting where star = '5'";
	$r=mysqli_query($cn,$q);
	$s5 = mysqli_num_rows($r);
?>	
<head>
<title>Application Rating</title>
</head>
<div class="main-grid">
	<div class="agile-grids">
		<div class="w3l-chart events-chart">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">APPLICATION RATING</h2>
			</div>
			<div class="banner">
				<h2>
					<a href="home_admin.php">Home</a>
					<i class="fa fa-angle-right"></i>
					<span>Extras</span>
					<i class="fa fa-angle-right"></i>
					<span>View Users Rating</span>
				</h2>
			</div><br>
			<div align="center" id="graph2"></div>
		</div>
		<div class="panel panel-widget forms-panel">
			<div class="forms">
				<div class="form-grids widget-shadow" data-example-id="basic-forms"> 
					<div class="form-body">
						<table class="table table-hover">
								<tr>
							<thead>
									<th>User Name</th>
									<th>Given Star</th>
									<th>feed Back</th>
									<th>Date</th>
							</thead>
								</tr>
								<tbody>
								<?php
									$q="select * from ratting ORDER BY r_id DESC";
									$rs=mysqli_query($cn,$q);
									while($row=mysqli_fetch_array($rs))
									{
										$ans = $row['user_id'];
										$star = $row['star'];
										$nstar = 5 - $star;	
										$q1="select * from register_user where user_id = '$ans'";
										$r=mysqli_query($cn,$q1);
										$user=mysqli_fetch_array($r);
								?>
									<tr align="center">
										<td><?php print $user['name'];?></td>
										<td><?php  for($i=0 ; $i<$star; $i++)
										{ echo "<i class='fa fa-star' style='color:#32C8A3;'></i>";}
										for($j=0;$j<$nstar;$j++) 
										{ echo "<i class='far fa-star'style='color:#32C8A3;'></i>";}
										?></td>
										<td><?php print $row['msg'];?></td>
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
</div>
<script>
			$(function(){
				$('#graph2').graphify({
					start: 'donut',
					obj: {
						id: 'lol',
						legend: false,
						showPoints: true,
						width: 775,
						legendX: 450,
						pieSize: 200,
						shadow: true,
						height: 400,
						animations: true,
						x: ["1 Star","2 Star","3 Star","4 Star","5 Star"],
						points: [<?php echo $s1.",".$s2.",".$s3.",".$s4.",".$s5; ?>],
						xDist: 90,
						scale: 12,
						yDist: 35,
						grid: false,
						xName: 'Star',
						colors: ['Red','orange','yellow','blue','Green'],
						dataNames: ['Ratting'],
						design: {
							lineColor: 'red',
							tooltipFontSize: '20px',
							pointColor: 'red',
							barColor: 'blue',
							areaColor: 'orange'
						}
					}
				});
				var bar = new GraphBar({
					attachTo: '#graph3',
					special: 'combo',
					height: 600,
					width: 620,
					yDist: 50,
					xDist: 70,
				});
				bar.init();
			});
		</script>
		
<script src="js/graph.js"></script>	
<?php
include("footer.php");
?>