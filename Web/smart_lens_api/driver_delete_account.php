<?php
	include("connect.php");
	$id= $_GET['id'];
	$q1="select * from memo where user_id='$id' && p_status=1";
	$rs1=mysqli_query($cn,$q1)or die(mysqli_error($cn));
	if($row=mysqli_fetch_array($rs1))
	{
		http_response_code(200);
		$response['status']="due";
		echo json_encode($response);
	}
	else
	{
		$q="Update register_user set password='',email='', contact_no='' where user_id='$id'";
		$rs=mysqli_query($cn,$q)or die(mysqli_error($cn));
		http_response_code(200);
		$response['status']="true";
		echo json_encode($response);
	}
?>