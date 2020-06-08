package br.com.desafiosefaz.beans;

import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.naming.NamingException;

import br.com.desafiosefaz.model.Usuario;
import br.com.desafiosefaz.service.TelefoneService;
import br.com.desafiosefaz.service.UsuarioService;
import br.com.desafiosefaz.util.CreateDb;

@ViewScoped
@ManagedBean(name = "loginBean")
public class LoginBean extends AbstractBean{

	private static final long serialVersionUID = 1L;
	
	private UsuarioService usuarioService;
	private Usuario usuario;
	
	@PostConstruct
	public void init() {
		usuario = new Usuario();
		usuarioService = UsuarioService.getInstance();
	}
	
	public String logar() {
		try {
			TelefoneService.getInstance().executeQueryTeste(new CreateDb().criarTabelaTelefone());
		} catch (ClassNotFoundException | NamingException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Usuario usu = usuarioService.findByEmail(usuario.getEmail());
			if(usu != null) {
				if(usuario.getSenha().equals(usu.getSenha())) {
					FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("regUser", usu);
					return "listagem?faces-redirect=true";
				}
			}
		}catch (Exception e) {
			reportarMensagemDeErro(e.getMessage());
		}
		reportarMensagemDeErro("Usuario não encontrado");
		return "index?faces-redirect=true";
	}
	
	
	public String sair() {
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("regUser");
		return "index?faces-redirect=true";
	}
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
