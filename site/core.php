<?php
	if(!defined('INCLUDE_CHECK')) die('У вас нет прав на выполнение данного файла!');

	function hash_joomla($pass) {
		global $realPass;

		$cryptPass = false;
		$parts = explode( ':', $realPass);
		$salt = $parts[1];
		$cryptPass = md5($pass . $salt) . ":" . $salt;

		return $cryptPass;
	}

	function hash_xenforo($pass) {
		global $salt;

		$cryptPass = false;
		$cryptPass = hash('sha256', hash('sha256', $pass) . $salt);

		return $cryptPass;
	}

	function hash_wordpress($pass) {
		global $realPass;

		$cryptPass = false;
		$itoa64 = './0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz';
		$count_log2 = strpos($itoa64, $realPass[3]);
		$count = 1 << $count_log2;
		$salt = substr($realPass, 4, 8);
		$input = md5($salt . $pass, TRUE);
		do {
			$input = md5($input . $pass, TRUE);
		} 
		while (--$count);
                
		$output = substr($realPass, 0, 12);

		$count = 16;
		$i = 0;
		do {
			$value = ord($input[$i++]);
			$cryptPass .= $itoa64[$value & 0x3f];
			
			if ($i < $count)
				$value |= ord($input[$i]) << 8;
			$cryptPass .= $itoa64[($value >> 6) & 0x3f];
			
			if ($i++ >= $count)
				break;
			if ($i < $count)
				$value |= ord($input[$i]) << 16;
			$cryptPass .= $itoa64[($value >> 12) & 0x3f];
			if ($i++ >= $count)
				break;
			$cryptPass .= $itoa64[($value >> 18) & 0x3f];
		} 
	
		while ($i < $count);

		$cryptPass = $output . $cryptPass;

		return $cryptPass;
	}

	function hash_dle($pass) {
		$cryptPass = false;
		$cryptPass = md5(md5($pass));

		return $cryptPass;
	}

?>