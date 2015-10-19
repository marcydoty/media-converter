package com.marcilene.test;

import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Test;

import com.brightcove.zencoder.client.ZencoderClientException;
import com.marcilene.exception.EncoderException;
import com.marcilene.service.AmazonService;
import com.marcilene.service.EncoderService;
import com.marcilene.util.FileUtil;

public class TestMediaConverter {

	EncoderService encoderService = new EncoderService();
	AmazonService amazonService = new AmazonService();
	String urlTest = "http://dinamica-sambatech.s3.amazonaws.com/sample.dv";
	String urlTestError = "http://dinamica-sambatech.s3.amazonaws.com/sample.css";
	
	@Test
	public void testUploadService() throws ZencoderClientException, EncoderException {

		String urlEncoder = encoderService.createJob(urlTest);
		File tempFile = FileUtil.getFile(urlEncoder);
		assertNotNull(amazonService.uploadFileEncoder(tempFile));

	}
	
	@Test
	public void testEncoderService() throws ZencoderClientException, EncoderException {
		EncoderService encoderService = new EncoderService();
		assertNotNull(encoderService.createJob(urlTest));

	}
	
	@Test(expected=EncoderException.class)
	public void testEncoderServiceError() throws ZencoderClientException, EncoderException {
		EncoderService encoderService = new EncoderService();
		encoderService.createJob(urlTestError);
		

	}


}
