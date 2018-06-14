<?php
define('DB_NAME', 'interessi');//kendi database adınız
/** MySQL veritabanı kullanıcısı */
define('DB_USER', 'root');//kendi kullanıcı adınız
/** MySQL veritabanı parolası */
define('DB_PASSWORD', '');//kendi şifreniz
/** MySQL sunucusu */
define('DB_HOST', 'localhost');
/** Yaratılacak tablolar için veritabanı karakter seti. */
define('DB_CHARSET', 'utf8');
$con=mysqli_connect(DB_HOST,DB_USER,DB_PASSWORD,DB_NAME);

// Check connection
if (mysqli_connect_errno())
  {
  echo "Bağlanırken hata oluştu : " . mysqli_connect_error();
  }

?>