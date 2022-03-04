<?php
include("connect.php");
session_start();
$email=$_POST['email'];
$contact=$_POST['number'];
$q="select * from police_station where email='$email' and contact_no='$contact'";
$rs=mysqli_query($cn,$q);
if($row=mysqli_fetch_array($rs))
{
	$password=$row['password'];
	$name=$row['name'];
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

	 
	$mail->Subject = "Forgot Password";
	$mail->Body = "Hi $name,<br /><br />Your Smart Lens Branch account Password is <span style='color: blue;'>$password</span>.";
	 
	if(!$mail->Send())
		header("location:forgot_password.php?msg=server");
	else
		header("location:login.php?msg=sended");
}
else
{
	header("location:forgot_password.php?msg=fail");
}
?>