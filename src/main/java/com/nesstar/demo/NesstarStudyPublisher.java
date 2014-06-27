package com.nesstar.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;

import com.nesstar.api.NotAuthorizedException;
import com.nesstar.api.Study;
import com.nesstar.api.publishing.DDIparsingException;
import com.nesstar.api.publishing.InconsistentDDIException;
import com.nesstar.api.publishing.MalformedDDIexception;
import com.nesstar.api.publishing.PublishingException;
import com.nesstar.api.publishing.StudyPublishingBuilder;

public class NesstarStudyPublisher extends NesstarServer{
	

	public NesstarStudyPublisher() throws IOException, URISyntaxException {
		super(new URI("http://127.0.0.1:8085"));
	}

	

	public static void main(String[] args) throws URISyntaxException, NotAuthorizedException {
		NesstarStudyPublisher nesstarDemoServerStudyPublisher;

		try {
			nesstarDemoServerStudyPublisher = new NesstarStudyPublisher();
			
			nesstarDemoServerStudyPublisher.testNesstar4Publisher();

		} catch (IOException|PublishingException ioe) {
			System.err.println("Error: " + ioe);
		}
	}


	public void testNesstar4Publisher() throws IOException, PublishingException, NotAuthorizedException, URISyntaxException, DDIparsingException, InconsistentDDIException, MalformedDDIexception {

		try {
			StudyPublishingBuilder builder = new StudyPublishingBuilder();
			InputStream ddiFileEN = Thread.currentThread().getContextClassLoader().getResourceAsStream("test.xml");
			//InputStream ddiFileNO = Thread.currentThread().getContextClassLoader().getResourceAsStream("com/nesstar/api/impl/test-gor/DDINO.xml");
			//File csvFile = new File(Thread.currentThread().getContextClassLoader().getResource("com/nesstar/api/impl/test-gor/test!gor_F1.csv").toURI());

			//builder.addDDI(ddiFileNO);
			builder.addDDI(ddiFileEN);
			//builder.addData(new CSVFileDataSource(csvFile, "UTF-8"), "test_gor");

			//builder.setDefaultLanguage(new Locale("en"));



			Study study = server.publishStudy(builder);
		}catch (IOException|PublishingException ioe) {
			System.err.println("Error: " + ioe.getMessage());
			ioe.printStackTrace();
		}
		//server.deleteStudy("test!gor");
		//nesstarDB.clear();

	}

}
