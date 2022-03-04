<?php
include("connect.php");
$id = $_POST['id'];
$name = $_POST['name'];
$password = $_POST['password'];
$email = $_POST['email'];
$contact = $_POST['contact_no'];
$q1="select * from admin where email='$email' && ad_id!='$id'";
$rs1=mysqli_query($cn,$q1);
$q2="select * from admin where contact_no='$contact' && ad_id!='$id'";
$rs2=mysqli_query($cn,$q2);
if($row1=mysqli_fetch_array($rs1))
{
		header("location:profile.php?msg=failedemail");

}
elseif($row2=mysqli_fetch_array($rs2))
{
		header("location:profile.php?msg=failedcontact");

}
else
{
	$q2="update admin set name = '$name', password = '$password', email = '$email', contact_no = '$contact' where ad_id='$id'";
	$data=mysqli_query($cn,$q2);
	if($data)
	{
		header("location:profile.php?msg=success");
	}
	else
	{
	header("location:profile.php?msg=failed");
	}
}
?>