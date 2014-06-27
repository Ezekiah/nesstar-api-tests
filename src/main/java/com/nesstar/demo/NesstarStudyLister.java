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

public final class NesstarStudyLister extends NesstarServer{
	

	public NesstarStudyLister() throws IOException, URISyntaxException {
		super(new URI("http://127.0.0.1:8085"));
	}

	public String getListText() throws NotAuthorizedException, IOException {
		NesstarList<Study> allStudies = server.getBank(Study.class).getAll();
		StringBuilder studyLabelList = new StringBuilder();
		for (Study study : allStudies) {
			studyLabelList.append(study.getLabel());
			studyLabelList.append(System.getProperty("line.separator"));
		}
		return studyLabelList.toString();
	}

	public static void main(String[] args) throws URISyntaxException, NotAuthorizedException {
		NesstarStudyLister nesstarDemoServerStudyLister;

		try {
			nesstarDemoServerStudyLister = new NesstarStudyLister();
			String studyListText = nesstarDemoServerStudyLister.getListText();
			System.out.println(studyListText);


		} catch (IOException ioe) {
			System.err.println("Error: " + ioe);
		}
	}


	

}
