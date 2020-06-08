package br.com.desafiosefaz.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.NamingException;

import br.com.desafiosefaz.connectiondb.ConnectionDB;
import br.com.desafiosefaz.dao.UsuarioDao;

public class CreateDb extends ConnectionDB{

	static final String JDBC_DRIVER = "org.h2.Driver";  
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException, NamingException {
//		Class.forName(JDBC_DRIVER).newInstance(); 
//		Connection con = DriverManager.getConnection("jdbc:h2:./Database/sefaz","root","root"); 
//        System.out.println("Connected database successfully..."); 
		
		CreateDb db = new CreateDb();
//		db.executeUpdate(db.criarTabelaUsuario(), null);
//		db.executeUpdate(db.criarTabelaTelefone(), null);
		
//		new CreateDb().teste();
//		ResultSet rs = UsuarioDao.getInstance().findAll();
//		while(rs.next()) {
//			System.out.println(rs.getString("US_ID"));
//			System.out.println(rs.getString("US_NOME"));
//			System.out.println(rs.getString("US_SENHA"));
//			System.out.println(rs.getString("US_EMAIL"));
//			System.out.println(rs.getString("STATUS"));
//			System.out.println(rs.getString("REGDATE"));
//			System.out.println(rs.getString("REGUSER"));
//		}
//		
		ResultSet rs = new CreateDb().executeQuery("SELECT * FROM TELEFONE WHERE  STATUS = 'I' ");
		while(rs.next()) {
			System.out.println(rs.getString("TEL_ID"));
//			System.out.println(rs.getString("TEL_DDD"));
//			System.out.println(rs.getString("TEL_NUMERO"));
		}
		
		
//		CreateDb db = new CreateDb();
//		 db.executeUpdate("INSERT INTO USUARIO (US_NOME,US_SENHA,US_EMAIL) VALUES ('SIDCLEI','123','SID@12')", null);
//		db.executeUpdate("INSERT INTO TELEFONE (TEL_ID,TEL_DDD,TEL_NUMERO,TEL_TIPO,US_ID) VALUES (2,'123','4565',1,1)", null);
		
//		db.executeUpdate("delete USUARIO where us_id = 1", null);
		System.out.println("foi");
	}
	
	
	
	public String criarTabelaUsuario () {
		StringBuilder sb =  new StringBuilder();
		sb.append(" CREATE TABLE IF NOT EXISTS USUARIO (	");
		sb.append(" US_ID NUMBER NOT NULL AUTO_INCREMENT, 	");
		sb.append(" US_NOME VARCHAR2(50) NOT NULL , 	");
		sb.append(" US_SENHA VARCHAR2(20) NOT NULL , 	");
		sb.append(" US_EMAIL VARCHAR2(20) NOT NULL , 	");
		sb.append(" STATUS CHAR(1) DEFAULT 'A' NOT NULL , 	");
		sb.append(" REGDATE DATE DEFAULT SYSDATE NOT NULL , 	");
		sb.append(" REGUSER VARCHAR2(20) DEFAULT 'SISTEMA' NOT NULL , 	");
		sb.append(" CONSTRAINT USUARIO_PK PRIMARY KEY (US_ID),	");
		sb.append(" CONSTRAINT USUARIO_UK1 UNIQUE (US_EMAIL) ");
		sb.append(" )	");
		return sb.toString();
	}
	
	public String criarTabelaTelefone () {
		StringBuilder sb =  new StringBuilder();
		sb.append(" CREATE TABLE IF NOT EXISTS TELEFONE (	");
		sb.append(" 	TEL_ID NUMBER NOT NULL AUTO_INCREMENT , 	");
		sb.append(" 	TEL_DDD VARCHAR2(20 ) NOT NULL , 	");
		sb.append(" 	TEL_NUMERO VARCHAR2(20 ) NOT NULL , 	");
		sb.append(" 	TEL_TIPO NUMBER NOT NULL , 	");
		sb.append(" 	US_ID NUMBER NOT NULL , 	");
		sb.append(" 	STATUS CHAR(1) DEFAULT 'A' NOT NULL , 	");
		sb.append(" 	REGDATE DATE DEFAULT SYSDATE NOT NULL , 	");
		sb.append(" 	REGUSER VARCHAR2(20 BYTE) DEFAULT 'SISTEMA' NOT NULL , 	");
		sb.append(" 	CONSTRAINT TELEFONE_PK PRIMARY KEY (TEL_ID),	");
		sb.append("     CONSTRAINT TELEFONE_UK1 UNIQUE (TEL_DDD, TEL_NUMERO), ");
		sb.append(" 	 CONSTRAINT TELEFONE_FK FOREIGN KEY (US_ID)	");
		sb.append(" 	  REFERENCES USUARIO (US_ID) ON DELETE CASCADE	");
		sb.append("    )	");
		return sb.toString();
	}
}
