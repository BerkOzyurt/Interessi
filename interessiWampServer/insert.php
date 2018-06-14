<?php

if($_SERVER["REQUEST_METHOD"]=="POST"){
	require 'connection.php';
	createStudent();
}


function createStudent()
{
	global $connect;
	
	$ad = $_POST["ad"];
	$soyad = $_POST["soyad"];
	$kullanici_adi = $_POST["kullanici_adi"];
	$mail = $_POST["mail"];
	$sifre = $_POST["sifre"];

	$query = " Insert into kullanicilar(ad,soyad,kullanici_adi,mail,sifre) values ('$ad','$soyad', '$kullanici_adi', '$mail', '$sifre');";
	
	mysqli_query($connect, $query) or die (mysqli_error($connect));
	mysqli_close($connect);
	
}








?>
