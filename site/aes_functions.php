<?php
#=============#
#   alex55i   #
#=============#
if(!defined('INCLUDE_CHECK')) die('Сюда нельзя!');

function aes_encrypt_urlsafe($text, $key) {
	$encrypted = mcrypt_encrypt(MCRYPT_RIJNDAEL_128, md5($key, true), $text, MCRYPT_MODE_CBC, getIV());
	return base64_url_encode($encrypted);
}

function aes_decrypt_urlsafe($b64_encrypted, $key) {
	$data = mcrypt_decrypt(MCRYPT_RIJNDAEL_128, md5($key, true), base64_url_decode($b64_encrypted), MCRYPT_MODE_CBC, getIV());	
	return rtrim($data, $data[strlen($data) - 1]);
}

function getIV() {
	$iv = "%jUS*(Aol(-y)lC/";
	return $iv;
}

function aes_generate_key() {
	$rnd = '';
	for ($i = 0; $i < 16; $i ++) {
		$rnd .= md5(microtime(true).mt_rand(1000,90000));
	}
	return md5($rnd, true);
}

function base64_url_encode($input) {
	return strtr(base64_encode($input), '+/', '-_');
}

function base64_url_decode($input) {
	return base64_decode(strtr($input, '-_', '+/'));
}
?>
