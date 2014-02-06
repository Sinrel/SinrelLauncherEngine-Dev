package org.sinrel.sle.actions;

import org.sinrel.sle.Engine;

public class LauncherData extends Data {
	
	public LauncherData( Engine engine, int versionCode, String version ) {
		super( engine, versionCode, version );
	}

	/**
	 * @return Возвращает результат проверки на устаревшесть лаунчера.<br>
	 * Если true, то значит, что текущий лаунчер устарел.<br><br>
	 * <b>Проверяется только код версии!</b><br>True-ответ, означающий устаревшесть, возможет только в случае более большего кода версии на сервере.
	 */
	@Override
	public boolean isOutdated() {
		if( getVersionCode() > engine.getSettings().getVersionCode() ) return true;
		return false;
	}
	
}
