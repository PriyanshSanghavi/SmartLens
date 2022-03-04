<?php
include("connect.php");
$name = $_POST['name'];
$ps_id = $_POST['ps_id'];
$email = $_POST['email'];
$contact = $_POST['phone'];
$address = $_POST['address'];
$city = $_POST['city'];
$state = $_POST['state'];
$q1="select * from police_station where email='$email' && ps_id!='$ps_id'";
$rs1=mysqli_query($cn,$q1);
$q2="select * from police_station where contact_no='$contact' && ps_id!='$ps_id'";
$rs2=mysqli_query($cn,$q2);
if($row1=mysqli_fetch_array($rs1))
{
		header("location:edit_branch.php?ps_id=$ps_id&msg=failedemail");

}
elseif($row2=mysqli_fetch_array($rs2))
{
		header("location:edit_branch.php?ps_id=$ps_id&msg=failedcontact");

}
else
{
	$q="update police_station set name = '$name', email = '$email', contact_no = '$contact', address='$address', city = '$city', state = '$state' where ps_id='$ps_id'";
	$data=mysqli_query($cn,$q);
	if($data)
	{
		header("location:viewbranch.php?msg=success");
	}
	else
	{
		header("location:edit_branch.php?ps_id=$ps_id&msg=failed");
	}
}
?>