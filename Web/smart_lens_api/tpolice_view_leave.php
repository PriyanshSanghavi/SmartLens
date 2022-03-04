<?php
	include("connect.php");
	$tp_id = $_REQUEST['id'];
	date_default_timezone_set("Asia/Calcutta");
	$fromdate = $_REQUEST['fromdate'];
	$todate = $_REQUEST['todate'];
	$q="select * from tp_leave where tp_id = '$tp_id' && (from_date between '$fromdate' and '$todate' || to_date between '$fromdate' and '$todate' || from_date <= '$fromdate' and  to_date>='$todate')";
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	$response = array();
	while ($row = mysqli_fetch_assoc($rs)) {
		$response[] =$row;
	}
	echo json_encode($response);
?>