<?php
include("connect.php");
require 'image.compare.class.php';
$filename= "../temp/" . $_FILES['image']['name'];
move_uploaded_file($_FILES['image']['tmp_name'] ,$filename);
$q="select * from register_user";
$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));

$flag=0;
while($row=mysqli_fetch_array($rs))
{
	$photo=$row['photo'];
	$id=$row['user_id'];
	$name=$row['name'];
	$class = new compareImages;
	$result = $class->compare($filename,$photo);
	if($result<=23)
	{
		$response['id']=$id;
		$response['name']=$name;
		$response['status']="true";
		echo json_encode($response);
		$flag=1;
		break;
	}
}

if($flag==0)
{		
	$response['status']="false";
	echo json_encode($response);
}

?>