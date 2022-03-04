<?php
	include("connect.php");
	$doc_id = $_REQUEST['id'];
	$q="delete from document where doc_id = '$doc_id'";
	$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
	http_response_code(200);
	$response['status']="true";
	echo json_encode($response);
?>