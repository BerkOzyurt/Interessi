<?php  
  //Veritabanı bağlantı dosyamızı include ettik.
  include('connection.php');
  
  //Burada herhangi bir veri gelmeyecek sadece mevcut verileri döndüreceğiz.
  
  $sorgu2 = "SELECT kullanici_adi FROM kullanicilar";
  $sorgu2Sonuc = mysql_query($sorgu2);
  $sonucDizisi['donenVeriler'] = array();
  
  /*
    basrili 1
    basarisiz 0
  */
  if($sorgu2Sonuc){
    //while ile veritabanından çekilen tüm satırları sırayla her adımda diziye
    //atıyoruz ve ardından json ile göndereceğiz.
    while($geciciDizi = mysql_fetch_array($sorgu2Sonuc)){
      $temp['tag'] = $geciciDizi['isim'];
      array_push($sonucDizisi['donenVeriler'], $temp);
      //burada, oluşturduğumuz temp dizisiyle veritabanının 'isim' sütunundan gelen
      //veriler her adımda sonuc dizimize eklendi. Ve bir json array oluşmuş oldu.
    }
    $sonucDizisi['basarilimi'] = 1;
    echo json_encode($sonucDizisi);
    //işlem başarılı olduysa basarilimi tag'ımıza da değerimizi atadık ve json ile değerleri
    //döndürdük. Başarılı olmazsa devam eden kod bloğunda başarısız oldugunu uygulamamıza bildirdik.
  }
  else{
    $sonucDizisi['basarilimi'] = 0;
    echo json_encode($sonucDizisi);
  }
  
  mysql_close();
?>