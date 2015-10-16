package com.marcilene.controller;

import java.io.IOException;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.model.UploadedFile;

import com.marcilene.service.AmazonService;

@Model
public class UploadController {

	@Inject
	private AmazonService amazonService;
	
    private UploadedFile file;
    
    private String fileUrl;
    
    public UploadedFile getFile() {
        return file;
    }
 
    public void setFile(UploadedFile file) {
        this.file = file;
    }
     
    public void upload() {
        if(file != null) {
        	try {
        		fileUrl = amazonService.uploadFile(file.getInputstream(), file.getSize(), file.getFileName());
			} catch (IOException e) {
				e.printStackTrace();
			}
            FacesMessage message = new FacesMessage("Succesful", file.getFileName() + " is uploaded.");
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
}
