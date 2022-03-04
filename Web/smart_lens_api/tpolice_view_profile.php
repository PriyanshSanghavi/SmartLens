<?php
include("connect.php");
$tp_id = $_REQUEST['id'];
$q="select * from traffic_police where tp_id = '$tp_id'";
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	$response = array();
if($row=mysqli_fetch_array($rs))
{
	$response['photo']=$row['photo'];
	$response['name']=$row['name'];
	$ps_id = $row['ps_id'];
	$q1 = "select name from police_station where ps_id = '$ps_id'";
	$res=mysqli_query($cn,$q1) or die(mysqli_error($cn));
	$row1=mysqli_fetch_array($res);
	$response['branch']=$row1['name'];
	$response['birthdate']=date( 'd/m/Y', strtotime($row['birthdate']));
	$response['joindate']=date( 'd/m/Y', strtotime($row['joindate']));
	$response['gender']=$row['gender'];
	$response['email']=$row['email'];
	$response['contact_no']=$row['contact_no'];
	$response['address']=$row['address'];
	$response['city']=$row['city'];
	$response['state']=$row['state'];
	$response['status']=true;
}
	echo json_encode($response);
?>