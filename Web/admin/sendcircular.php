<?php
include("header.php");
?>
<head>
<title>Send Circular</title>
</head>	
<div class="main-grid">
			<div class="agile-grids">	
				<!-- input-forms -->
				<div class="grids">
					<div class="progressbar-heading grids-heading">
						<h2 style=" font-family: Algerian;">SEND CIRCULAR</h2>
					</div>
					<div class="banner">
						<h2>
							<a href="home_admin.php">Home</a>
							<i class="fa fa-angle-right"></i>
							<span>Circular</span>
							<i class="fa fa-angle-right"></i>
							<span>Send</span>
						</h2>
					</div><br>
						<div class="w3agile-validation w3ls-validation">
							<div class="panel panel-widget agile-validation">
								<div class="validation-grids widget-shadow" data-example-id="basic-forms">
									<div class="form-body form-body-info">
									<form method="post" action="sendcircular_code.php" enctype="multipart/form-data" data-toggle="validator" novalidate="true"> 
										<div style="text-align: center;">
												<?php
												 	if(@$_REQUEST['msg']=="success") 
												 		{echo "<span style='color: green;'>"."<B>Circular Sent Successfully !!!</B>"."</span>";}
												 	elseif (@$_REQUEST['msg']=="failed") 
												 		{ echo "<span style='color: red;'>"."<B>Oops! Somthing went Wrong. Please try again.</B>"."</span>";}
												 	elseif (@$_REQUEST['msg']=="error") 
												 		{ echo "<span style='color: red;'>"."<B>Your file Contains Errors.</B>"."</span>";} 
												 	elseif (@$_REQUEST['msg']=="typeinvalid") 
												 		{ echo "<span style='color: red;'>"."<B>Oops! File Type is not Correct.</B>"."</span>";}  
												?>
										</div>
										<div class="form-group"> 
											<label>Title</label><label style="color: red;">*</label>
											<input type="text"  required="true" data-error="" name="ctitle" class="form-control" placeholder="Enter Title of Circular"> 
											<span class="help-block with-errors"></span>		
										</div>
										<div class="form-group"> 
											<label for="exampleInputFile">Circular (Only PDF file)</label><label style="color: red;">*</label><br><br>
											<input type="file" accept="application/pdf" required="true" name="circular" data-required=" " id="exampleInputFile"> 
											<p class="help-block">Select Circular from file.</p> 
										</div>
										<button type="submit" name="submit" value="upload" class="btn btn-default w3ls-button">SEND</button> 
									</form> 
								</div>
							</div>
						</div>
					</div>
				</div>
				<!-- //input-forms -->
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