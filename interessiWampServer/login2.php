<?php
require "init.php";
$kullanici_adi=$_POST["kullanici_adi"];
$sifre=$_POST["sifre"];
$sql_query="select kullanici_adi, sifre from kullanicilar where kullanici_adi like '$kullanici_adi' and sifre like '$sifre';";
$result=mysqli_query($con,$sql_query);
if(mysqli_num_rows($result)>0)
{
$row=mysqli_fetch_assoc($result);
$name=$row["name"];
echo"Login Success...Welcome ".$name;
}
else
{
echo"Login Failed...Try Again";
}
?>