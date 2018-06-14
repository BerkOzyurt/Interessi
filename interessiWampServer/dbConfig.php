//mysql_connect fonksiyonu sırasıyla 3 parametre alır
//sunucu adresi, kullanıcı adı ve şifre
$dbBaglanti=@mysql_connect("localhost","root","") 
    or die("Server baglanti saglanilamadi.");
if($dbBaglanti)
{
  //mysql select db fonksiyonu 2 parametre alır
  //1.si kullanılacak veritabanı adı
  //2.si üst kısımda gerçekleştirilen bağlantıdır.
  mysql_select_db("interessi",$dbBaglanti) 
      or die("Database baglanti saglanilamadi.");  
}