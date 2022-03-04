<?php
include("header.php");
include("connect.php");
$user=$_REQUEST['user'];
?>
<head>
	<title>Verify Documents</title>
	<style >
		input[type=number]::-webkit-inner-spin-button,
		input[type=number]::-webkit-outer-spin-button{
			-webkit-appearance: none;
			margin: 0;
		}
	</style>
</head>
<div class="main-grid">
	<div class="agile-grids">	
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">VERIFY DOCUMENTS</h2>
			</div>
			<div class="banner">
				<h2>
					<a href="home_admin.php">Home</a>
					<i class="fa fa-angle-right"></i>
					<a href="uploaded_documents.php">Uploaded Documents</a>
					<i class="fa fa-angle-right"></i>
					<span>Verify Documents</span>
				</h2>
			</div><br>
				<!-- Profile-forms -->
				<?php 
					$q="select * from register_user where user_id = '$user'";
					$rs=mysqli_query($cn,$q);
					$row=mysqli_fetch_array($rs);					
				?>
				<div class="w3agile-validation w3ls-validation">
					<div class="panel panel-widget agile-validation">
						<div class="validation-grids widget-shadow" data-example-id="basic-forms">
							<div class="form-body form-body-info">
							<div class="input-info">
								<h3 style="color: #14C1d7;">Driver Details :</h3>
							</div>
								<form action="#" method="post">
									<div style="text-align: center;">
										<?php
										 	if(@$_REQUEST['msg']=="success") 
										 		{echo "<span style='color: green;'>"."<B>One Document Verified Successfully !!!</B>"."</span>";}
										 	elseif (@$_REQUEST['msg']=="failed") 
										 		{ echo "<span style='color: red;'>"."<B>Oops! Somthing went Wrong. Please try again. </B>"."</span>";} 
										?>
									</div>
									<div class="row">
										<div class="col-md-10">
											<div class="form-group valid-form">
												<label >Name</label>
												<input type="text" name="name" class="form-control" value="<?php print $row['name'];?>" id="inputName" readonly="true">
											</div>
											<div class="form-group"> 
												<label >Birth Date </label>
												<input type="Date" name="bdate" value="<?php print $row['birthdate'];?>" readonly="true" class="form-control">
											</div> 
											<div class="form-group">
												<label >Email</label>
												<input type="email" name="email" class="form-control" value="<?php print $row['email'];?>" readonly="true">
											</div>
											<div class="form-group"> 
												<label >Contact No.</label>
												<input type="number" name="phone" value="<?php print $row['contact_no'];?>" class="form-control" readonly="true">
											</div>
										</div>	
										<div class="form-group col-md-2" style="text-align: center;">
											<label style="color: #14C1d7;">Profile Pic </label>
											<img class="agile-gallery" src="<?php print $row['photo'];?>" alt="Profile pic file crash" style="height: 250px; width: 100%;">
										</div>
									</div><br>
										<div class="form-group"> 
										<label >Address</label>
										<textarea type="text" name="address" class="form-control" readonly="true"><?php print $row['address'];?></textarea>
									</div> 
										<div class="form-group"> 
											<label >City </label>
											<input type="text" name="city" value="<?php print $row['city'];?>" class="form-control" readonly="true">
										</div> 
										<div class="form-group"> 
											<label >State </label>
											<input type="text" name="state" value="<?php print $row['state'];?>" class="form-control" readonly="true">
										</div> 
								<div class="gallery-grids form-group">
									<div class="show-reel">
								<?php
								$q1="select * from document where user_id = '$user' && verification = 'pending'";
								$r=mysqli_query($cn,$q1);	
								$a = mysqli_num_rows($r);
								if($a==0){echo "<script type='text/javascript'>window.open('uploaded_documents.php?msg=success','_self');</script>"; }
									while($row=mysqli_fetch_array($r))
									{
								?>
										<div class="col-md-4 agile-gallery-grid" style="padding-bottom: 20px">
											<div class="agile-gallery">
												<a href="<?php print $row['file'];?>" target="_blank">
													<img style="height: 250px" src="<?php print $row['file'];?>" alt="" />
													<div class="agileits-caption" style="height: 100%">
														<h4><?php print $row['title'];?></h4>
														<p><?php print "Exp Date : ".date( 'd/m/Y', strtotime($row['exp_date'])); ?></p>
														<P>Make a decision Approved or Reject.</p>
														<button style="background-color: green; border-radius: 50%; padding: 5px; margin: 5px;" formaction="verify_code.php?doc_id=<?php print $row['doc_id'];?>&verify=approved&user=<?php echo $user; ?>"><i class="fa fa-thumbs-up" style="color: white;"></i></button>
														<button style="background-color: red; border-radius: 50%; padding: 5px; margin: 5px;" formaction="verify_code.php?doc_id=<?php print $row['doc_id'];?>&verify=reject&user=<?php echo $user; ?>"><i class="fa fa-thumbs-down" style="color: white;"></i></button>
														<p>click for open image.</p>
													</div>
												</a>
											</div>
										</div>
									<?php		
									}
								?>
							<div class='clearfix'> </div>
							</div>
								<script>
									$(window).load(function() {
									  $.fn.lightspeedBox();
									});

								</script>
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