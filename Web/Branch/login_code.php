<?php
include("connect.php");
session_start();
$email=$_POST['email'];
$password=$_POST['password'];
$q="select * from police_station where email='$email' and password='$password'";
$rs=mysqli_query($cn,$q);
if($row=mysqli_fetch_array($rs))
{
	$_SESSION['ps_id']=$row['ps_id'];
	header("location:home_branch.php");
}
else
{
	header("location:login.php?msg=fail");
}
?>