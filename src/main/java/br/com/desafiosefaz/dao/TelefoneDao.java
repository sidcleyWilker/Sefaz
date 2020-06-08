package br.com.desafiosefaz.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.naming.NamingException;

import br.com.desafiosefaz.connectiondb.ConnectionDB;
import br.com.desafiosefaz.connectiondb.ParamDAO;
import br.com.desafiosefaz.model.Telefone;

public class TelefoneDao extends ConnectionDB{

	private StringBuilder sb;
	private static TelefoneDao instance;
	
	private TelefoneDao () {
		sb = new StringBuilder();
	}
	
	public static TelefoneDao getInstance() {
		if (instance == null) {
			instance = new TelefoneDao();
		}
		return instance;
	}
	
	public ResultSet findById(int id) throws ClassNotFoundException, NamingException, SQLException {
		cleanStringBuilder();
		sb.append(" SELECT * FROM TELEFONE WHERE TEL_ID = ? AND STATUS = 'A'");
		ParamDAO[] params = new ParamDAO[1];
		params[0] = new ParamDAO(id, Types.INTEGER);
		return super.executeQuery(sb.toString(), params);
	}
	
	public ResultSet findByUsuario(int  usId) throws ClassNotFoundException, NamingException, SQLException {
		cleanStringBuilder();
		sb.append(" SELECT * FROM TELEFONE WHERE US_ID = ? AND STATUS = 'A' ");
		ParamDAO[] params = new ParamDAO[1];
		params[0] = new ParamDAO(usId, Types.INTEGER);
		return super.executeQuery(sb.toString(), params);
	}
	
	public ResultSet findAll() throws ClassNotFoundException, NamingException, SQLException {
		cleanStringBuilder();
		sb.append(" SELECT * FROM TELEFONE WHERE STATUS = 'A' ");
		return super.executeQuery(sb.toString());
	}
	
	public void update(Telefone tel) throws ClassNotFoundException, NamingException, SQLException {
		cleanStringBuilder();
		sb.append(" UPDATE TELEFONE SET TEL_DDD = ?, TEL_NUMERO = ?, TEL_TIPO = ?, REGDATE = SYSDATE WHERE TEL_ID = ?");
		ParamDAO[] params = new ParamDAO[4];
		params[0] = new ParamDAO(tel.getDdd(), Types.VARCHAR);
		params[1] = new ParamDAO(tel.getNumero(), Types.VARCHAR);
		params[2] = new ParamDAO(tel.getTipo().getCod(), Types.INTEGER);
		params[3] = new ParamDAO(tel.getId(), Types.INTEGER);
		super.executeUpdate(sb.toString(), params);
	}
	
	public void delete(int id) throws ClassNotFoundException, NamingException, SQLException {
		cleanStringBuilder();
		sb.append(" UPDATE TELEFONE SET STATUS = 'I' WHERE TEL_ID = ? ");
		ParamDAO[] params = new ParamDAO[1];
		params[0] = new ParamDAO(id, Types.INTEGER);
		super.executeUpdate(sb.toString(), params);
	}
	
	public int insert(Telefone tel, int uId) throws ClassNotFoundException, NamingException, SQLException {
		cleanStringBuilder();
		sb.append(" INSERT INTO TELEFONE (TEL_DDD,TEL_NUMERO,TEL_TIPO,US_ID) VALUES (?,?,?,?) ");
		ParamDAO[] params = new ParamDAO[4];
		params[0] = new ParamDAO(tel.getDdd(), Types.VARCHAR);
		params[1] = new ParamDAO(tel.getNumero(), Types.VARCHAR);
		params[2] = new ParamDAO(tel.getTipo().getCod(), Types.INTEGER);
		params[3] = new ParamDAO(uId, Types.INTEGER);
		return super.executeUpdate(sb.toString(), params, new String[] {"TEL_ID"});
	}
	
	public void executeQueryTeste(String query) throws ClassNotFoundException, NamingException, SQLException {
		 super.executeUpdate(query, null);
	}
	
	private void cleanStringBuilder() {
		sb.delete(0, sb.length());
	}
}
