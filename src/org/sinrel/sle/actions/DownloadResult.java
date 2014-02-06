package org.sinrel.sle.actions;

/**
 * <b>FILE_NOT_EXIST</b> - При загрузке обнаружен недоступный файл<br>
 * <b>BAD_CONNECTION</b> - Не удалось установить соединение с сервером<br>
 * <b>OK</b> - Загрузка прошла успешно
 */
public enum DownloadResult {
	FILE_NOT_EXIST,
	BAD_CONNECTION,
	OK
}