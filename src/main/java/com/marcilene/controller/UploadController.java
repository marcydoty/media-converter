package com.marcilene.controller;

import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import com.marcilene.entity.Video;
import com.marcilene.service.UploadService;


/**
 * Classe que controla upload de arquivos para conversão e exibição.
 *
 */
@Model
public class UploadController {


    @Inject
    private UploadService uploadService;

    private UploadedFile file;

    private Video video;

    /**
     * Método para controle de upload de arquivos no browser
     *
     */
    public void upload(FileUploadEvent event) {


        file = event.getFile();
        if (file != null) {
            try {
                video = uploadService.uploadAndEncode(file.getInputstream(), file.getFileName(), file.getSize());
                showMessage(FacesMessage.SEVERITY_INFO, "Vídeo enviado e convertido com sucesso.");
            } catch (Exception e) {
                showMessage(FacesMessage.SEVERITY_WARN, "Erro ao enviar vídeo!");
                e.printStackTrace();
            }
        }
    }

    /**
     * Método para exibição de Mensagens
     *
     * @return String - Mensagem de erro ou sucesso.
     */

    private void showMessage(Severity severity, String message) {

        FacesMessage facesMessage = new FacesMessage(severity, message, null);
        FacesContext.getCurrentInstance().addMessage(null, facesMessage);
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }
}
