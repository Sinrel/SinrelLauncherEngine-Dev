package org.sinrel.engine.actions;

public enum DownloadResult {
	FILE_NOT_EXIST, // При загрузке обнаружен недоступный файл
	BAD_CONNECTION,
	OK
}