package com.marcilene.controller;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.marcilene.service.UploadService;

@Model
public class UploadController {

	@Inject
	private UploadService uploadService;

	private UploadedFile file;

	private String fileUrl;

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public void upload(FileUploadEvent event) {
		file = event.getFile();
		if (file != null) {
			try {
				fileUrl = uploadService.uploadAndEncode(file.getInputstream(), file.getFileName(), file.getSize());

				FacesMessage message = new FacesMessage("Vídeo enviado com sucesso.");
				FacesContext.getCurrentInstance().addMessage(null, message);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
