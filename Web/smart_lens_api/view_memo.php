<?php
include("connect.php");
$id = $_REQUEST['id'];
$q="select * from memo where m_id = '$id'";
$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
$response = array();
if($row=mysqli_fetch_array($rs))
{
	$user_id=$row['user_id'];
	$response['vehical_no']=$row['vehical_no'];
	$response['reason']=$row['title'];
	$response['amount']=$row['amount'];
	$response['area']=$row['area'];
	$response['city']=$row['city'];
	$response['date']=$row['date'];
	$response['time']=$row['time'];
	$response['p_status']=$row['p_status'];
	$q1="select * from register_user where user_id='$user_id'";
	$rs1=mysqli_query($cn,$q1) or die(mysqli_error($cn));
	if($row1=mysqli_fetch_array($rs1))
	{
		$response['name']=$row1['name'];
	}
	$response['status']=true;
}
echo json_encode($response);
?>