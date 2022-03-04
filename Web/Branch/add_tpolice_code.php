<?php
include("connect.php");
session_start();
$ps_id = $_SESSION['ps_id'];
$name = $_POST['name'];
$password = $_POST['password'];
$gender = $_POST['gender'];
$birthdate = $_POST['bdate'];
$joindate = $_POST['jdate'];
$email = $_POST['email'];
$contact = $_POST['phone'];
$address = $_POST['address'];
$city = $_POST['city'];
$state = $_POST['state'];
$q1="select * from traffic_police where email='$email'";
$rs1=mysqli_query($cn,$q1);
$q2="select * from traffic_police where contact_no='$contact'";
$rs2=mysqli_query($cn,$q2);
if(time()<strtotime('+18 years',strtotime($birthdate)))
{
		header("location:add_tpolice.php?msg=failedbirthdate");
}
elseif(strtotime('+18 years',strtotime($birthdate))>strtotime($joindate))
{
		header("location:add_tpolice.php?msg=failedjoindate");
}
elseif($row1=mysqli_fetch_array($rs1))
{
		header("location:add_tpolice.php?msg=failedemail");

}
elseif($row2=mysqli_fetch_array($rs2))
{
		header("location:add_tpolice.php?msg=failedcontact");

}
else
{
	$q2="insert into traffic_police (ps_id,name,password,gender,birthdate,joindate,email,contact_no,address,city,state) values ('$ps_id','$name','$password','$gender','$birthdate','$joindate','$email',$contact,'$address','$city','$state')";
	$data=mysqli_query($cn,$q2);
	if($data)
	{
		require 'class.phpmailer.php';
		$mail = new PHPMailer();
		$mail->IsSMTP();
		$mail->Mailer = 'smtp';
		$mail->SMTPAuth = true;
		$mail->Host = 'smtp.gmail.com'; // "ssl://smtp.gmail.com" didn't worked
		$mail->Port = 465;
		$mail->SMTPSecure = 'ssl';
		// or try these settings (worked on XAMPP and WAMP):
		 //$mail->Port = 587;
		 //$mail->SMTPSecure = 'tls';
		 
		 
		$mail->Username = "smartlenscustomercare@gmail.com";
		$mail->Password = "@Pns4299";
		 
		$mail->IsHTML(true); // if you are going to send HTML formatted emails
		$mail->SingleTo = true; // if you want to send a same email to multiple users. multiple emails will be sent one-by-one.
		 
		$mail->From = "smartlenscustomercare.com";
		$mail->FromName = "Smart Lens";
		 
		$mail->addAddress($email,$name);

		 
		$mail->Subject = "Smart Lens Account Created";
		$mail->Body = "Hi $name,<br /><br />Your Smart Lens account is Created Successfully. Use this Email : <span style='color: blue;'>$email</span> and Password : <span style='color: blue;'>$password</span> for Login.";
		 
		if(!$mail->Send())
			header("location:add_tpolice.php?msg=failed");
		else
			header("location:add_tpolice.php?msg=success");
	}
	else
	{
		header("location:add_tpolice.php?msg=failed");
	}
}
?>