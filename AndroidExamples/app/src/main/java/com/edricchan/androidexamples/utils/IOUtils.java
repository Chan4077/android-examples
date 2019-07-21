package com.edricchan.androidexamples.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;

public final class IOUtils {
	private static final String TAG = IOUtils.class.getSimpleName();

	public static void copy(InputStream is, File file) {
		try {
			FileOutputStream os = new FileOutputStream(file);
			copy(is, os);
		} catch (FileNotFoundException e) {
			Log.e(TAG, e.toString(), e);
		}
	}

	public static void copy(InputStream is, OutputStream os) {
		try {

			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				os.write(buf, 0, len);
			}

		} catch (IOException e) {
			Log.e(TAG, e.toString(), e);
		} finally {
			close(is);
			close(os);
		}
	}

	public static void close(Closeable stream) {
		try {
			if (stream != null) {
				stream.close();
			}
		} catch (IOException e) {
			Log.e(TAG, e.toString(), e);
		}
	}

	public static InputStream getStream(String url) {

		InputStream is = null;
		try {
			is = new URL(url).openConnection().getInputStream();
		} catch (IOException e) {
			Log.e(TAG, e.toString(), e);
		}

		return is;
	}

	public static String toString(InputStream inputStream) {
		BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
		StringBuilder total = new StringBuilder();
		String line;
		try {
			while ((line = br.readLine()) != null) {
				total.append(line);
			}
		} catch (IOException e) {
			Log.e(TAG, e.toString(), e);
		}

		return total.toString();
	}
}
