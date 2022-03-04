<?php
include("connect.php");
$result = $_POST['tpolice'];
$fromdate =$_POST['fromdate'];
$todate = $_POST['todate'];
echo $result;
$q="select * from traffic_police where tp_id = '$result' || email = '$result' || contact_no = '$result'";
$r=mysqli_query($cn,$q);
if($tp=mysqli_fetch_array($r))
{	
	$tp_id=$tp['tp_id'];
	$name=$tp['name'];
	header("location:tpolicememo.php?tpolice=$result&tp_id=$tp_id&tname=$name&fromdate=$fromdate&todate=$todate");
	}
else
{
	header("location:tpolicememo.php?tpolice=$result&fromdate=$fromdate&todate=$todate&msg=failed");
}
?>