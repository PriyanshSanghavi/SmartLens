<?php
include("connect.php");
$user_id = $_REQUEST['user_id'];
$star = $_REQUEST['star'];
$msg = $_REQUEST['msg'];
$q = "select * from ratting where user_id = '$user_id'";
$rs = mysqli_query($cn,$q);
$num = mysqli_num_rows($rs);
if($num == 0)
{
	$q1="insert into ratting (user_id,star,date,msg) values ('$user_id','$star',now(),'$msg')";
	$rs1=mysqli_query($cn,$q1) or die(mysqli_error($cn));
	http_response_code(200);
	$response['status']=true;
	echo json_encode($response);
}
else
{
	$q1="update ratting set star='$star',msg='$msg' where user_id = '$user_id'";
	$rs1=mysqli_query($cn,$q1) or die(mysqli_error($cn));
	http_response_code(200);
	$response['status']=true;
	echo json_encode($response);	
}
?>