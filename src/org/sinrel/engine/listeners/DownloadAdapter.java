package org.sinrel.engine.listeners;

import org.sinrel.engine.actions.DownloadResult;

public class DownloadAdapter implements DownloadListener, DownloadCompleteListener {

	public void onDownloadComplete(DownloadResult result) {
	}

	public void onStartDownload() {
	}

	public void onFileChange(String now, String next) {
	}

	public void onPercentChange(long total, int count) {
	}

}
