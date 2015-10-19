package com.marcilene.entity;

import java.util.List;

/**
 * Entidade de vídeo
 *
 */
public class Video {

	private String sourceUrl;
	
	private List<Output> outputs;

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public List<Output> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<Output> outputs) {
		this.outputs = outputs;
	}	
	
}
