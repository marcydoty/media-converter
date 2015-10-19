package com.marcilene.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.IOUtils;

/**
 * Classe para tratamento de tipos de arquivos
 *
 */
public class FileUtil {


	/**
	 * Método para tratar  arquivo do tipo Stream
	 *
	 * @return File
	 */

	public static File streamTofile(InputStream in) throws IOException {

		final File tempFile = File.createTempFile(UUID.randomUUID().toString(), ".mp4");
		tempFile.deleteOnExit();
		FileOutputStream out = new FileOutputStream(tempFile);
		IOUtils.copy(in, out);
		return tempFile;
	}

	/**
	 * Método que faz requisição do arquivo através da URL e retorna o mesmo.
	 *
	 * @return File
	 */
	public static File getFile(String url) {

		File tempFile = null;
		int responseCode = -1;
		do {
			try {
				URL urlObj = new URL(url);
				HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				responseCode = connection.getResponseCode();
				if (responseCode != 200) {
					Thread.sleep(1000);
					continue;
				}
				tempFile = FileUtil.streamTofile(connection.getInputStream());
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (responseCode != 200);

		return tempFile;
	}
}
