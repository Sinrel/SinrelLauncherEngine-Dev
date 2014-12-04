<?php
define("INCLUDE_CHECK", true);

require_once 'config.php';

use sle\Engine;
use sle\EngineSettings;
use sle\DatabaseSettings;
use sle\network\Response;

if( !isset( $_GET['query'] ) | empty( $_GET['query'] ) )
	die('{"command":"empty"}');

try{
    //----
    $settings = new EngineSettings( $version, $versionCode, $protectionKey );
    $databaseSettings = new DatabaseSettings( $hostname, $port, $username, $password, $databaseName, $integration, $prefix );
    $engine = new Engine( $settings, $databaseSettings  );
    //----

    $query = $engine->getCryptor()->decryptWithKey( $_GET['query'], "sle2" );
    if( strpos($query,'"command":"key"') !== false ) {
      die('{"command:"key","properties":{"key":'.$protectionKey.'}}');
    }

    $query = $engine->getCryptor()->decrypt( $_GET['query'] );
    //$query = AES::decrypt( . , $protectionKey );

    $request = json_decode( $query );
    unset( $query );

    if( !(json_last_error() === JSON_ERROR_NONE) ) die('{"command":"empty"}');
    if( $request->command == "empty" ) die('{"command":"empty"}');

    $response = new Response();
    switch( strtolower( $request->command ) ) {
        case "key":
           $response->setCommand( "key" );
          $response->put( "key", $protectionKey );
          return "";
    }
}catch( Exception $e) {
	echo $e->getTraceAsString();
}

