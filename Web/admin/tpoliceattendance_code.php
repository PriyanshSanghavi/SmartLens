<?php
include("connect.php");
$result = $_POST['tpolice'];
$fromdate =$_POST['fromdate'];
$todate = $_POST['todate'];
echo $result;
$q="select * from traffic_police where tp_id = '$result' or email = '$result' or contact_no = '$result'";
$r=mysqli_query($cn,$q);
if($tp=mysqli_fetch_array($r))
{	
	$psid=$tp['ps_id'];
	$q1="select * from police_station where ps_id='$psid'";
	$r1=mysqli_query($cn,$q1);
	$pst=mysqli_fetch_array($r1);
	$ps=$pst['name'];
	$tp_id=$tp['tp_id'];
	$name=$tp['name'];
	header("location:tpoliceattendance.php?tpolice=$result&tp_id=$tp_id&tname=$name&ps=$ps&fromdate=$fromdate&todate=$todate");
	}
else
{
	header("location:tpoliceattendance.php?tpolice=$result&fromdate=$fromdate&todate=$todate&msg=failed");
}
?>