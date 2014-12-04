<?php

namespace sle;

class DatabaseSettings {

    private $hostname;
    private $port;

    private $username;
    private $password;

    private $databaseName;

    private $siteEngine;
    private $prefix;

    /**
     * @param $hostname string
     * @param $port string
     * @param $username string
     * @param $password string
     * @param $databaseName string
     * @param $siteEngine string
     * @param $prefix string
     */
    public function __construct( $hostname, $port, $username, $password, $databaseName, $siteEngine, $prefix ) {
        switch( strtolower( $siteEngine ) ) {
            case "wordpress";
                $this->siteEngine = "wordpress";
                break;
            case "dle":
                $this->siteEngine = "dle";
                break;
            case "xenforo":
                $this->siteEngine = "xenforo";
                break;
            case "webmcr":
                $this->siteEngine = "webmcr";
                break;
            default: $this->siteEngine = "other";
        }

        $this->hostname = $hostname;
        $this->port = $port;
        $this->username = $username;
        $this->password = $password;
        $this->databaseName = $databaseName;
        $this->prefix = $prefix;
        $this->siteEngine = $siteEngine;
    }

    /**
     * @return string
     */
    public function getHostname() {
        return $this->hostname;
    }

    /**
     * @return string
     */
    public function getPort() {
        return $this->port;
    }

    /**
     * @return string
     */
    public function getUsername() {
        return $this->username;
    }

    /**
     * @return string
     */
    public function getPassword() {
        return $this->password;
    }

    /**
     * @return string
     */
    public function getDatabaseName() {
        return $this->databaseName;
    }

    /**
     * @return string
     */
    public function getSiteEngine() {
        return $this->siteEngine;
    }

    /**
     * @return string
     */
    public function getPrefix() {
        return $this->prefix;
    }

    public function setHostname( $hostname ) {
        $this->hostname = $hostname;
    }

    public function setPort( $port ) {
        $this->port = $port;
    }

    public function setUsername( $username ) {
        $this->username = $username;
    }

    public function setPassword( $password ) {
        $this->password = $password;
    }

    public function setDatabaseName( $databaseName ) {
        $this->databaseName = $databaseName;
    }

    public function setSiteEngine( $siteEngine ) {
        switch( strtolower( $siteEngine ) ) {
            case "wordpress";
                $this->siteEngine = "wordpress";
                break;
            case "dle":
                $this->siteEngine = "dle";
                break;
            case "xenforo":
                $this->siteEngine = "xenforo";
                break;
            case "webmcr":
                $this->siteEngine = "webmcr";
                break;
            default: $this->siteEngine = "other";
        }
    }

    /**
     * @param $prefix string
     */
    public function setPrefix( $prefix ) {
        $this->prefix = $prefix;
    }

}
