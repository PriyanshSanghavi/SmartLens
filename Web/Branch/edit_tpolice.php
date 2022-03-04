<?php
include("header.php");
include("connect.php");
$tp_id=@$_REQUEST['tp_id'];
$q="select * from traffic_police where tp_id='$tp_id'";
$q1="select * from police_station";
$rs=mysqli_query($cn,$q);
$r=mysqli_query($cn,$q1);
$row=mysqli_fetch_array($rs);
?>
<head>
	<title>Traffic Police Profile Edit</title>
</head>		
<div class="main-grid">
	<div class="agile-grids">	
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">Edit TRAFFIC POLICE</h2>
			</div>
			<div class="banner">
				<h2>
					<a href="home_branch.php">Home</a>
					<i class="fa fa-angle-right"></i>
					<span>Traffic Police</span>
					<i class="fa fa-angle-right"></i>
					<span>Edit</span>
				</h2>
			</div><br>
			<div class="w3agile-validation w3ls-validation">
				<div class="panel panel-widget agile-validation">
					<div class="validation-grids widget-shadow" data-example-id="basic-forms">
						<div class="form-body form-body-info">
							<div style="text-align: center;">
								<?php
								 	if (@$_REQUEST['msg']=="failed") 
								 		{ echo "<span style='color: red;'>"."<B>Oops! Somthing went Wrong. Please try again. </B>"."</span>";} 
								 	elseif (@$_REQUEST['msg']=="failedemail") 
								 		{ echo "<span style='color: red;'>"."<B>Oops! Email Id is already Exist. </B>"."</span><br>";} 
								 	elseif (@$_REQUEST['msg']=="failedcontact") 
								 		{ echo "<span style='color: red;'>"."<B>Oops! Contact no is already Exist. </B>"."</span><br>";}
								 	elseif (@$_REQUEST['msg']=="failedbirthdate") 
								 		{ echo "<span style='color: red;'>"."<B>Please Enter Valid Birth date. </B>"."</span><br>";}
								 	elseif (@$_REQUEST['msg']=="failedjoindate") 
								 		{ echo "<span style='color: red;'>"."<B>Please Enter Valid join date. </B>"."</span><br>";} 
								?>
							</div><br>
							<form data-toggle="validator" novalidate="true" action="edit_tpolice_code.php" method="post">
								<input type="hidden" name="tp_id" value="<?php print $tp_id?>">
								<div class="form-group valid-form">
									<label >Name</label><label style="color: red;">*</label>
									<input type="text" name="name" class="form-control" value="<?php print $row['name'];?>" id="inputName" placeholder="Traffic Police name" pattern="[A-Za-z ]{3,50}" required="">
								</div>
								<div class="form-group valid-form">
									<label >Branch</label><label style="color: red;">*</label>
									<select name="branch" class="form-control" id="branch" data-error="Please, Select Branch from the list." data-required=" " required="true">
										<option value=""></option>
									<?php	while($branch=mysqli_fetch_array($r))
										{ ?>
											<option value="<?php print $branch['ps_id'];?>" <?php echo ($branch['ps_id'] == $row['ps_id']) ? "selected = 'selected'" : ''; ?>><?php print $branch['name'];?></option>
									<?php	} ?>
									</select>
									<span class="help-block with-errors"></span>
								</div>
								<div class="form-group"> 										
									<label>Gender</label><label style="color: red;">*</label><br>

									<?php
									if($row['gender']== "MALE")
									{
									?>
									<input type="radio"  name="gender" value="MALE" style="margin-left: 10px;" checked>
									<span>MALE</span>
									<input type="radio" name="gender" value="FEMALE" style="margin-left: 10px;">
									<span>FEMALE</span>
									<br>
									<?php
									}
									else{
										?>
									<input type="radio"  name="gender" value="MALE" style="margin-left: 10px;" >
									<span>MALE</span>
									<input type="radio" name="gender" value="FEMALE" style="margin-left: 10px;" checked>
									<span>FEMALE</span>
									<br>
										<?php
										}	
										?>
								</div>	
								<div class="row">									
									<div class="form-group col-md-6"> 
										<label >Birth Date </label><label style="color: red;">*</label> 
										<input type="Date" required="true" data-required=" " value="<?php print $row['birthdate'];?>" name="bdate" class="form-control" placeholder="Enter Traffic Police Birth Date">
									</div>
									<div class="form-group col-md-6"> 
										<label >Join Date </label><label style="color: red;">*</label> 
										<input type="Date" required="true" data-required=" " value="<?php print $row['joindate'];?>" name="jdate" class="form-control" placeholder="Enter Traffic Police Join Date">
									</div>
								</div>
								<div class="form-group"> 
									<label >Contact No.</label><label style="color: red;">*</label> 
									<input type="text" pattern="[0-9]{10}" name="phone" value="<?php print $row['contact_no'];?>" data-toggle="validator" class="form-control" id="inputPassword" placeholder="Traffic Police Contact no." required="">
									<span class="help-block">Please enter a valid Contact number</span>
								</div>
								<div class="form-group has-feedback">
									<label>Email</label><label style="color: red;">*</label>
									<input type="text" name="email" value="<?php print $row['email'];?>" class="form-control" id="inputEmail" placeholder="Traffic Police Email Id" pattern="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,3}$" data-error="Sorry, that email address is invalid" required="">
									<span class="glyphicon form-control-feedback" aria-hidden="true"></span>
									<span class="help-block with-errors">Please enter a valid email address</span>
								</div>
								<div class="form-group"> 
									<label >Address</label><label style="color: red;">*</label> 
									<textarea type="text" name="address" data-required=" " required="true" class="form-control"><?php print $row['address'];?></textarea>
								</div> 
								<div class="row">
									<div class="form-group col-md-6"> 
										<label >City </label><label style="color: red;">*</label> 
										<input type="text" required="true" value="<?php print $row['city'];?>" pattern="[A-Za-z ]{3,30}" data-required=" " name="city" class="form-control" placeholder="Enter Traffic Police City">
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
									<div class="form-group">
										<button type="submit" class="btn btn-default w3ls-button">UPDATE</button>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>	
			<!-- //input-forms -->
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