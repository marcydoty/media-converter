package com.marcilene.service;

import java.io.File;
import java.io.InputStream;

import javax.inject.Inject;

import com.brightcove.zencoder.client.ZencoderClientException;
import com.marcilene.entity.Output;
import com.marcilene.entity.Video;
import com.marcilene.exception.EncoderException;
import com.marcilene.util.FileUtil;
/**
 * Classe de interseção de serviços Amazon e Zencoder para o upload de
 * arquivos
 *
 */
public class UploadService {


	@Inject
	private AmazonService amazonService;

	@Inject
	private EncoderService encoderService;

	/**
	 * Método realizar upload do arquivo de entrada no Amazon S3 e conversão
	 * com Zencoder salvando o arquivo convertido também Amazon S3.
	 *
	 * @return Video
	 */

	public Video uploadAndEncode(InputStream input, String name, Long size)
			throws ZencoderClientException, EncoderException {

		Video video = new Video();

		video.setSourceUrl(amazonService.uploadFile(input, size, name));
		video.setOutputs(encoderService.createJob(video.getSourceUrl()));
		for(Output output : video.getOutputs()) {
			File tempFile = FileUtil.getFile(output.getUrl());
			output.setUrl(amazonService.uploadFile(tempFile));			
		}
		return video;
	}
}
