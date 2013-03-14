package org.sinrel.engine.listeners;

import org.sinrel.engine.actions.DownloadResult;

public class DownloadAdapter implements DownloadListener, DownloadCompleteListener{

	@Override
	public void onDownloadComplete(DownloadResult result) { }

	@Override
	public void onStartDownload() { }

	@Override
	public void onFileChange(String now, String next) { }

	@Override
	public void onPercentChange(long total, int count) { }

}
