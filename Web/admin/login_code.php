<?php
include("connect.php");
session_start();
$email=$_POST['email'];
$password=$_POST['password'];
$q="select * from admin where email='$email' and password='$password'";
$rs=mysqli_query($cn,$q);
if($row=mysqli_fetch_array($rs))
{
	$_SESSION['ad_id']=$row['ad_id'];
	header("location:home_admin.php");
}
else
{
	header("location:login.php?msg=fail");
}
?>