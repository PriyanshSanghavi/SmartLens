<?php
include("connect.php");
$email=$_REQUEST['email'];
$password=$_REQUEST['password'];

$q="select * from register_user where email='$email' and password='$password'";
$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
$response = array();
if($row=mysqli_fetch_array($rs))
{
	$response['user_id']=$row['user_id'];
	$response['photo']=$row['photo'];
	$response['name']=$row['name'];
	$response['password']=$row['password'];
	$response['birthdate']=$row['birthdate'];
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