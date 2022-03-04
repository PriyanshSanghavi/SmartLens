<?php
	switch(trim(strtolower($_POST["search"])))
	{
		case "branch home" :
			header("location:home_branch.php");
			break;
		case "home" :
			header("location:home_branch.php");
			break;
		case "user ratting" :
			header("location:rating.php");
			break;
		case "ratting" :
			header("location:rating.php");
			break;
		case "user rating" :
			header("location:rating.php");
			break;
		case "rating" :
			header("location:rating.php");
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
		case "leave request" :
			header("location:reply_request.php");
			break;
		case "reply request" :
			header("location:reply_request.php");
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
		case "add traffic police" :
			header("location:add_tpolice.php");
			break;
		case "add traffic-police" :
			header("location:add_tpolice.php");
			break;
		case "add police" :
			header("location:add_tpolice.php");
			break;
		case "add" :
			header("location:add_tpolice.php");
			break;
		case "remove" :
			header("location:viewtrafficpolice.php");
			break;
		case "remove traffic-police" :
			header("location:viewtrafficpolice.php");
			break;
		case "remove traffic police" :
			header("location:viewtrafficpolice.php");
			break;
		case "remove police" :
			header("location:viewtrafficpolice.php");
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
			header("location:branch_profile.php");
			break;
		default :
			header("location:".$_SERVER['HTTP_REFERER']."?msg=fail");
			break;
	}
?>
