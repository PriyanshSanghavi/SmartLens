<?php
include("connect.php");
$user_id = $_REQUEST['id'];
$q="select * from memo where user_id = '$user_id' ORDER BY m_id DESC";
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	$response = array();
	while ($row = mysqli_fetch_assoc($rs)) {
		$response[] =$row;
	}
	echo json_encode($response);
?>