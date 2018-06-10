package br.com.caelum.jdbc;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCPool;

public class ConnectionPool {
	
	DataSource dataSource;
	
	ConnectionPool() {
		JDBCPool pool = new JDBCPool();
		pool.setUrl("jdbc:hsqldb:hsql://localhost/loja-virtual");
		pool.setUser( "SA");
		pool.setPassword("");
		this.dataSource = pool;
	}

	Connection getConection() throws SQLException {

		Connection connection =  dataSource.getConnection();
		return connection;
	}

}
