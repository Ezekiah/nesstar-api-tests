package com.nesstar.demo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import org.apache.commons.io.IOUtils;

import com.nesstar.api.NesstarDB;
import com.nesstar.api.NesstarDBFactory;
import com.nesstar.api.Server;


public class NesstarServer {
	protected NesstarDB nesstarDB;
	protected URI serverURI;
	protected Server server;
	protected String username;
	protected String password;
	protected Properties props = new Properties();



	public NesstarServer () throws IOException, URISyntaxException {
		loadParams();
		
		this.nesstarDB = NesstarDBFactory.getInstance();
		this.server = nesstarDB.getServer(serverURI);
		
		this.server.login(username, password);

	}

	public void loadParams() throws URISyntaxException {
		
		try {
			InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("server.properties"); 
			
			props.load(stream);
			
			try {
			    IOUtils.copy(stream, System.out);
			} finally {
			    stream.close();
			}
			

	      } 
	      catch(Exception e) {
	         System.out.println(e);
	         System.out.println(props.propertyNames());
	         
	         
	      }
		finally{
			serverURI = new URI(props.getProperty("ServerAddress"));
			username = new String(props.getProperty("username"));
			password  = new String(props.getProperty("password"));
			
		}
	      
		
		
		
			
		
		
		
		
		
		
	}


}
