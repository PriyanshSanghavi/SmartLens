<?php
include("connect.php");
$q="select * from circular ORDER BY c_id DESC";
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	$response = array();
	while ($row = mysqli_fetch_assoc($rs)) {
		$response[] =$row;
	}
	echo json_encode($response);
?>