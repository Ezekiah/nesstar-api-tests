package com.nesstar.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;
import com.nesstar.api.NesstarDB;
import com.nesstar.api.NesstarDBFactory;
import com.nesstar.api.Server;


public class NesstarServer {
	protected NesstarDB nesstarDB;
	protected URI serverURI;
	protected Server server;
	protected String username;
	protected String password;
	
	
	
	public NesstarServer (URI serverURI) throws IOException, URISyntaxException {
		loadParams();
		this.nesstarDB = NesstarDBFactory.getInstance();
		this.server = nesstarDB.getServer(serverURI);
		this.server.login(username, password);

	}
	
	public void loadParams() throws URISyntaxException {
	    Properties props = new Properties();
	    InputStream is = null;
	 
	    // First try loading from the current directory
	    try {
	        File f = new File("server.properties");
	        is = new FileInputStream( f );
	    }
	    catch ( Exception e ) { is = null; }
	 
	    try {
	        if ( is == null ) {
	            // Try loading from classpath
	            is = getClass().getResourceAsStream("server.properties");
	        }
	 
	        // Try loading properties from the file (if found)
	        props.load( is );
	    }
	    catch ( Exception e ) { }
	 
	    serverURI = new URI(props.getProperty("ServerAddress"));
	    username = new String(props.getProperty("username"));
	    password  = new String(props.getProperty("password"));
	}
	

}
