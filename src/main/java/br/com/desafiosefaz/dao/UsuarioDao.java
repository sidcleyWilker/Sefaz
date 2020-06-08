package br.com.desafiosefaz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.naming.NamingException;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;

import br.com.desafiosefaz.connectiondb.ConnectionDB;
import br.com.desafiosefaz.connectiondb.ParamDAO;
import br.com.desafiosefaz.model.Usuario;

public class UsuarioDao extends ConnectionDB{

	private StringBuilder sb;
	private static UsuarioDao instance;
	
	private UsuarioDao () {
		sb = new StringBuilder();
	}
	
	public static UsuarioDao getInstance() {
		if (instance == null) {
			instance = new UsuarioDao();
		}
		return instance;
	}
	
	public ResultSet findById(int id) throws ClassNotFoundException, NamingException, SQLException {
		cleanStringBuilder();
		sb.append(" SELECT * FROM USUARIO WHERE US_ID = ? AND STATUS = 'A'");
		ParamDAO[] params = new ParamDAO[1];
		params[0] = new ParamDAO(id, Types.INTEGER);
		return super.executeQuery(sb.toString(), params);
	}
	
	public ResultSet findByEmail(String email) throws ClassNotFoundException, NamingException, SQLException {
		cleanStringBuilder();
		sb.append(" SELECT * FROM USUARIO WHERE US_EMAIL = ? AND STATUS = 'A' ");
		ParamDAO[] params = new ParamDAO[1];
		params[0] = new ParamDAO(email, Types.VARCHAR);
		return super.executeQuery(sb.toString(), params);
	}
	
	public ResultSet findAll() throws ClassNotFoundException, NamingException, SQLException {
		cleanStringBuilder();
		sb.append(" SELECT * FROM USUARIO WHERE STATUS = 'A' ");
		return super.executeQuery(sb.toString());
	}
	
	public void update(Usuario usuario) throws ClassNotFoundException, NamingException, SQLException, JdbcSQLIntegrityConstraintViolationException {
		cleanStringBuilder();
		sb.append(" UPDATE USUARIO SET US_NOME = ?, US_SENHA = ?, US_EMAIL = ?, REGDATE = SYSDATE WHERE US_ID = ?");
		ParamDAO[] params = new ParamDAO[4];
		params[0] = new ParamDAO(usuario.getNome(), Types.VARCHAR);
		params[1] = new ParamDAO(usuario.getSenha(), Types.VARCHAR);
		params[2] = new ParamDAO(usuario.getEmail(), Types.VARCHAR);
		params[3] = new ParamDAO(usuario.getId(), Types.INTEGER);
		super.executeUpdate(sb.toString(), params);
	}
	
	public void delete(int id) throws ClassNotFoundException, NamingException, SQLException {
		cleanStringBuilder();
		sb.append(" UPDATE USUARIO SET STATUS = 'I' WHERE US_ID = ? ");
		ParamDAO[] params = new ParamDAO[1];
		params[0] = new ParamDAO(id, Types.INTEGER);
		super.executeUpdate(sb.toString(), params);
	}
	
	public int insert(Usuario usuario) throws ClassNotFoundException, NamingException, SQLException, JdbcSQLIntegrityConstraintViolationException {
		cleanStringBuilder();
		sb.append(" INSERT INTO USUARIO (US_NOME,US_SENHA,US_EMAIL) VALUES (?,?,?) ");
		ParamDAO[] params = new ParamDAO[3];
		params[0] = new ParamDAO(usuario.getNome(), Types.VARCHAR);
		params[1] = new ParamDAO(usuario.getSenha(), Types.VARCHAR);
		params[2] = new ParamDAO(usuario.getEmail(), Types.VARCHAR);
		return super.executeUpdate(sb.toString(), params, new String[] {"US_ID"});
	}
	
	private void cleanStringBuilder() {
		sb.delete(0, sb.length());
	}
}
