<?php
include("connect.php");
$tp_id = $_REQUEST['id'];
$fromdate = $_REQUEST['fromdate'];
$todate = $_REQUEST['todate'];
$q="select * from tp_attendance where tp_id = '$tp_id' && date between '$fromdate' and '$todate'";
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	$response = array();
	while ($row = mysqli_fetch_assoc($rs)) {
		$response[] =$row;
	}
	echo json_encode($response);
?>