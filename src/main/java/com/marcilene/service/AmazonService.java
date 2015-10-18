package com.marcilene.service;

import java.io.File;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.UUID;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class AmazonService {
	/**
	 * Classe para controle de serviços da Amazon
	 * 
	 */

	private static final String BUCKET_NAME = "newbucketmarcydoty";
	private static final String ACCESS_KEY = "AKIAJ7J34V3CXL4OXJGA";
	private static final String SECRET_KEY = "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";
	private static final String URL_PATTERN = "https://s3-sa-east-1.amazonaws.com/{0}/{1}";

	private AWSCredentials credentials;
	private AmazonS3 client;

	public AmazonService() {
		/**
		 * Método para instanciar credenciais em cliente AmazonS3
		 * 
		 */

		credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		client = new AmazonS3Client(credentials);
	}

	public String buildUrl(String fileKey) {
		/**
		 * Método para construção da URL a ser enviada exibição do vídeo
		 * 
		 * @return String - URL.
		 */

		return MessageFormat.format(URL_PATTERN, BUCKET_NAME, fileKey);
	}

	public String uploadFile(InputStream input, Long size, String fileName) {
		/**
		 * Método upload de arquivo no Amazon S3
		 * 
		 * @return String - URL do arquivo na Amazon S3.
		 */

		String fileKey = UUID.randomUUID().toString() + fileName;
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(size);

		// Subindo o arquivo para o servidor
		client.putObject(new PutObjectRequest(BUCKET_NAME, fileKey, input, metadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));

		return buildUrl(fileKey);
	}

	public String uploadFile(File input) {
		/**
		 * Método upload de arquivo no Amazon S3, com envio somente do parâmetro
		 * input.
		 * 
		 * @return String - URL.
		 */

		String fileKey = UUID.randomUUID().toString() + input.getName();
		client.putObject(
				new PutObjectRequest(BUCKET_NAME, fileKey, input)
				.withCannedAcl(CannedAccessControlList.PublicRead));

		return buildUrl(fileKey);
	}
}
