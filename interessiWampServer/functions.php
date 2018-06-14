<?php
 

 
class DB_Functions {
 
    private $conn;
 
    // constructor
    function __construct() {
        require_once 'connection.php';
        // veritabanına bağlan
        $db = new connection();
        $this->conn = $db->connect();
    }
 
    // destructor
    function __destruct() {
         
    }
 
  
 
    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($mail, $sifre) {
 
        $stmt = $this->conn->prepare("SELECT * FROM kullanicilar WHERE email = ?");
 
        $stmt->bind_param("s", $mail);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            // verifying user password
            $salt = $user['salt'];
            $encrypted_password = $user['encrypted_password'];
            $hash = $this->checkhashSSHA($salt, $mail);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $user;
            }
        } else {
            return NULL;
        }
    }
 
    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
        $stmt = $this->conn->prepare("SELECT email from users WHERE email = ?");
 
        $stmt->bind_param("s", $email);
 
        $stmt->execute();
 
        $stmt->store_result();
 
        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }
 
    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($sifre) {
 
        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($sifre . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }
 
    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $sifre) {
 
        $hash = base64_encode(sha1($sifre . $salt, true) . $salt);
 
        return $hash;
    }
 
}
 
?>