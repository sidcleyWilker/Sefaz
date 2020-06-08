package br.com.desafiosefaz.beans;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

public class AbstractBean implements Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void reportarMensagemDeErro(String detalhe) {
		reportarMensagem(true, detalhe);

	}

	protected void reportarMensagemDeSucesso(String detalhe) {
		reportarMensagem(false, detalhe);
	}

	protected void reportarMensagem(boolean isErro, String detalhe) {
		String tipo = "Sucesso! "+detalhe;
		Severity severity = FacesMessage.SEVERITY_INFO;
		if (isErro) {
			tipo = "Erro! "+detalhe;
			severity = FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().validationFailed();
		}
		FacesMessage msg = new FacesMessage(severity, tipo, detalhe);
		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		flash.setRedirect(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

}
