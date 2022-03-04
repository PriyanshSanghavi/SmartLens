<?php
	session_start();
	include("connect.php");
	$title = $_POST['ctitle'];
	$dest = "../circular/".mt_rand(1,9).time().$_SESSION['ad_id'].".pdf";
	$f_name = $_FILES['circular']['name'];
	$type = $_FILES['circular']['type'];
	$error = $_FILES['circular']['error'];
	$f_tmp = $_FILES['circular']['tmp_name'];
	if($error==0)
	{
		if($type=="application/pdf")
		{
			if(move_uploaded_file($f_tmp, $dest)) 
			{
				$q="insert into  circular (title,file,date) values('$title','$dest',NOW())";
				$data=mysqli_query($cn,$q);
				if($data)
				{
					header("location:sendcircular.php?msg=success");
				}
				else
				{
					header("location:sendcircular.php?msg=failed");
				}
			}
		}
		else
		{
			header("location:sendcircular.php?msg=typeinvalid");
		}
	}
	else
	{
		header("location:sendcircular.php?msg=error");
	}
?>