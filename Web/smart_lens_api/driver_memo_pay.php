<?php
	include("connect.php");
	date_default_timezone_set("Asia/Calcutta");
	$id = $_REQUEST['id'];
	$type = $_REQUEST['type'];
	$time = date("h:i A");

	$q="insert into payment (m_id,type,date,time) values ('$id','$type',now(),'$time')";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
			$q1="update memo set p_status=1 where m_id='$id'";
			$rs1=mysqli_query($cn,$q1) or die(mysqli_error($cn));
			http_response_code(200);
			$response['status']=true;
			echo json_encode($response);
?>