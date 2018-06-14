<?php
include "db.php";
include "function.php";
$islem = isset($_GET["islem"]) ? addslashes(trim($_GET["islem"])) : null;
$jsonArray = array(); // array değişkenimiz bunu en alta json objesine çevireceğiz. 
$jsonArray["hata"] = FALSE; // Başlangıçta hata yok olarak kabul edelim. 

$_code = 200; // HTTP Ok olarak durumu kabul edelim. 

	
    // üye ekleme kısmı burada olacak. CREATE İşlemi 
 if($_SERVER['REQUEST_METHOD'] == "POST") {
 	
 	// verilerimizi post yöntemi ile alalım. 
    $ad = addslashes($_POST["ad"]);
    $soyad = addslashes($_POST["soyad"]);
    $kullanici_adi = addslashes($_POST["kullanici_adi"]);
    $mail = addslashes($_POST["mail"]);
    $sifre = addslashes($_POST["sifre");

    
    // Kontrollerimizi yapalım.
    // gelen kullanıcı adı veya e-posta veri tabanında kayıtlı mı kontrol edelim. 
    $uyeler = $db->query("SELECT * from uyeler WHERE kullaniciAdi='$kullaniciAdi' OR posta='$posta'");
    
    if(empty($kullaniciAdi) || empty($adSoyad) || empty($sifre) || empty($posta) || empty($telefon)) {
    	$_code = 400; 
		$jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
        $jsonArray["hataMesaj"] = "Boş Alan Bırakmayınız."; // Hatanın neden kaynaklı olduğu belirtilsin.
	}
    else if(!filter_var($posta,FILTER_VALIDATE_EMAIL)) {
    	$_code = 400;
        $jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
        $jsonArray["hataMesaj"] = "Geçersiz E-Posta Adresi"; // Hatanın neden kaynaklı olduğu belirtilsin. 
    }else if($kullaniciAdi != kullaniciAdi($kullaniciAdi)){ // kullaniciAdi fonksiyonunu db.php dosyası içerisinden bakabilirsiniz.
        $_code = 400;
        $jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
        $jsonArray["hataMesaj"] = "Geçersiz Kullanıcı Adı"; // Hatanın neden kaynaklı olduğu belirtilsin.    
    }else if($uyeler->rowCount() !=0) {
    	$_code = 400;
        $jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
        $jsonArray["hataMesaj"] = "Kullanıcı Adı Veya E-Posta Alınmış."; 
    }else {
    	
			$ex = $db->prepare("insert into uyeler set 
			kullaniciAdi= :kadi, 
			adSoyad= :ads, 
			sifre= :pass, 
			posta= :mail, 
			telefon= :tlf");
		$ekle = $ex->execute(array(
			"kadi" => $kullaniciAdi,
			"ads" => $adSoyad,
			"pass" => $sifre,
			"mail" => $posta,
			"tlf" => $telefon
			
		));
		
		
		if(	!$ekle) {
			$_code = 201;
			$jsonArray["mesaj"] = "Ekleme Başarılı.";
		}else {
			$_code = 400;
			 $jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
       		 $jsonArray["hataMesaj"] = "Sistem Hatası.";
		}
	}
}else if($_SERVER['REQUEST_METHOD'] == "PUT") {
     $gelen_veri = json_decode(file_get_contents("php://input")); // veriyi alıp diziye atadık.
    	
    	// basitçe bi kontrol yaptık veriler varmı yokmu diye 
     if(	isset($gelen_veri->kullanici_adi) && 
     		isset($gelen_veri->ad_soyad) && 
     		isset($gelen_veri->posta) && 
     		isset($gelen_veri->user_id) && 
     		isset($gelen_veri->telefon)
     	) {
     		
     		// veriler var ise güncelleme yapıyoruz.
				$q = $db->prepare("UPDATE uyeler SET kullaniciAdi= :kadi, adSoyad= :ad_soyad, posta= :posta, telefon= :telefon WHERE id= :user_id ");
			 	$update = $q->execute(array(
			 			"kadi" => $gelen_veri->kullanici_adi,
			 			"ad_soyad" => $gelen_veri->ad_soyad,
			 			"posta" => $gelen_veri->posta,
			 			"telefon" => $gelen_veri->telefon,
			 			"user_id" => $gelen_veri->user_id	 	
			 	));
			 	// güncelleme başarılı ise bilgi veriyoruz. 
			 	if($update) {
			 		$_code = 200;
			 		$jsonArray["mesaj"] = "Güncelleme Başarılı";
			 	}
			 	else {
			 		// güncelleme başarısız ise bilgi veriyoruz. 
			 		$_code = 400;
					$jsonArray["hata"] = TRUE;
		 			$jsonArray["hataMesaj"] = "Sistemsel Bir Hata Oluştu";
				}
		}else {
			// gerekli veriler eksik gelirse apiyi kulanacaklara hangi bilgileri istediğimizi bildirdik. 
			$_code = 400;
			$jsonArray["hata"] = TRUE;
	 		$jsonArray["hataMesaj"] = "kullanici_adi,ad_soyad,posta,telefon,user_id Verilerini json olarak göndermediniz.";
		}
} else if($_SERVER['REQUEST_METHOD'] == "DELETE") {

    // üye silme işlemi burada olacak. DELETE işlemi 
    if(isset($_GET["user_id"]) && !empty(trim($_GET["user_id"]))) {
		$user_id = intval($_GET["user_id"]);
		$userVarMi = $db->query("select * from uyeler where id='$user_id'")->rowCount();
		if($userVarMi) {
			
			$sil = $db->query("delete from uyeler where id='$user_id'");
			if( $sil ) {
				$_code = 200;
				$jsonArray["mesaj"] = "Üyelik Silindi.";
			}else {
				// silme başarısız ise bilgi veriyoruz. 
				$_code = 400;
				$jsonArray["hata"] = TRUE;
	 			$jsonArray["hataMesaj"] = "Sistemsel Bir Hata Oluştu";
			}
		}else {
			$_code = 400; 
			$jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
    		$jsonArray["hataMesaj"] = "Geçersiz id"; // Hatanın neden kaynaklı olduğu belirtilsin.
		}
	}else {
		$_code = 400;
		$jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
    	$jsonArray["hataMesaj"] = "Lütfen user_id değişkeni gönderin"; // Hatanın neden kaynaklı olduğu belirtilsin.
	}
} else if($_SERVER['REQUEST_METHOD'] == "GET") {


    // üye bilgisi listeleme burada olacak. GET işlemi 
    if(isset($_GET["kullanici_adi"]) && !empty(trim($_GET["kullanici_adi"]))) {
		$user_id = intval($_GET["kullanici_adi"]);
		$userVarMi = $db->query("select * from kullanicilar where kullanici_adi='$kullanici_adi'")->rowCount();
		if($userVarMi) {
			
			$bilgiler = $db->query("select * from  kullanicilar where kullanici_adi='$kullanici_adi'")->fetch(PDO::FETCH_ASSOC);
			$jsonArray["uye-bilgileri"] = $bilgiler;
			$_code = 200;
			
		}else {
			$_code = 400;
			$jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
    		$jsonArray["hataMesaj"] = "Üye bulunamadı"; // Hatanın neden kaynaklı olduğu belirtilsin.
		}
	}else {
		$_code = 400;
		$jsonArray["hata"] = TRUE; // bir hata olduğu bildirilsin.
    	$jsonArray["hataMesaj"] = "Lütfen user_id değişkeni gönderin"; // Hatanın neden kaynaklı olduğu belirtilsin.
	}
}else {
	$_code = 406;
	$jsonArray["hata"] = TRUE;
 	$jsonArray["hataMesaj"] = "Geçersiz method!";
}


SetHeader($_code);
$jsonArray[$_code] = HttpStatus($_code);
echo json_encode($jsonArray);
?>