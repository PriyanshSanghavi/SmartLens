<?php
include("header.php");
include("connect.php");
$id=$_SESSION['ps_id'];
$q="select * from police_station where ps_id = '$id'";
$rs=mysqli_query($cn,$q);
$row=mysqli_fetch_array($rs);												
?>
<head>
	<title>Profile</title>
</head>		
<div class="main-grid">
	<div class="agile-grids">	
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">PROFILE</h2>
			</div>
			<div class="banner">
				<h2>
					<a href="home_branch.php">Home</a>
					<i class="fa fa-angle-right"></i>
					<span>Admin Profile</span>
				</h2>
			</div><br>
			<!-- Profile-forms -->
			<div class="w3agile-validation w3ls-validation">
				<div class="panel panel-widget agile-validation">
					<div class="validation-grids widget-shadow" data-example-id="basic-forms">
						<div class="form-body form-body-info">
							<form data-toggle="validator" novalidate="true" action="profile_code.php" method="post">
								<div style="text-align: center;">
									<?php
									 	if(@$_REQUEST['msg']=="success") 
									 		{echo "<span style='color: green;'>"."<B>Profile Update Successfully !!!</B>"."</span>";}
									 	elseif (@$_REQUEST['msg']=="failed") 
									 		{ echo "<span style='color: red;'>"."<B>Oops! Somthing went Wrong. Please try again. </B>"."</span>";} 
									 	elseif (@$_REQUEST['msg']=="failedemail") 
									 		{ echo "<span style='color: red;'>"."<B>Oops! Email Id is already Exist. </B>"."</span><br>";} 
									 	elseif (@$_REQUEST['msg']=="failedcontact") 
									 		{ echo "<span style='color: red;'>"."<B>Oops! Contact no is already Exist. </B>"."</span><br>";}
									?>
								</div><br>
								<input type="hidden" name="id" value="<?php echo $row['ps_id']; ?>">
								<div class="form-group valid-form">
									<label >Name</label><label style="color: red;">*</label>
									<input type="text" name="name" class="form-control" pattern="[A-Za-z ]{3,50}" value="<?php echo $row['name']; ?>" id="Name" placeholder="Branch name" required="">
								</div>
								<div class="form-group">
								  <label >Password</label><label style="color: red;">*</label>
								  <input type="password" name="password"  pattern="(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{8,}" data-toggle="validator" value="<?php echo $row['password']; ?>" class="form-control" id="inputPassword" placeholder="Password" required="">
								  <span class="help-block">Must contain at least one number, one uppercase and lowercase letter and at least 8 or more characters</span>
								  <input type="checkbox" onclick="myFunction()"> Show Password					
									<!-- Password Show -->
									<script>	
									function myFunction() {
									  var x = document.getElementById("inputPassword");
									  if (x.type === "password") {
									    x.type = "text";
									  } else {
									    x.type = "password";
									  }
									}
									</script>
									<!-- //Password Show -->
								</div>
								<div class="form-group"> 
									<label >Contact No.</label><label style="color: red;">*</label> 
									<input type="text" name="contact" data-toggle="validator" value="<?php echo $row['contact_no']; ?>" pattern="[0-9]{10}" class="form-control" id="inputPassword" placeholder="Branch Contact no." required="">
									<span class="help-block">Please enter a valid Contact number</span>
								</div>
								<div class="form-group has-feedback">
									<label >Email</label><label style="color: red;">*</label>
									<input type="text" name="email" pattern="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,3}$" class="form-control" id="inputEmail" value="<?php echo $row['email']; ?>" placeholder="Branch Email Id" data-error="Sorry, that email address is invalid" required="">
									<span class="glyphicon form-control-feedback" aria-hidden="true"></span>
									<span class="help-block with-errors">Please enter a valid email address</span>
								</div>
								<div class="form-group"> 
									<label >Address</label><label style="color: red;">*</label> 
									<textarea type="text" name="address" data-required=" " required="true" class="form-control"><?php echo $row['address']; ?></textarea>
								</div> 
								<div class="row">
									<div class="form-group col-md-6"> 
										<label >City </label><label style="color: red;">*</label> 
										<input type="text" required="true" data-required=" " pattern="[A-Za-z ]{3,30}" value="<?php echo $row['city']; ?>" name="city" class="form-control" placeholder="Branch City">
									</div> 
									<div class="form-group col-md-6">  
										<label >State OR Union Territory </label><label style="color: red;">*</label>
										<select name="state" class="form-control" id="state" data-error="Please, Select State." data-required=" " required="true">
											<option value="">Select State OR Union Territory</option>
											<?php 
												$states=array("Andaman and Nicobar Islands","Andhra Pradesh", "Arunachal Pradesh", "Assam","Bihar", "Chandigarh","Chhattisgarh", "Dadra & Nagar Haveli", "Daman & Dium ","Delhi","Goa", "Gujarat", "Haryana", "Himachal Pradesh","Jammu & Kashmir", "Jharkhand", "Karnataka",
            										"Kerala", "Lakshadweep","Madhya Pradesh", "Maharashtra", "Manipur", "Meghalaya", "Mizoram", 
            										"Nagaland", "Odisha","Puducherry", "Punjab", "Rajasthan","Sikkim", "Tamil Nadu", "Telangana", "Tripura", "Uttarakhand", "Uttar Pradesh", "West Bengal");
												for($i=0;$i<count($states);$i++)
													{ ?>

														<option value="<?php echo $states[$i]; ?>"<?php echo ($row['state']==$states[$i]) ? "selected = 'selected'" : ''; ?>><?php echo $states[$i]; ?></option>
											<?php   }?>
										</select>
									</div>
								</div>
								<div class="form-group">
									<button type="submit" class="btn btn-default w3ls-button">SAVE CHANGE</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>	
			<!-- //Profile-forms -->
		</div>
	</div>
</div>
		<!-- footer -->
<?php
include("footer.php");
?>
	<!-- input-forms -->
		<script type="text/javascript" src="js/valida.2.1.6.min.js"></script>
		<script type="text/javascript" >
			$(document).ready(function() {
				$('.valida').valida();
			});
		</script>
		<!-- //input-forms -->
		<!--validator js-->
<script src="js/validator.min.js"></script>
		<!--//validator js-->