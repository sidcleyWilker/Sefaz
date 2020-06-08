package br.com.desafiosefaz.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.naming.NamingException;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import br.com.desafiosefaz.dao.UsuarioDao;
import br.com.desafiosefaz.model.Telefone;
import br.com.desafiosefaz.model.Usuario;
import br.com.desafiosefaz.util.Util;

public class UsuarioService {

	private static UsuarioService instance;
	private UsuarioDao usuarioDao;
	private TelefoneService telefoneService;
	
	private UsuarioService() {
		usuarioDao = UsuarioDao.getInstance();
		telefoneService = TelefoneService.getInstance();
	}
	
	public static UsuarioService getInstance() {
		if (instance == null) {
			instance = new UsuarioService();
		}
		return instance;
	}
	
	public int insert(Usuario usuario) throws ClassNotFoundException, NamingException, SQLException, JdbcSQLIntegrityConstraintViolationException {
		int id = usuarioDao.insert(usuario);
		if(usuario.getTelefones() != null && !usuario.getTelefones().isEmpty()) {
			usuario.getTelefones().forEach(tel -> {
				telefoneService.insert(tel, id);
			});
		}
		return id;
	}
	
	public Usuario findByEmail(String email) throws ClassNotFoundException, NamingException, SQLException {
		ResultSet rs = usuarioDao.findByEmail(email);
		if (rs != null) {
			if(rs.next()) {
				Usuario usu = resultsetToObject(rs);
				usu.setTelefones(telefoneService.findByUsuario(usu.getId()));
				return usu;
			}
		}
		return null;
	}
	
	public Usuario findById(int id) throws ClassNotFoundException, NamingException, SQLException {
		ResultSet rs = usuarioDao.findById(id);
		if (rs != null) {
			if(rs.next()) {
				Usuario usu = resultsetToObject(rs);
				usu.setTelefones(telefoneService.findByUsuario(usu.getId()));
				return usu;
			}
		}
		return null;
	}
	
	public List<Usuario> findAll() throws ClassNotFoundException, NamingException, SQLException{
		ResultSet rs = usuarioDao.findAll();
		List<Usuario> usuarios = null;
		if( rs!= null) {
			usuarios = new ArrayList<Usuario>();
			while(rs.next()) {
				Usuario usu = resultsetToObject(rs);
				usu.setTelefones(telefoneService.findByUsuario(usu.getId()));
				usuarios.add(usu);
			}
		}
		return usuarios;
	}
	
	public void update(Usuario usuario) throws ClassNotFoundException, NamingException, SQLException, JdbcSQLIntegrityConstraintViolationException {
		Usuario usuarioAntigo = findById(usuario.getId());
		
		usuarioDao.update(usuario);
		
		//Foi removido todos os telefones
		if( (usuario.getTelefones() == null || usuario.getTelefones().isEmpty()) && !usuarioAntigo.getTelefones().isEmpty() ) {
			deletarListaTelefone(usuarioAntigo.getTelefones());
		}
		
		//Novos telefones
		if(usuario.getTelefones() != null && !usuario.getTelefones().isEmpty()) {
			usuario.getTelefones().forEach(tel -> {
				if(tel.getId() == null) {
					telefoneService.insert(tel, usuario.getId());
				}
			});
		}
		
		//deletar 
		if((usuario.getTelefones() != null && !usuario.getTelefones().isEmpty()) && !usuarioAntigo.getTelefones().isEmpty()) {
			usuarioAntigo.getTelefones().forEach(tel -> {
				if (!usuario.getTelefones().contains(tel)) {
					telefoneService.delete(tel.getId());
				}
			});
		}

	}
	
	public void delete(Usuario usuario) throws ClassNotFoundException, NamingException, SQLException {
		usuarioDao.delete(usuario.getId());
		if(usuario.getTelefones() != null && !usuario.getTelefones().isEmpty()) {
			deletarListaTelefone(usuario.getTelefones());
		}
	}
	
	private Usuario resultsetToObject(ResultSet rs) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setId(rs.getInt("US_ID"));
		usuario.setNome(rs.getString("US_NOME"));
		usuario.setEmail(rs.getString("US_EMAIL"));
		usuario.setSenha(rs.getString("US_SENHA"));
		usuario.setStatus(rs.getString("STATUS"));
		usuario.setRegUser(rs.getString("REGUSER"));
		usuario.setRegdate(Util.converteStringToDate(rs.getString("REGDATE")));
		return usuario;
	}
	
	private void deletarListaTelefone(Set<Telefone> telefones) {
		telefones.forEach(tel -> {
			telefoneService.delete(tel.getId());
		});
	}
}
