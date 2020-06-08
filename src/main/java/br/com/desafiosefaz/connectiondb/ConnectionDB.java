package br.com.desafiosefaz.connectiondb;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.sql.Types;

import javax.naming.NamingException;
import javax.sql.rowset.CachedRowSet;

import com.sun.rowset.CachedRowSetImpl;

public abstract class ConnectionDB {
	
	private String server = "jdbc:h2:./Database/sefaz";
	private String usuario = "root";
	private String senha = "root";
	
	private Connection conexao;

	protected  Connection getConexao() throws ClassNotFoundException, SQLException {
		if ( conexao == null || conexao.isClosed()) {
			try {
				Class.forName("org.h2.Driver");
				conexao = DriverManager.getConnection(server,usuario,senha);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return conexao;
	}
	
	public boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
		ResultSetMetaData rsmd = rs.getMetaData();
		int columns = rsmd.getColumnCount();
		for (int x = 1; x <= columns; x++) {
			if (columnName.equals(rsmd.getColumnName(x))) {
				return true;
			}
		}
		return false;
	}

	public CachedRowSet executeQuery(String strStatment)
			throws NamingException, SQLException, ClassNotFoundException {
		return executeQuery(strStatment, null);
	}
	
	public CachedRowSet executeQuery(String strStatment, ParamDAO[] params)
			throws NamingException, SQLException, ClassNotFoundException {

		CachedRowSet crs = null;
		PreparedStatement psmt = null;
		Connection conn = getConexao();
		ResultSet rs = null;
		try {
			psmt = conn.prepareStatement(strStatment);
			setStatementParams(params, psmt);
			rs = psmt.executeQuery();
			crs = new CachedRowSetImpl();
			crs.populate(rs);
		}catch (SQLException e) {
			throw e;
		} finally {
			rs.close();
			psmt.close();
			conn.close();
		}

		return crs;
	}
	
	
	public int executeUpdate(String strStatment, ParamDAO[] params)
			throws NamingException, SQLException, ClassNotFoundException {
		int linhasAfetadas = 0;
		Connection conn = getConexao();
		PreparedStatement psmt  = null;
		try {
			psmt = conn.prepareStatement(strStatment);
			setStatementParams(params, psmt);
			linhasAfetadas = psmt.executeUpdate();

		}catch (Exception e) {
			throw e;
		} finally {
			psmt.close();
			conn.close();
		}
		return linhasAfetadas;
	}
	
	
	public int executeUpdate(String strStatment, ParamDAO[] params, String[] indices) throws ClassNotFoundException, SQLException {
		int linhasAfetadas = 0;

		Connection conn = getConexao();
		PreparedStatement psmt  = null;
		try {
			psmt = conn.prepareStatement(strStatment, indices);
			setStatementParams(params, psmt);
			linhasAfetadas = psmt.executeUpdate();
			ResultSet generatedKeys = psmt.getGeneratedKeys();
			if (generatedKeys != null && generatedKeys.next()) {
				linhasAfetadas = generatedKeys.getInt(1);
			}
					
		}catch (Exception e) {
			throw e;
		} finally {
			psmt.close();
			conn.close();
		}
		return linhasAfetadas;
	}
	
	
	private void setStatementParams(ParamDAO[] params, PreparedStatement psmt)
			throws SQLException {
		if (params != null) {

			for (int i = 0; i < params.length; i++) {
				ParamDAO p = params[i];
				if ((p != null) && p.isSaida()) {
					((CallableStatement) psmt).registerOutParameter(i + 1,
							p.getTipo());
				} else {
					setInParamValue(psmt, i + 1, p);
				}
			}

		}
	}
	
	private void setInParamValue(PreparedStatement psmt, int parameterIndex,
			ParamDAO p) throws SQLException {

		if (p.getValor() == null) {
			psmt.setNull(parameterIndex, p.getTipo());
		} else {

			switch (p.getTipo()) {
			case Types.INTEGER:
				psmt.setInt(parameterIndex, (Integer) p.getValor());
				break;
			case Types.SMALLINT:
			case Types.TINYINT:
				psmt.setShort(parameterIndex, (Short) p.getValor());
				break;
			case Types.BIGINT:
			case Types.NUMERIC:
				psmt.setLong(parameterIndex, (Long) p.getValor());
				break;
			case Types.FLOAT:
				psmt.setFloat(parameterIndex, (Float) p.getValor());
				break;
			case Types.DOUBLE:
			case Types.REAL:
			case Types.DECIMAL:
				psmt.setDouble(parameterIndex, (Double) p.getValor());
				break;
			case Types.VARCHAR:
			case Types.CHAR:
				if (p.getValor() instanceof Character) {
					psmt.setString(parameterIndex,
							((Character) p.getValor()).toString());
				} else {
					psmt.setString(parameterIndex, (String) p.getValor());
				}
				break;
			case Types.DATE:
				if (p.getValor() instanceof java.util.Date) {
					psmt.setDate(parameterIndex,
							new Date(((java.util.Date) p.getValor()).getTime()));
				} else {
					psmt.setDate(parameterIndex, (Date) p.getValor());
				}
				break;
			case Types.TIME:
				psmt.setTime(parameterIndex, (Time) p.getValor());
				break;
			case Types.TIMESTAMP:
				if (p.getValor() instanceof java.util.Date) {
					psmt.setTimestamp(parameterIndex, new Timestamp(
							((java.util.Date) p.getValor()).getTime()));
				} else {
					psmt.setTimestamp(parameterIndex, (Timestamp) p.getValor());
				}
				break;
			case Types.ARRAY:
			case Types.BINARY:
			case Types.BLOB:
			case Types.CLOB:
			case Types.LONGVARBINARY:
			case Types.LONGVARCHAR:
			case Types.VARBINARY:

				byte[] bytesValor;
				if (p.getValor() instanceof String) {
					try {
						bytesValor = ((String) p.getValor()).getBytes("UTF-8");
					} catch (UnsupportedEncodingException e) {
						throw new SQLException(e.getMessage());
					}
				} else {
					bytesValor = (byte[]) p.getValor();
				}
				ByteArrayInputStream inStream = new ByteArrayInputStream(
						bytesValor);
				psmt.setBinaryStream(parameterIndex, inStream,
						bytesValor.length);
				break;
			case Types.BIT:
			case Types.BOOLEAN:
				psmt.setBoolean(parameterIndex, (Boolean) p.getValor());
				break;
			}
		}
	}
	
}
