<?php

include("connect.php");

$id= $_GET['id'];
$name = $_REQUEST['name'];
$gender = $_REQUEST['gender'];
$birthdate = date( 'Y-m-d', strtotime($_REQUEST['birthdate']));
$email = $_REQUEST['email'];
$contact_no = $_REQUEST['contact_no'];
$address = $_REQUEST['address'];
$city = $_REQUEST['city'];
$state = $_REQUEST['state'];
$q="select * from traffic_police where tp_id='$id'";
$rs=mysqli_query($cn,$q)or die(mysqli_error($cn));
if($row=mysqli_fetch_array($rs))
{
	$joindate = $row['joindate'];
}
$q1="select * from traffic_police where email='$email' && tp_id!='$id'";
$rs1=mysqli_query($cn,$q1)or die(mysqli_error($cn));
$q2="select * from traffic_police where contact_no='$contact_no' && tp_id!='$id'";
$rs2=mysqli_query($cn,$q2)or die(mysqli_error($cn));
if(time()<strtotime('+18 years',strtotime($birthdate)) || strtotime('+18 years',strtotime($birthdate))>strtotime($joindate))
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
else{

	$q="update traffic_police set name='$name',gender='$gender',birthdate='$birthdate',email='$email',contact_no='$contact_no',address='$address',city='$city',state='$state' where tp_id = '$id'";
	
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	
	http_response_code(200);
  
	$response['status']=true;
	echo json_encode($response);
}
?>