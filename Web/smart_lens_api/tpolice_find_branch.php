<?php
include("connect.php");
$ps_id = $_REQUEST['id'];

$q = "select name from police_station where ps_id = '$ps_id'";
$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
$row=mysqli_fetch_array($rs);
$name = $row['name'];
	http_response_code(200);
$response['name']=$name;
echo json_encode($response);
?>