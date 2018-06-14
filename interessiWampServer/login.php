<?php
 
    $hostname_localhost ="localhost";
    $database_localhost ="interessi";
    $username_localhost ="root";
    $password_localhost ="";
    $localhost = mysql_connect($hostname_localhost,$username_localhost,$password_localhost)
    or
    trigger_error(mysql_error(),E_USER_ERROR);
  
    mysql_select_db($database_localhost, $localhost);
  
    $ukullanici_adi = $_POST['kullanici_adi'];
    $psifre = $_POST['sifre'];
    $query_search = "select * from kullanicilar where kullanici_adi = '".$kullanici_adi."' AND sifre = '".$sifre. "'";
    $query_exec = mysql_query($query_search) or die(mysql_error());
    $rows = mysql_num_rows($query_exec);
     
    if($rows == 0) { 
        echo "No Such User Found"; 
    }
    else  {
        echo "User Found"; 
    }
     
?>