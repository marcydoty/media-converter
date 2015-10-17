package com.marcilene.service;

import java.util.ArrayList;
import java.util.List;

import com.brightcove.zencoder.client.ZencoderClient;
import com.brightcove.zencoder.client.ZencoderClientException;
import com.brightcove.zencoder.client.model.ContainerFormat;
import com.brightcove.zencoder.client.model.State;
import com.brightcove.zencoder.client.request.ZencoderCreateJobRequest;
import com.brightcove.zencoder.client.request.ZencoderOutput;
import com.brightcove.zencoder.client.response.ZencoderCreateJobResponse;
import com.brightcove.zencoder.client.response.ZencoderInputOutputProgress;
import com.brightcove.zencoder.client.response.ZencoderJobDetail;

public class EncoderService {
	private static final String API_KEY = "key_api_zencoder";

	public String createJob(String url) throws ZencoderClientException {
		ZencoderClient client = new ZencoderClient(API_KEY);
		ZencoderCreateJobRequest job = new ZencoderCreateJobRequest();
		job.setInput(url);
		List<ZencoderOutput> outputs = new ArrayList<ZencoderOutput>();

		ZencoderOutput output1 = new ZencoderOutput();
		output1.setFormat(ContainerFormat.MP4);
		outputs.add(output1);

		ZencoderOutput output2 = new ZencoderOutput();
		output2.setFormat(ContainerFormat.WEBM);
		outputs.add(output2);

		job.setOutputs(outputs);
		ZencoderCreateJobResponse response = client.createZencoderJob(job);
		ZencoderJobDetail details = client.getZencoderJob(response.getId());
		String inputId = details.getInputMediaFile().getId();
		State inputState = null;
		do {
			ZencoderInputOutputProgress inputProgress = client.getInputProgress(inputId);
			inputState = inputProgress.getState();
			System.out.println(inputState);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (inputState != State.FINISHED);

		String result = response.getOutputs().get(0).getUrl();

		return result;

	}
}