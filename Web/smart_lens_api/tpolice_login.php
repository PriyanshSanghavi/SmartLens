<?php
include("connect.php");
$email=$_REQUEST['email'];
$password=$_REQUEST['password'];
$q="select * from traffic_police where email='$email' and password='$password'";
$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
$response = array();
if($row=mysqli_fetch_array($rs))
{
	$response['tp_id']=$row['tp_id'];
	$response['ps_id']=$row['ps_id'];
	$response['photo']=$row['photo'];
	$response['name']=$row['name'];
	$response['password']=$row['password'];
	$response['birthdate']=$row['birthdate'];
	$response['joindate']=$row['joindate'];
	$response['gender']=$row['gender'];
	$response['email']=$row['email'];
	$response['contact_no']=$row['contact_no'];
	$response['address']=$row['address'];
	$response['city']=$row['city'];
	$response['state']=$row['state'];
	$response['status']=true;
}
else{
	$response['status']=false;
}
echo json_encode($response);
?>