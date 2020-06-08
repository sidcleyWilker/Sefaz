package br.com.desafiosefaz.util;

import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import br.com.desafiosefaz.model.Usuario;

public class Authorization implements PhaseListener{

	private static final long serialVersionUID = 1L;

	@Override
	public void afterPhase(PhaseEvent event) {
		FacesContext context = event.getFacesContext();
		String pagina = context.getViewRoot().getViewId();
		
		if("/index.xhtml".equals(pagina)) {
			return ;
		}
		
		Usuario usu = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("regUser");
		
		if(usu != null) {
			return ;
		}
		
		NavigationHandler handler = context.getApplication().getNavigationHandler();
		handler.handleNavigation(context, null, "/index?faces-redirect=true");
		context.renderResponse();
	}

	@Override
	public void beforePhase(PhaseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
