<form action="#" method="POST">
<div class="row">
	<div class="col-md-6">
		<span>From Date</span><br>
		<input type="date" name="fromdate">
	</div>
	<div class="col-md-6">
		<span>To Date</span><br>
		<input type="date" name="todate">
	</div>
</div><br>
<input type="submit" name="submit" value="submit">
</form>
<?php
if($_SERVER["REQUEST_METHOD"]=="POST")
{
	include("connect.php");
	date_default_timezone_set("Asia/Calcutta");
	$i=0;
	$fromdate=$_POST['fromdate'];
	$todate=$_POST['todate'];
	while(strtotime($todate) >= $d=strtotime("+".$i." days",strtotime($fromdate)))
	{
		$date = date("Y-m-d",$d);
		$q="insert into  tp_attendance (tp_id,date,in_time,in_latitude,in_longitude,out_time,out_latitude,out_longitude) values (1,'$date','09:00 AM','22.7303440','71.6285420','06:00 PM','22.7303440','71.6285420')";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		$q="insert into  tp_attendance (tp_id,date,in_time,in_latitude,in_longitude,out_time,out_latitude,out_longitude) values (2,'$date','09:00 AM','23.0394666','72.5300793','06:00 PM','23.0394666','72.5300793')";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		$q="insert into  tp_attendance (tp_id,date,in_time,in_latitude,in_longitude,out_time,out_latitude,out_longitude) values (3,'$date','09:00 AM','28.6517623','77.1866324','06:00 PM','28.6517623','77.1866324')";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		$q="insert into  tp_attendance (tp_id,date,in_time,in_latitude,in_longitude,out_time,out_latitude,out_longitude) values (4,'$date','09:00 AM','24.6217249','73.8895582','06:00 PM','24.6217249','73.8895582')";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		$q="insert into  tp_attendance (tp_id,date,in_time,in_latitude,in_longitude,out_time,out_latitude,out_longitude) values (5,'$date','09:00 AM','19.1123322','72.8669742','06:00 PM','19.1123322','72.8669742')";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		$q="insert into  tp_attendance (tp_id,date,in_time,in_latitude,in_longitude,out_time,out_latitude,out_longitude) values (6,'$date','09:00 AM','22.7189582','71.6552208','06:00 PM','22.7189582','71.6552208')";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		$q="insert into  tp_attendance (tp_id,date,in_time,in_latitude,in_longitude,out_time,out_latitude,out_longitude) values (7,'$date','09:00 AM','21.2392001','72.8498598','06:00 PM','21.2392001','72.8498598')";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		$q="insert into  tp_attendance (tp_id,date,in_time,in_latitude,in_longitude,out_time,out_latitude,out_longitude) values (8,'$date','09:00 AM','22.7251069','71.6352322','06:00 PM','22.7251069','71.6352322')";
		$rs=mysqli_query($cn,$q) or die(mysqli_error($cn));
		$i++;
	}
}
?>