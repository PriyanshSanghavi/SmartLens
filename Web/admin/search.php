<?php
	switch(trim(strtolower($_POST["search"])))
	{
		case "admin home" :
			header("location:home_admin.php");
			break;
		case "home" :
			header("location:home_admin.php");
			break;
		case "user rating" :
			header("location:rating.php");
			break;
		case "rating" :
			header("location:rating.php");
			break;
		case "user ratting" :
			header("location:rating.php");
			break;
		case "ratting" :
			header("location:rating.php");
			break;
		case "verify document" :
			header("location:uploaded_documents.php");
			break;
		case "document" :
			header("location:uploaded_documents.php");
			break;
		case "verify documents" :
			header("location:uploaded_documents.php");
			break;
		case "documents" :
			header("location:uploaded_documents.php");
			break;
		case "traffic police attendance" :
			header("location:tpoliceattendance.php");
			break;
		case "traffic-police attendance" :
			header("location:tpoliceattendance.php");
			break;
		case "police attendance" :
			header("location:tpoliceattendance.php");
			break;
		case "attendance" :
			header("location:tpoliceattendance.php");
			break;
		case "branch attendance" :
			header("location:branchattendance.php");
			break;
		case "traffic police leave" :
			header("location:tpoliceleave.php");
			break;
		case "traffic-police leave" :
			header("location:tpoliceleave.php");
			break;
		case "police leave" :
			header("location:tpoliceleave.php");
			break;
		case "leave" :
			header("location:tpoliceleave.php");
			break;
		case "branch leave" :
			header("location:branchleave.php");
			break;
		case "traffic police memo" :
			header("location:tpolicememo.php");
			break;
		case "traffic-police memo" :
			header("location:tpolicememo.php");
			break;
		case "police memo" :
			header("location:tpolicememo.php");
			break;
		case "memo" :
			header("location:tpolicememo.php");
			break;
		case "branch memo" :
			header("location:branchmemo.php");
			break;
		case "add branch" :
			header("location:addbranch.php");
			break;
		case "remove branch" :
			header("location:viewbranch.php");
			break;
		case "branch" :
			header("location:viewbranch.php");
			break;
		case "branches" :
			header("location:viewbranch.php");
			break;
		case "traffic-police" :
			header("location:viewtrafficpolice.php");
			break;
		case "traffic police" :
			header("location:viewtrafficpolice.php");
			break;
		case "police" :
			header("location:viewtrafficpolice.php");
			break;
		case "driver" :
			header("location:viewdriver.php");
			break;
		case "drivers" :
			header("location:viewdriver.php");
			break;
		case "circular" :
			header("location:viewcircular.php");
			break;
		case "circulars" :
			header("location:viewcircular.php");
			break;
		case "send circular" :
			header("location:sendcircular.php");
			break;
		case "smart lens" :
			header("location:aboutus.php");
			break;
		case "aboutus" :
			header("location:aboutus.php");
			break;
		case "about us" :
			header("location:aboutus.php");
			break;
		case "help" :
			header("location:faqs.php");
			break;
		case "faqs" :
			header("location:faqs.php");
			break;
		case "faq" :
			header("location:faqs.php");
			break;
		case "frequent ask question" :
			header("location:faqs.php");
			break;
		case "frequent ask questions" :
			header("location:faqs.php");
			break;
		case "profile" :
			header("location:profile.php");
			break;
		default :
			header("location:".$_SERVER['HTTP_REFERER']."?msg=fail");
			break;
	}
?>
