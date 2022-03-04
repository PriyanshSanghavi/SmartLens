<?php
include("connect.php");
require 'image.compare.class.php';
date_default_timezone_set("Asia/Calcutta");
$id = $_REQUEST['id'];
$type = $_REQUEST['type'];
$latitude = $_REQUEST['latitude'];
$longitude = $_REQUEST['longitude'];
$time=date('h:i A');
$date = date('Y-m-d');
$filename= "../temp/" . $_FILES['image']['name'];
move_uploaded_file($_FILES['image']['tmp_name'] ,$filename);
$q="select * from traffic_police where tp_id='$id'";
$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
if($row=mysqli_fetch_array($rs))
{
	$photo=$row['photo'];
}
$class = new compareImages;
$result = $class->compare($filename,$photo);
if($result<=23)
{
	if($type == "In Time")
	{
		$q="select * from tp_attendance where date = '$date' && tp_id='$id'";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		if(mysqli_num_rows($rs)>0)
		{
			http_response_code(200);
			$response['status']='intimeexsist';
			echo json_encode($response);
		}
		else
		{
			$q="insert into  tp_attendance (tp_id,date,in_time,in_latitude,in_longitude) 
			values ('$id',now(),'$time','$latitude','$longitude')";
			$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
			http_response_code(200);
			$last_id = mysqli_insert_id($cn);
			$response['status']="true";
			echo json_encode($response);
		}
	}
	else
	{
		$q="select * from tp_attendance where date = '$date' && tp_id='$id'";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		if($row=mysqli_fetch_array($rs))
		{
			if($row['out_time']=="")
			{
				$q="update tp_attendance set out_time='$time',out_latitude='$latitude',out_longitude='$longitude' where date = '$date' && tp_id='$id'";
				$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
				http_response_code(200);
				$response['status']="true";
				echo json_encode($response);
			}
			else
			{
				http_response_code(200);
				$response['status']='outtimeexsist';
				echo json_encode($response);	
			}
		}
		else
		{
			http_response_code(200);
			$response['status']='nointime';
			echo json_encode($response);
		}
	}
}
else
{
			http_response_code(200);
			$response['status']='face';
			echo json_encode($response);
}