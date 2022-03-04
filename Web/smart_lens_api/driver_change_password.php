<?php
	include("connect.php");

	$id= $_GET['id'];
	$npassword = $_REQUEST['npassword'];
	$opassword = $_REQUEST['opassword'];
	$q1="select password from register_user where user_id='$id'";
	$rs1=mysqli_query($cn,$q1)or die(mysqli_error($cn));
	if($row1=mysqli_fetch_array($rs1))
	{
		if($opassword != $row1['password'])
		{
			http_response_code(200);
			$response['status']="oldpassword";
			echo json_encode($response);
		}
		else
		{
		$q="update register_user set password='$npassword' where user_id=$id";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		http_response_code(200);
		$response['status']=true;
		echo json_encode($response);
		}
	}
?>