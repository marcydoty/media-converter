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
import com.brightcove.zencoder.client.response.ZencoderOutputResponse;
import com.marcilene.entity.Output;
import com.marcilene.exception.EncoderException;
import com.marcilene.util.FileUtil;

/**
 * Classe para controle de serviços do Zencoder
 *
 */
public class EncoderService {


	private static final String API_KEY = "key_api_zencoder";

	/**
	 * Método para criar uma chamada para que o arquivo seja convertido no Zencoder.
	 * É criado um job com a chave do serviço do Zencoder que retorna a url do arquivo
	 * já convertido.
	 *
	 *  @return String - URL do arquivo no Zencoder.
	 */

	public List<Output> createJob(String url) throws ZencoderClientException, EncoderException {
		List<Output> outputList = new ArrayList<Output>();
		
		//Instanciando Cliente Zencoder e requisição  de Job
		ZencoderClient client = new ZencoderClient(API_KEY);
		ZencoderCreateJobRequest job = new ZencoderCreateJobRequest();
		
		//Setando url do arquivo a ser convertido
		job.setInput(url);
		List<ZencoderOutput> outputs = new ArrayList<ZencoderOutput>();
		
		//Setando saída e formato do arquivo para MP4
		ZencoderOutput output1 = new ZencoderOutput();
		output1.setFormat(ContainerFormat.MP4); 
		outputs.add(output1);
		
		//Setando saída e formato do arquivo para MP4
		ZencoderOutput output2 = new ZencoderOutput();
		output2.setFormat(ContainerFormat.WEBM);
		outputs.add(output2);
		
		job.setOutputs(outputs);
		
		//Realizando a requisição da URL do arquivo convertido
		ZencoderCreateJobResponse response = client.createZencoderJob(job);
		ZencoderJobDetail details = client.getZencoderJob(response.getId());
		String inputId = details.getInputMediaFile().getId();
		State inputState = null;
		
		//Aguarda o processamento do arquivo observando os estados
		do {
			ZencoderInputOutputProgress inputProgress = client.getInputProgress(inputId);
			inputState = inputProgress.getState();
			System.out.println(inputState);
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} while (!inFinalState(inputState));
		

		if (inputState == State.FINISHED) {
			for(ZencoderOutputResponse outResponse : response.getOutputs()) {
				Output output = new Output();
				output.setUrl(outResponse.getUrl());
				output.setContentType(FileUtil.contentTypeFromUrl(output.getUrl()));
				outputList.add(output);
			}
			return outputList;
		} else {
			throw new EncoderException();
		}
	}

	/**
	 * Método para tratar as respostas de estado do arquivo em processamento
	 *  @return Boolean
	 */

	private boolean inFinalState(State state) {

		if (state.equals(State.FAILED) || state.equals(State.FINISHED) || state.equals(State.CANCELLED)
				|| state.equals(State.READY)) {
			return true;
		}
		return false;
	}
}