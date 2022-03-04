<?php
include("connect.php");
date_default_timezone_set("Asia/Calcutta");
$date = strtotime(date('Y-m-d'));
$tp_id = $_REQUEST['tp_id'];
$type = $_REQUEST['type'];
$reason = $_REQUEST['reason'];
$from_date = $_REQUEST['from_date'];
$to_date = $_REQUEST['to_date'];
$day_type = $_REQUEST['day_type'];
$permission = 'pending';
if(strtotime($from_date)>strtotime($to_date))
{
	$response['status']="todate";
	echo json_encode($response);
}
elseif ($date>strtotime($from_date)) 
{
	$response['status']="fromdate";
	echo json_encode($response);
}
else
{
	$q="insert into tp_leave (tp_id,type,reason,from_date,to_date,day_type,permission) values ('$tp_id','$type','$reason','$from_date','$to_date','$day_type','$permission')";
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	http_response_code(200);
	$response['status']="true";
	echo json_encode($response);
}
?>