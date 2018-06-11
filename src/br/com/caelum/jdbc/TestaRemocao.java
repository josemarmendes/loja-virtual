package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaRemocao {
	public static void main(String[] args) throws SQLException {
		ConnectionPool database = new ConnectionPool();
		
		Connection connection = database.getConnection();
		Statement statement = connection.createStatement();
		
		statement.execute("delete from Produto where id > 3");
		
		int count = statement.getUpdateCount();
		
		System.out.println(count + " linhas atualizadas");
		
		connection.close();
		statement.close();
		
	}
}
