package org.sinrel.sle.library;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

public class JsonProperties extends Properties {

	private static final long serialVersionUID = 2785306320177984438L;

	private JsonParser parser;

	public JsonProperties() {
		parser = new JsonParser();
	}

	public synchronized void load( InputStream inStream ) throws IOException {
		this.load(new InputStreamReader(inStream));
	}

	public synchronized void load(Reader reader) throws IOException {
		JsonObject root = parser.parse(reader).getAsJsonObject();
		for (Entry<String, JsonElement> elem : root.entrySet()) {
			this.put(elem.getKey(), elem.getValue().getAsString());
		}
	}

	public void store(OutputStream outputStream, String comment) throws IOException {
		this.store(new OutputStreamWriter(outputStream), comment);
	}

	public void store(Writer writer, String comment) throws IOException {
		Gson g = new Gson();
		JsonObject root = new JsonObject();

		for (Entry<Object, Object> elem : this.entrySet()) {
			root.add((String) elem.getKey(),
					new JsonPrimitive((String) elem.getValue()));
		}

		String to = g.toJson(root);
		writer.write(to);
		writer.flush();
	}

}
