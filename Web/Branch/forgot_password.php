<!DOCTYPE html>
<html>
<head>
	<title>Forgot Password</title>
	<meta charset="UTF-8">
<!-- Add icon Link -->
	<link rel="icon" href="images/logo.png" type="image/png">
<!-- //Add icon Link -->
	<meta name="viewport" content="width=device-width, initial-scale=1">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/bootstrap/css/bootstrap.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="css/font-awesome.css"/>
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/animate/animate.css">
<!--===============================================================================================-->	
	<link rel="stylesheet" type="text/css" href="vendor/css-hamburgers/hamburgers.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="vendor/select2/select2.min.css">
<!--===============================================================================================-->
	<link rel="stylesheet" type="text/css" href="css/util.css">
	<link rel="stylesheet" type="text/css" href="css/main.css">
<!--===============================================================================================-->

<style >
	input[type=number]::-webkit-inner-spin-button,
	input[type=number]::-webkit-outer-spin-button{
		-webkit-appearance: none;
		margin: 0;
	}
	</style>
</head>
<body>
	<div class="limiter">
		<div class="container-login100" style="background-image: url('images/bg.jpg'); background-position: center; background-repeat: no-repeat; background-size: cover;">
			<div class="wrap-login100"style="margin-top: 50px; padding-top: 0px;">
				<span style="font-family: Arial Black; font-size: 75px;  color: #333333;  line-height: 1.2;  text-align: center;
  				width: 100%;  display: block; margin-top: 50px; padding-bottom: 54px;">
						Smart Lens
					</span>
					<div style="line-height: 1.2;  text-align: center; width: 100%;  display: block; margin-top: -50px; padding-bottom: 54px;">
						<?php
						 	if (@$_REQUEST['msg']=="fail") 
						 		{ echo "<span style='color: red;'>"."<B>Oops! Input Data is Invalid. </B>"."</span>";} 
						 	if (@$_REQUEST['msg']=="server") 
						 		{ echo "<span style='color: red;'>"."<B>Please, Check Your Internet Connection. </B>"."</span>";} 
						?>
					</div>
				<div class="login100-pic js-tilt" data-tilt>
					<img src="images/img.png" alt="IMG">
				</div>
				<form class="login100-form validate-form" action="fpassword_code.php" method="post">
					<span class="login100-form-title" style="margin-bottom: 30px; font-family: arial Black">
						Forgot Password
					</span>

					<div class="wrap-input100 validate-input" data-validate = "Valid email is required: ex@abc.xyz">
						<input class="input100" type="text" name="email" placeholder="Email">
						<span class="focus-input100"></span>
						<span class="symbol-input100">
							<i class="fa fa-envelope" aria-hidden="true"></i>
						</span>
					</div>

					<div class="container-login100-form-btn">
						<button class="login100-form-btn">
							Send Password
						</button>
					</div>
				</form>
			</div>
			<div class="footer" style="background-color: transparent; width: 100%; text-align: center;">
				<p style="color: white;">© 2021 Smart Lens . All Rights Reserved . Design by Priyansh Sanghavi</p>
			</div>
		</div>
	</div>
	
	

	
<!--===============================================================================================-->	
	<script src="vendor/jquery/jquery-3.2.1.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/bootstrap/js/popper.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/select2/select2.min.js"></script>
<!--===============================================================================================-->
	<script src="vendor/tilt/tilt.jquery.min.js"></script>
	<script >
		$('.js-tilt').tilt({
			scale: 1.1
		})
	</script>
<!--===============================================================================================-->
	<script src="js/main.js"></script>
	<script src="js/passwordicon.js"></script>

</body>
</html>