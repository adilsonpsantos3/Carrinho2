package model;

import java.sql.Connection;
import java.sql.DriverManager;


public class DAO {
	
	//PARAMÊTROS DE CONEXÃO
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://10.26.45.200:3306/dbcarcompraja";
	private String user = "dba";
	private String password = "Senac@123";
	
	/**
	 * Conexão com o bando de dados
	 * @return con
	 */
	public Connection conectar() {
		// con -> Objeto
		Connection con = null;
		try {
			// uso do driver
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;		
		} catch (Exception e) {
			System.out.println(e);
			return null;

		}
		
		
	}
		
}