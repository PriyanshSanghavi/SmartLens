<?php
include("connect.php");
$id=$_REQUEST['id'];

$q="select * from register_user where user_id='$id'";
$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
$response = array();
if($row=mysqli_fetch_array($rs))
{
	$response['photo']=$row['photo'];
	$response['name']=$row['name'];
	$response['password']=$row['password'];
	$response['birthdate']=date( 'd/m/Y', strtotime($row['birthdate']));
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