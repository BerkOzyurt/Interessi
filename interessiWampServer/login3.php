<?php 
require_once("baglan.php");//database bağlantısı gercekleştirdik

if($_POST){//eğer posttan geliyorsa işlem yapacak
	$mail = $_POST["mail"];//mail adresini aldık
	$sifre = $_POST["sifre"];//sifreyi aldık
	//mail ve şifreyi androidde kontrol etmiştik .Güvenlik acısından burdada kontrol edeceğiz.
	$hata = false;
	$sonucmesaji = "";
	
	if($mail==""){//mail bos mu
		$hata = true;
		$sonucmesaji = "Mail Adresiniz Boş Olamaz.";
	}
	if(strlen($sifre)<6){//şifre 6 haneden kısamı
		$hata =true;
		$sonucmesaji = "Şifre 6 Haneden kısa olamaz.";
	}
	if (!filter_var($mail, FILTER_VALIDATE_EMAIL)) {//mail format kontrol
		$hata =true;
		$sonucmesaji = "Mail Formatı Yanlış.";
	}
	
	if(!$hata){//eğer hata yoksa database sorgusu yapılacak
		
		$sql ="SELECT * FROM wp_uyeler WHERE mail='$mail' AND sifre='$sifre'";//mail ve şifre kontrolü
		$sonuc = mysqli_query($con,$sql);
		if(mysqli_num_rows($sonuc)>0){//0 dan fazla veri varsa 
			$sonucmesaji = "Giriş Başarılı.";
			$cevap = array('sonuc' => "1", 'sonucmesaji' => $sonucmesaji);
		}else{
			$sonucmesaji = "Kullanıcı Bulunamadı.";
			$cevap = array('sonuc' => "0", 'sonucmesaji' =>  $sonucmesaji);
		}
		mysqli_close($con);//database bağlantısını kapattık

	}else{
		$cevap = array('sonuc' => "0",'sonucmesaji' => $sonucmesaji); 
	}
	echo json_encode($cevap);// json verisini yazdırdık
}else{
	echo "Giriş Engellendi";
}
?>