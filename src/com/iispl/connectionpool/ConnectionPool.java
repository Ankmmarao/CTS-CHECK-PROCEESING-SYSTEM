package com.iispl.connectionpool;

import java.beans.PropertyVetoException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class ConnectionPool {

	
	private static ComboPooledDataSource datasource;
	
	static {
		try {
			datasource=new ComboPooledDataSource();
			Properties properties=new Properties();
			FileInputStream inputstream=new FileInputStream("resources/db.properties");
			properties.load(inputstream);
			datasource.setDriverClass(properties.getProperty("db.driver"));
			datasource.setJdbcUrl(properties.getProperty("db.url"));
			datasource.setUser(properties.getProperty("db.username"));
			datasource.setPassword(properties.getProperty("db.password"));
			datasource.setInitialPoolSize(5);
			datasource.setMinPoolSize(5);
			datasource.setAcquireIncrement(5);
			datasource.setMaxPoolSize(20);
		}
		catch(IOException | PropertyVetoException e) {
			System.out.println(e.getMessage());
		}
	}
	public static javax.sql.DataSource getDataSource() {
		// TODO Auto-generated method stub
		return datasource;
	}
	

}