<?php
	include("connect.php");
	date_default_timezone_set("Asia/Calcutta");
	$l_id = $_REQUEST['id'];
	$q="select * from tp_leave where l_id = '$l_id'";
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	if($row = mysqli_fetch_array($rs)) {
		$fromdate = strtotime($row['from_date']);
		$date = strtotime(date('Y-m-d'));

		if($date<$fromdate)
		{
			$q="delete from tp_leave where l_id = '$l_id'";
			$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
			http_response_code(200);
			$response['status']="true";
			echo json_encode($response);
		}
		else
		{
			$response['status']="false";
			echo json_encode($response);
		}
	}
?>