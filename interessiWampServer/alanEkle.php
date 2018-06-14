<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	createStudent();
}


function createStudent()
{
	global $connect;
	
	$ad = $_POST["alan_id"];
	$soyad = $_POST["alan_adi"];
	

	$query = " Insert into alanlar(alan_id,alan_adi) values ('$alan_id','$alan_adi');";
	
	mysqli_query($connect, $query) or die (mysqli_error($connect));
	mysqli_close($connect);
	
}








?>
