<?php
include("connect.php");
date_default_timezone_set("Asia/Calcutta");
$date = strtotime(date('Y-m-d'));
$user_id = $_REQUEST['id'];
$filename= "../document/" . $_FILES['image']['name'];
move_uploaded_file($_FILES['image']['tmp_name'] ,$filename);
$title = $_REQUEST['title'];
$exp_date = $_REQUEST['exp_date'];
$verification = "pending";
if ($date>=strtotime($exp_date)) 
{
	$response['status']="expdate";
	echo json_encode($response);
}
else
{
	if ($title == "Front Side Driving License") 
	{
		$q="select * from document where user_id = '$user_id' && title ='$title'";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		if($row = mysqli_fetch_array($rs))
		{
			$doc_id = $row['doc_id'];
			$q="update document set file='$filename',exp_date='$exp_date',verification='$verification' where doc_id='$doc_id'";
			$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
			http_response_code(200);
			$response['status']="true";
			echo json_encode($response);
		}
		else
		{
			$q="insert into document (user_id,title,file,exp_date,verification) values ('$user_id','$title','$filename','$exp_date','$verification')";
			$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
			http_response_code(200);
			$response['status']="true";
			echo json_encode($response);
		}
	}
	elseif ($title == "Back Side Driving License") 
	{
		$q="select * from document where user_id = '$user_id' && title ='$title'";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		if($row = mysqli_fetch_array($rs))
		{
			$doc_id = $row['doc_id'];
			$q="update document set file='$filename',exp_date='$exp_date',verification='$verification' where doc_id='$doc_id'";
			$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
			http_response_code(200);
			$response['status']="true";
			echo json_encode($response);

		}
		else
		{
			$q="insert into document (user_id,title,file,exp_date,verification) values ('$user_id','$title','$filename','$exp_date','$verification')";
			$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
			http_response_code(200);
			$response['status']="true";
			echo json_encode($response);
		}	
	}
	else
	{
		$q="insert into document (user_id,title,file,exp_date,verification) values ('$user_id','$title','$filename','$exp_date','$verification')";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		http_response_code(200);
		$response['status']="true";
		echo json_encode($response);
	}
}
?>