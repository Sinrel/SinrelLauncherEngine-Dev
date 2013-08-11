package org.sinrel.engine.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javassist.CannotCompileException;
import javassist.CtClass;

public class VirtualZipModifer {
	private File file;
	private ZipFile zipFile;
	private Map<String, byte[]> virtualEntries;

	public VirtualZipModifer(File file) throws ZipException, IOException {
		this.file = file;
		virtualEntries = new HashMap<String, byte[]>();
	}

	public File getFile() {
		return file;
	}

	public byte[] getBytes(String key) {
		return virtualEntries.get(key);
	}

	public Map<String, byte[]> getEntriesMap() {
		synchronized (this) {
			return virtualEntries;
		}
	}

	public void putEntry(String key, byte[] value) {
		synchronized (this) {
			virtualEntries.put(key, value);
		}
	}

	public void putClass(CtClass clazz) throws IOException, CannotCompileException {
		synchronized (this) {
			virtualEntries.put(clazz.getName().replace('.', '/') + ".class", clazz.toBytecode());
		}
	}

	public void write() throws IOException, InterruptedException {
		write(false);
	}

	public void write(boolean ignoreMetaInf) throws IOException, InterruptedException {
		synchronized (this) {
			Map<String, byte[]> newEntries = new HashMap<String, byte[]>();

			zipFile = new ZipFile(file);
			Enumeration<? extends ZipEntry> numer = zipFile.entries();
			while (numer.hasMoreElements()) {
				ZipEntry entry = numer.nextElement();
				String name = entry.getName();
				if (!(ignoreMetaInf && name.startsWith("META-INF"))) {
					if (!virtualEntries.containsKey(name))
						newEntries.put(name, toByteArray(zipFile.getInputStream(entry)));
				}
			}
			zipFile.close();

			Set<Entry<String, byte[]>> entrySet1 = virtualEntries.entrySet();
			for (Entry<String, byte[]> entry : entrySet1) {
				String name = entry.getKey();
				if (!(ignoreMetaInf && name.startsWith("META-INF"))) {
					newEntries.put(name, entry.getValue());
				}
			}

			ZipOutputStream zos = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
			try {
				Set<Entry<String, byte[]>> entrySet2 = newEntries.entrySet();
				for (Entry<String, byte[]> entry : entrySet2) {
					zos.putNextEntry(new ZipEntry(entry.getKey()));
					zos.write(entry.getValue());
					zos.closeEntry();
				}
				Thread.sleep(0, 1);
			} finally {
				zos.flush();
				zos.close();
			}

			virtualEntries.clear();
		}
	}

	private byte[] toByteArray(InputStream input) throws IOException {
		ByteArrayOutputStream output = new ByteArrayOutputStream();

		byte[] buffer = new byte[1024 * 4];
		int n = 0;
		while (-1 != (n = input.read(buffer)))
			output.write(buffer, 0, n);
		return output.toByteArray();
	}
	
}
