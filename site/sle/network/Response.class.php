<?php

namespace sle\network;

class Response {

    private $command;
    private $properties;

    public function __construct() {
        $this->command = "empty";
        $this->properties = array();
    }

    /**
     * @param $command - string
     */
    public function setCommand( $command ) {
        $this->command = $command;
    }

    /**
     * @return string
     */
    public function getCommand() {
        return $this->command;
    }

    /**
     * @param $key
     * @return mixed
     */
    public function get( $key ) {
            return $this->properties[ $key ];
    }

    /**
     * @param $key string
     * @param $value mixed
     */
    public function put( $key, $value ) {
        $this->properties[ $key ] = $value;
    }

    /**
     * @return string
     */
    public function toJson() {
        return json_encode( $this );
    }

}