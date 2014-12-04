<?php

namespace sle\modules;

use sle\Engine;

class Cryptor {

    private $engine;

    private $protectionKey;

    public function __construct( Engine $engine ) {
        $this->engine = $engine;
        $this->protectionKey = $this->engine->getEngineSettings()->getKey();
    }

    public function getKey() {
        return $this->engine->getEngineSettings()->getKey();
    }

    /**
     * @param $text
     * @return string
     */
    public function encrypt( $text ) {
        return $this->encryptWithKey( $text, $this->protectionKey );
    }

    /**
     * @param $encrypted
     * @return string
     */
    public function decrypt( $encrypted ) {
        return $this->decryptWithKey( $encrypted, $this->protectionKey );
    }

    //Почему нет перезагрузки методов в php !?

   public function encryptWIthKey($text, $key) {
        $encrypted = mcrypt_encrypt(MCRYPT_RIJNDAEL_128, md5($key, true), $text, MCRYPT_MODE_CBC, $this->getIV());
        return $this->base64_url_encode($encrypted);
    }

   public function decryptWithKey($b64_encrypted, $key) {
        $data = mcrypt_decrypt(MCRYPT_RIJNDAEL_128, md5($key, true), $this->base64_url_decode($b64_encrypted), MCRYPT_MODE_CBC, $this->getIV());
        return rtrim($data, $data[strlen($data) - 1]);
    }

    //Почему нет перезагрузки методов в php !?


    private function getIV() {
        $iv = "%jUS*(Aol(-y)lC/";
        return $iv;
    }

    private function base64_url_encode($input) {
        return strtr(base64_encode($input), '+/', '-_');
    }

   private function base64_url_decode($input) {
        return base64_decode(strtr($input, '-_', '+/'));
    }

}