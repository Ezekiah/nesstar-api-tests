package com.nesstar.demo;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import com.nesstar.api.NesstarDB;
import com.nesstar.api.NesstarDBFactory;
import com.nesstar.api.NesstarList;
import com.nesstar.api.NotAuthorizedException;
import com.nesstar.api.Server;
import com.nesstar.api.Study;
import com.nesstar.api.publishing.CSVFileDataSource;
import com.nesstar.api.publishing.DDIparsingException;
import com.nesstar.api.publishing.InconsistentDDIException;
import com.nesstar.api.publishing.MalformedDDIexception;
import com.nesstar.api.publishing.PublishingException;
import com.nesstar.api.publishing.StudyPublishingBuilder;

public class NesstarServer {
	protected NesstarDB nesstarDB;
	protected URI serverURI;
	protected Server server;
	
	
	public NesstarServer (URI serverURI) throws IOException {
		this.serverURI = serverURI;
		this.nesstarDB = NesstarDBFactory.getInstance();
		this.server = nesstarDB.getServer(serverURI);
		this.server.login("admin", "");
	}
	

}
