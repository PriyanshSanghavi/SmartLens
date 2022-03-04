<?php
	include("connect.php");
	$d_id=$_REQUEST['doc_id'];
	$user= $_REQUEST['user'];
	if($_REQUEST['verify']=="approved")
	{
		$q = "update document set verification='approved' where doc_id = '$d_id'";
	}
	elseif ($_REQUEST['verify']=="reject")
	{
		$q = "update document set verification='reject' where doc_id = '$d_id'";	
	}
	$result=mysqli_query($cn,$q);
	if($result)
	{
	header("location:verify_documents.php?msg=success&user=$user");
	}
	else
	{
		header("location:verify_documents.php?msg=failed&user=$user");
	}
?>
