<?php

namespace sle;

class EngineSettings {

    private $version;
    private $versionCode;

    private $protectionKey = "sle2";

    /**
     * @param $version string
     * @param $versionCode integer
     * @param $protectionKey string
     */
    public function __construct( $version, $versionCode, $protectionKey ) {
        $this->version = $version;
        $this->versionCode = $versionCode;
        $this->protectionKey = $protectionKey;
    }

    /**
     * @return string
     */
    public function getVersion() {
        return $this->version;
    }

    /**
     * @return integer
     */
    public function getVersionCode() {
        return $this->versionCode;
    }

    /**
     * @return string
     */
    public function getKey() {
       return $this->protectionKey;
    }

    public function setVersion( $version ) {
        $this->version = $version;
    }

    public function setVersionCode( $versionCode ) {
        $this->versionCode = $versionCode;
    }

    public function setKey( $key ) {
        $this->protectionKey = $key;
    }

}

?>