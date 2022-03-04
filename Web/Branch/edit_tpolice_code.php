<?php
include("connect.php");
$name = $_POST['name'];
$branch = $_POST['branch'];
$tp_id = $_POST['tp_id'];
$gender = $_POST['gender'];
$birthdate = $_POST['bdate'];
$joindate = $_POST['jdate'];
$email = $_POST['email'];
$contact = $_POST['phone'];
$address = $_POST['address'];
$city = $_POST['city'];
$state = $_POST['state'];
$q1="select * from traffic_police where email='$email' && tp_id!='$tp_id'";
$rs1=mysqli_query($cn,$q1);
$q2="select * from traffic_police where contact_no='$contact' && tp_id!='$tp_id'";
$rs2=mysqli_query($cn,$q2);
if(time()<strtotime('+18 years',strtotime($birthdate)))
{
	header("location:edit_tpolice.php?tp_id=$tp_id&msg=failedbirthdate");
}
elseif(strtotime('+18 years',strtotime($birthdate))>strtotime($joindate))
{
	header("location:edit_tpolice.php?tp_id=$tp_id&msg=failedjoindate");
}
elseif($row1=mysqli_fetch_array($rs1))
{
		header("location:edit_tpolice.php?tp_id=$tp_id&msg=failedemail");

}
elseif($row2=mysqli_fetch_array($rs2))
{
		header("location:edit_tpolice.php?tp_id=$tp_id&msg=failedcontact");
}
else
{
	$q="update traffic_police set name = '$name', ps_id = '$branch', gender = '$gender', birthdate = '$birthdate', joindate = '$joindate', email = '$email', contact_no = '$contact', address='$address', city = '$city', state = '$state' where tp_id='$tp_id'";
	$data=mysqli_query($cn,$q);
	if($data)
	{
		header("location:viewtrafficpolice.php?msg=success");
	}
	else
	{
		header("location:edit_tpolice.php?msg=failed");
	}
}
?>