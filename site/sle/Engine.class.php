<?php

namespace sle;

use sle\modules\Cryptor;

class Engine {

    private $engineSettings;
    private $databaseSettings;

    private $cryptor;
    private $authorizer;

    /**
     * @param EngineSettings $engineSettings
     * @param DatabaseSettings $databaseSettings
     * @throws \InvalidArgumentException
     */
    public function __construct( EngineSettings $engineSettings, DatabaseSettings $databaseSettings ) {
        $this->engineSettings->$engineSettings;
        $this->databaseSettings->$databaseSettings;

        $this->cryptor = new Cryptor( $this );
    }

    public function getEngineSettings() {
        return $this->engineSettings;
    }

    public function  getDatabaseSettings() {
        return $this->databaseSettings;
    }

    /**
     * @return Cryptor
     */
    public function getCryptor() {
        return $this->cryptor;
    }

    /**
     * @return Authorizer
     */
    public function  getAuthorizer() {
        return $this->authorizer;
    }


}