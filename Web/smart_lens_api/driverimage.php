<?php


include("connect.php");
$id= $_GET['id'];

$filename= "../driver_pic/" . $_FILES['image']['name'];
move_uploaded_file($_FILES['image']['tmp_name'] ,$filename);

$q="update register_user set photo='$filename' where user_id='$id'";

$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	
http_response_code(200);

$response['status']=true;
$response['driverid']=$id;

echo json_encode($response);


?>
