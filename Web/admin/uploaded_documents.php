<?php
include_once("header.php");
include("connect.php");
?>
<head>
<title>Uploaded Documents</title>
</head>
<div class="main-grid">
	<div class="agile-grids">	
		<div class="grids">
			<div class="progressbar-heading grids-heading">
				<h2 style=" font-family: Algerian;">UPLOADED DOCUMENTS</h2>
			</div>
			<div class="banner">
				<h2>
					<a href="home_admin.php">Home</a>
					<i class="fa fa-angle-right"></i>
					<span>Uploaded Documents</span>
				</h2>
			</div><br>
			<div class="w3agile-validation w3ls-validation">
				<div class="panel panel-widget agile-validation">
					<div class="validation-grids widget-shadow" data-example-id="basic-forms">
						<div class="col-md-12" style="text-align: center;">
						<?php 
							if(@$_REQUEST['msg']=="success") 
								{echo "<span style='color: green;'>"."<B>One User Documents Verifyed Successfully !!!</B>"."</span><br>";}
						?>
						</div>
						<div class="gallery-grids form-group">
							<div class="show-reel">
								<?php
									$q1="select doc_id,user_id from document where verification = 'pending'";
									$r=mysqli_query($cn,$q1);	
									$a = mysqli_num_rows($r);
									$user_id=0;
									while($row=mysqli_fetch_array($r))
									{

										if($user_id !=$row['user_id'] )
										{	
											$user_id= $row['user_id'];
											$doc_id = $row['doc_id'];
										$q="select u.*,r.* from document u ,register_user r where r.user_id=u.user_id and u.doc_id=$doc_id ";
										$res=mysqli_query($cn,$q);
										$user=mysqli_fetch_array($res);	
										$id = $user['user_id'];
								?>
											<div class="col-md-4 agile-gallery-grid" style="padding-bottom: 20px">
												<div class="agile-gallery">
													<a href="<?php print $user['file'];?>" class="lsb-preview" data-lsb-group="header">
														<img style="height: 250px" src="<?php print $user['file'];?>" alt="Document loading failed" />
														<a href="verify_documents.php?user=<?php echo $id;?>">
															<div class="agileits-caption" style="height: 100%">
																<h4><?php print $user['name'];?></h4>
																<p>Click here to Verify Documents.</p>
															</div>
														</a>
													</a>
												</div>
											</div>
									<?php		
										}
									}?>
								<div class='clearfix'> </div>
							</div>
								<script>
									$(window).load(function() {
									  $.fn.lightspeedBox();
									});

								</script>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
		<!-- footer -->
<?php
include_once("footer.php");
?>