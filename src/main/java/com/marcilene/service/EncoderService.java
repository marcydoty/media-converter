package com.marcilene.service;

import java.util.ArrayList;
import java.util.List;

import com.brightcove.zencoder.client.ZencoderClient;
import com.brightcove.zencoder.client.ZencoderClientException;
import com.brightcove.zencoder.client.model.ContainerFormat;
import com.brightcove.zencoder.client.request.ZencoderCreateJobRequest;
import com.brightcove.zencoder.client.request.ZencoderOutput;
import com.brightcove.zencoder.client.response.ZencoderCreateJobResponse;


public class EncoderService {
	private static final String API_KEY = "key_api_zencoder";
	
	public String createJob(String url_pattern) throws ZencoderClientException {
		ZencoderClient client = new ZencoderClient(API_KEY);
		
		ZencoderCreateJobRequest job = new ZencoderCreateJobRequest();
		
		job.setInput(url_pattern);
		List<ZencoderOutput> outputs = new ArrayList<ZencoderOutput>();

		ZencoderOutput output1 = new ZencoderOutput();
		output1.setFormat(ContainerFormat.MP4);
		outputs.add(output1);

		ZencoderOutput output2 = new ZencoderOutput();
		output2.setFormat(ContainerFormat.WEBM);
		outputs.add(output2);
		
			
		job.setOutputs(outputs);
		ZencoderCreateJobResponse response = client.createZencoderJob(job);
		
		
		return response.getOutputs().get(1).getUrl();

	}
	

	
	
}