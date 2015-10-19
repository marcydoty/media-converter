package com.marcilene.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.marcilene.enums.ContentType;

/**
 * Classe para tratamento de tipos de arquivos
 *
 */
public class FileUtil {

	/**
	 * Método para tratar arquivo do tipo Stream
	 *
	 * @return File
	 */

	public static File streamTofile(InputStream in, ContentType contentType) throws IOException {

		final File tempFile = File.createTempFile(UUID.randomUUID().toString(), "." + contentType.getExtension());
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
				tempFile = FileUtil.streamTofile(connection.getInputStream(), FileUtil.contentTypeFromUrl(url));
			} catch (Exception e) {
				e.printStackTrace();
			}
		} while (responseCode != 200);

		return tempFile;
	}

	public static ContentType contentTypeFromUrl(String url) {
		if (url.contains("?")) {
			url = url.substring(0, url.indexOf("?"));
		}

		String extension = FilenameUtils.getExtension(url);
		return ContentType.fromExtension(extension);
	}
}
