package tass;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Configuration {
	
	private static Configuration instance;
	
	private Connection connection = null;

	private Configuration() {

	}
	
	public static Configuration getInstance() {
		if(instance == null) {
			instance = new Configuration();
		}
		
		return instance;
	}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException {
		if(connection == null) {
			connection = createConnection();
		}
		
		return connection;
	}
	
	private Connection createConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/tass","tms", "tms");
		
		return connection;
	}
}
