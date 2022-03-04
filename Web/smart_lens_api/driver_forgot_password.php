<?php
include("connect.php");
$email=$_REQUEST['email'];
$q="select * from register_user where email='$email'";
$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
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
	$mail->Body = "Hi $name,<br /><br />Your Smart Lens account Password is <span style='color: blue;'>$password</span>.";
	 
	if(!$mail->Send())
		$response['status']="server";
	else
		$response['status']="true";
}
else{
	$response['status']="false";
}
echo json_encode($response);
?>
