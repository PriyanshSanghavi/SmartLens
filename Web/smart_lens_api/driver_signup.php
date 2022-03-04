<?php

include("connect.php");

$name = $_REQUEST['name'];
$password = $_REQUEST['password'];
$gender = $_REQUEST['gender'];
$birthdate = $_REQUEST['birthdate'];
$email = $_REQUEST['email'];
$contact_no = $_REQUEST['contact_no'];
$address = $_REQUEST['address'];
$city = $_REQUEST['city'];
$state = $_REQUEST['state'];
$q1="select * from register_user where email='$email'";
$rs1=mysqli_query($cn,$q1)or die(mysqli_error($cn));
$q2="select * from register_user where contact_no='$contact_no'";
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
	$q="insert into register_user (name,password,gender,birthdate,email,contact_no,address,city,state) 
	values ('$name','$password','$gender','$birthdate','$email',$contact_no,'$address','$city','$state')";
	
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	
	http_response_code(200);

	$last_id = mysqli_insert_id($cn);
  
	$response['status']=true;
	$response['driverid']=$last_id;

	echo json_encode($response);
}
?>