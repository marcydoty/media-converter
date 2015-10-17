package com.marcilene.service;

import java.io.File;
import java.io.InputStream;

import javax.inject.Inject;

import com.marcilene.util.FileUtil;

public class UploadService {

	@Inject
	private AmazonService amazonService;

	@Inject
	private EncoderService encoderService;

	public String uploadAndEncode(InputStream input, String name, Long size) {
		String fileUrl = null;
		try {
			fileUrl = amazonService.uploadFile(input, size, name);
			String urlEncoded = encoderService.createJob(fileUrl);
			File tempFile = FileUtil.getFile(urlEncoded);
			fileUrl = amazonService.uploadFile(tempFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileUrl;

	}
}
