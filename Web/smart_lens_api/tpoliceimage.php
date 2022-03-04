<?php


include("connect.php");
$id= $_GET['id'];

$filename= "../tpolice_pic/" . $_FILES['image']['name'];
move_uploaded_file($_FILES['image']['tmp_name'] ,$filename);

$q="update traffic_police set photo='$filename' where tp_id=$id";

$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	
http_response_code(200);

$response['status']=true;
$response['photo']=$filename;
$response['id']=$id;

echo json_encode($response);


?>
