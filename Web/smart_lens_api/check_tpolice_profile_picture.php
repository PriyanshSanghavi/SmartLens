<?php
include("connect.php");
$id=$_REQUEST['id'];
$q="select * from traffic_police where tp_id='$id'";
$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
$response = array();
if($row=mysqli_fetch_array($rs))
{
	$photo = $row['photo'];
	if($photo=="")
	{
		$response['status']="true";
	}
	else
	{
		$response['status']="false";
	}
}
echo json_encode($response);
?>