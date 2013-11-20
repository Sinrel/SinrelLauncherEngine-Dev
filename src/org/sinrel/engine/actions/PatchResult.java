package org.sinrel.engine.actions;

/**
 * <b>CLIENT_NOT_EXIST</b> - Не найден указанный для модификации клиент<br>
 * <b>NOT_COMPLETED</b> - Модифицирование не было произведено<br>
 * <b>BAD_CONNECTION</b> - Модифицирование было прервано из-за отсутствия соединения с сервисом Sinrel Box
 * <b>OK</b> - Модифицирование прошло успешно
 */
public enum PatchResult {
	CLIENT_NOT_EXIST, 
	NOT_COMPLETED,
	BAD_CONNECTION,
	OK
}
