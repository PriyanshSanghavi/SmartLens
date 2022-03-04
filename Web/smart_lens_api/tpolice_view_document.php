<?php
include("connect.php");
$user_id = $_REQUEST['user_id'];
$q="select * from document where user_id = '$user_id' && verification='approved'";
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	$response = array();
	while ($row = mysqli_fetch_assoc($rs)) {
		$response[] =$row;
	}
	echo json_encode($response);
?>