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

	private static final String BUCKET_NAME = "namebucket";
	private static final String ACCESS_KEY = "acces_key";
	private static final String SECRET_KEY = "secret_key";
	private static final String URL_PATTERN = "https://s3-sa-east-1.amazonaws.com/{0}/{1}";

	private AWSCredentials credentials;
	private AmazonS3 client;

	public AmazonService() {
		credentials = new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY);
		client = new AmazonS3Client(credentials);
	}

	public String buildUrl(String fileKey) {
		return MessageFormat.format(URL_PATTERN, BUCKET_NAME, fileKey);
	}

	public String uploadFile(InputStream input, Long size, String fileName) {
		String fileKey = UUID.randomUUID().toString() + fileName;
		ObjectMetadata metadata = new ObjectMetadata();

		metadata.setContentLength(size);
		client.putObject(new PutObjectRequest(BUCKET_NAME, fileKey, input, metadata)
				.withCannedAcl(CannedAccessControlList.PublicRead));

		return buildUrl(fileKey);
	}

	public String uploadFile(File input) {
		String fileKey = UUID.randomUUID().toString() + input.getName();
		client.putObject(
				new PutObjectRequest(BUCKET_NAME, fileKey, input).withCannedAcl(CannedAccessControlList.PublicRead));

		return buildUrl(fileKey);
	}
}