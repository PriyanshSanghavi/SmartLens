<?php
include("connect.php");
$tp_id = $_REQUEST['id'];
$q="select * from memo where tp_id = '$tp_id' ORDER BY m_id DESC";
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	$response = array();
	while ($row = mysqli_fetch_assoc($rs)) {
		$response[] =$row;
	}
	echo json_encode($response);
?>