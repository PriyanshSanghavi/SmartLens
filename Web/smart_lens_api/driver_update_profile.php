<?php

include("connect.php");

$id= $_GET['id'];
$name = $_REQUEST['name'];
$gender = $_REQUEST['gender'];
$birthdate = $_REQUEST['birthdate'];
$email = $_REQUEST['email'];
$contact_no = $_REQUEST['contact_no'];
$address = $_REQUEST['address'];
$city = $_REQUEST['city'];
$state = $_REQUEST['state'];
$q1="select * from register_user where email='$email' && user_id!='$id'";
$rs1=mysqli_query($cn,$q1)or die(mysqli_error($cn));
$q2="select * from register_user where contact_no='$contact_no' && user_id!='$id'";
$rs2=mysqli_query($cn,$q2)or die(mysqli_error($cn));
if(time()<strtotime('+18 years',strtotime($birthdate)))
{
	http_response_code(200);
	$response['status']="birthdate";
	echo json_encode($response);
}
elseif($row1=mysqli_fetch_array($rs1))
{
	http_response_code(200);
	$response['status']="email";
	echo json_encode($response);

}
elseif($row2=mysqli_fetch_array($rs2))
{
	http_response_code(200);
	$response['status']="number";
	echo json_encode($response);

}
else
{
	$q="update register_user set name='$name',gender='$gender',birthdate='$birthdate',email='$email',contact_no='$contact_no',address='$address',city='$city',state='$state' where user_id=$id";
	
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	
	http_response_code(200);
  
	$response['status']=true;
	echo json_encode($response);
}

?>