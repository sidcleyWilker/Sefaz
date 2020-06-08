package br.com.desafiosefaz.beans;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.naming.NamingException;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.primefaces.PrimeFaces;

import br.com.desafiosefaz.enums.TipoTelefone;
import br.com.desafiosefaz.model.Telefone;
import br.com.desafiosefaz.model.Usuario;
import br.com.desafiosefaz.service.UsuarioService;

@ViewScoped
@ManagedBean(name = "usuarioBean")
public class UsuarioBean extends AbstractBean{

	private static final long serialVersionUID = 1L;
	
	private Usuario usuario;
	private Telefone telefone;
	private String tipo;
	private UsuarioService usuarioService;
	private List<Usuario> usuarios;
	

	@PostConstruct
	public void init() {
		Usuario u = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("usuario");
		usuario = u != null ? u : new Usuario();
		telefone = new Telefone();
		usuarioService = UsuarioService.getInstance();
		caregarUsuarios();
	}
	
	public String cadastrarNovo() {
		return "cadastro?faces-redirect=true";
	}
	
	public String listagem() {
		return "listagem?faces-redirect=true";
	}
	
	public void removerTelefone(Telefone tel) {
		usuario.getTelefones().remove(tel);
	}
	
	public void visualizar(Usuario usu) {
		usuario = usu;
	}
	
	public void adicionarTelefone() {
		if(telefone.getDdd() == null || telefone.getDdd().trim().equals("") || telefone.getNumero() == null || telefone.getNumero().trim().equals("")) {
			reportarMensagemDeErro("Telefone invalido");
		}else if (usuario.getTelefones().contains(telefone)){
			reportarMensagemDeErro("Telefone já foi adicionado");
		}else {
			usuario.getTelefones().add(new Telefone(telefone.getDdd(), telefone.getNumero(), TipoTelefone.toEnum(Integer.parseInt(tipo))));
			telefone = new Telefone();
		}
	}
	
	public String editar(Usuario u) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("usuario", u);
		return "cadastro?faces-redirect=true";
	}
	
	public void deletar(Usuario u) {
		try {
			usuarioService.delete(u);
			usuarios.remove(u);
			reportarMensagemDeSucesso("Removido");
		} catch (ClassNotFoundException | NamingException | SQLException e) {
			reportarMensagemDeErro(e.getMessage() + " ao remover");
		}
	}
	
	public void salvar() throws SQLException  {
		try {
			if(usuario.getId() == null) {
				int id = usuarioService.insert(usuario);
				usuarios.add(usuarioService.findById(id));
			}else {
				usuarioService.update(usuario);
			}
			usuario = new Usuario();
			reportarMensagemDeSucesso("Processo efetuado com sucesso.");
		} catch (ClassNotFoundException | NamingException e) {
			reportarMensagemDeErro(e.getMessage() + " ao cadastrar");
		}catch(JdbcSQLIntegrityConstraintViolationException x) {
			reportarMensagemDeErro(" E-mail já cadastrado");
		}

	}	
	
	public void caregarUsuarios() {
		try {
			usuarios = usuarioService.findAll();
		} catch (ClassNotFoundException | NamingException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Telefone getTelefone() {
		return telefone;
	}

	public void setTelefone(Telefone telefone) {
		this.telefone = telefone;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
