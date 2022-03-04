<?php
include("connect.php");
date_default_timezone_set("Asia/Calcutta");
$tp_id = $_REQUEST['tp_id'];
$vehical_no = $_REQUEST['vehical_no'];
$contact_no = $_REQUEST['contact_no'];
$q="select * from register_user where contact_no='$contact_no'";
$rs=mysqli_query($cn,$q)or die(mysqli_error($cn));
if($row=mysqli_fetch_array($rs))
{
	$q1 = "select user_id from register_user where contact_no = '$contact_no'";
	$rs1=mysqli_query($cn,$q1) or die(mysqli_error($cn));
	$row1=mysqli_fetch_array($rs1);
	$user_id = $row1['user_id'];
	$title = $_REQUEST['title'];
	$amount = $_REQUEST['amount'];
	$time = date("h:i A");
	$area = $_REQUEST['area'];
	$city = $_REQUEST['city'];
	$q2="insert into memo (tp_id,vehical_no,user_id,title,amount,date,time,area,city) values ('$tp_id','$vehical_no','$user_id','$title','$amount',now(),'$time','$area','$city')";
	$rs2=mysqli_query($cn,$q2) or die(mysqli_error($cn));
	http_response_code(200);
	$response['status']="true";
	echo json_encode($response);
}
else
{
	http_response_code(200);
	$response['status']="number";
	echo json_encode($response);
}
?>