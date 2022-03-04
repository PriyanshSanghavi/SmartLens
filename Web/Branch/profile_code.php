<?php
include("connect.php");
$id = $_POST['id'];
$name = $_POST['name'];
$password = $_POST['password'];
$email = $_POST['email'];
$contact = $_POST['contact'];
$address = $_POST['address'];
$city = $_POST['city'];
$state = $_POST['state'];
$q1="select * from police_station where email='$email' && ps_id!='$id'";
$rs1=mysqli_query($cn,$q1);
$q2="select * from police_station where contact_no='$contact' && ps_id!='$id'";
$rs2=mysqli_query($cn,$q2);
if($row1=mysqli_fetch_array($rs1))
{
		header("location:branch_profile.php?msg=failedemail");

}
elseif($row2=mysqli_fetch_array($rs2))
{
		header("location:branch_profile.php?msg=failedcontact");

}
else
{
	$q2="update police_station set name = '$name', password = '$password', email = '$email', contact_no = '$contact',address = '$address', city='$city', state='$state' where ps_id='$id'";
	$data=mysqli_query($cn,$q2);
	if($data)
	{
		header("location:branch_profile.php?msg=success");
	}
	else
	{
	header("location:branch_profile.php?msg=failed");
	}
}
?>