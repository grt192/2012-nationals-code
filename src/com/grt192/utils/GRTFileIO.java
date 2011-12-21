package com.grt192.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import javax.microedition.io.Connector;

import org.json.me.JSONException;
import org.json.me.JSONObject;
import org.json.me.JSONTokener;

import com.sun.squawk.io.BufferedReader;
import com.sun.squawk.microedition.io.FileConnection;

public class GRTFileIO {

	public static String getFileContents(String filename) {
		String url = "file:///" + filename;
		String contents = "";
		try {
			FileConnection c = (FileConnection) Connector.open(url);
			BufferedReader buf = new BufferedReader(new InputStreamReader(c
					.openInputStream()));
			String line = "";
			while ((line = buf.readLine()) != null) {
				contents += line + "\n";
			}
			c.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return contents;
	}

	public static JSONObject fromJSONFile(String filename) {
		try {
			return new JSONObject(new JSONTokener(getFileContents(filename)));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void writeToFile(String filename, String contents) {
		String url = "file:///" + filename;
		try {
			FileConnection c = (FileConnection) Connector.open(url);
			OutputStreamWriter writer = new OutputStreamWriter(c
					.openOutputStream());
			writer.write(contents);
			c.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
