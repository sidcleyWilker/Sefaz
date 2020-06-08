package br.com.desafiosefaz.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import javax.naming.NamingException;

import br.com.desafiosefaz.dao.TelefoneDao;
import br.com.desafiosefaz.enums.TipoTelefone;
import br.com.desafiosefaz.model.Telefone;
import br.com.desafiosefaz.util.Util;

public class TelefoneService {

	private static TelefoneService instance;
	private TelefoneDao telefoneDao;
	
	private TelefoneService() {
		telefoneDao = TelefoneDao.getInstance();
	}
	
	public static TelefoneService getInstance() {
		if (instance == null) {
			instance = new TelefoneService();
		}
		return instance;
	}
	
	public int insert(Telefone telefone, int usId) {
		try {
			return telefoneDao.insert(telefone, usId);
		} catch (ClassNotFoundException | NamingException | SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public Set<Telefone> findByUsuario(int usId) throws ClassNotFoundException, NamingException, SQLException {
		ResultSet rs = telefoneDao.findByUsuario(usId);
		Set<Telefone> telefones = null;
		if (rs != null) {
			telefones = new HashSet<Telefone>();
			while(rs.next()) {
				telefones.add(resultsetToObject(rs));
			}
		}
		return telefones;
	}
	
	public Telefone findById(int id) throws ClassNotFoundException, NamingException, SQLException {
		ResultSet rs = telefoneDao.findById(id);
		if (rs != null) {
			if(rs.next()) {
				return resultsetToObject(rs);
			}
		}
		return null;
	}
	
	public Set<Telefone> findAll() throws ClassNotFoundException, NamingException, SQLException{
		ResultSet rs = telefoneDao.findAll();
		Set<Telefone> telefones = null;
		if( rs!= null) {
			telefones = new HashSet<Telefone>();
			while(rs.next()) {
				telefones.add(resultsetToObject(rs));
			}
		}
		return telefones;
	}
	
	public void update(Telefone tel) {
		try {
			telefoneDao.update(tel);
		} catch (ClassNotFoundException | NamingException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id){
		try {
			telefoneDao.delete(id);
		} catch (ClassNotFoundException | NamingException | SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	private Telefone resultsetToObject(ResultSet rs) throws SQLException {
		Telefone tel = new Telefone();
		tel.setId(rs.getInt("TEL_ID"));
		tel.setDdd(rs.getString("TEL_DDD"));
		tel.setNumero(rs.getString("TEL_NUMERO"));
		tel.setTipo(TipoTelefone.toEnum(rs.getInt("TEL_TIPO")));
		tel.setStatus(rs.getString("STATUS"));
		tel.setRegUser(rs.getString("REGUSER"));
		tel.setRegdate(Util.converteStringToDate(rs.getString("REGDATE")));
		return tel;
	}
	
	public void executeQueryTeste(String query) throws ClassNotFoundException, NamingException, SQLException {
		telefoneDao.executeQueryTeste(query);
	}
}
