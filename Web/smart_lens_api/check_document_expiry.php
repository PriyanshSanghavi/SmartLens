<?php
include("connect.php");
date_default_timezone_set("Asia/Calcutta");
$user_id = $_REQUEST['id'];
$q="select * from document where user_id = '$user_id' && verification ='approved'";
$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
$date = strtotime('+7 days',strtotime(date('Y-m-d')));
$cdate = strtotime(date('Y-m-d'));
$count = 0;
$exp_document = 0;
while($row=mysqli_fetch_array($rs))
{
	$exp_date=strtotime($row['exp_date']);
	if($cdate>$exp_date)
	{
		$exp_document = $exp_document + 1;
	}
	elseif($date>$exp_date)
	{
		$count = $count + 1;
	}
}
if($count > 0 || $exp_document > 0)
{
	$response['soon_exp'] = $count;
	$response['already_exp'] = $exp_document;
	$response['status']="true";
}
else
{
	$response['status']="false";
}
echo json_encode($response);
?>