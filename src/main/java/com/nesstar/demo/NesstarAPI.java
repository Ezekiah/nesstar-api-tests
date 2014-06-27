package com.nesstar.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.lf5.util.StreamUtils;

import com.nesstar.api.NesstarDB;
import com.nesstar.api.NesstarList;
import com.nesstar.api.NotAuthorizedException;
import com.nesstar.api.Study;
import com.nesstar.api.publishing.DDIparsingException;
import com.nesstar.api.publishing.InconsistentDDIException;
import com.nesstar.api.publishing.MalformedDDIexception;
import com.nesstar.api.publishing.PublishingException;
import com.nesstar.api.publishing.StudyPublishingBuilder;

public final class NesstarAPI extends NesstarServer{

	protected NesstarList<Study> allStudies;

	public NesstarAPI() throws IOException, URISyntaxException, NotAuthorizedException {
		super(new URI("http://127.0.0.1:8085"));
		allStudies = server.getBank(Study.class).getAll();
	}

	public String getListText() throws NotAuthorizedException, IOException {
		NesstarList<Study> allStudies = server.getBank(Study.class).getAll();
		StringBuilder studyLabelList = new StringBuilder();
		for (Study study : allStudies) {
			studyLabelList.append(study.getLabel());
			studyLabelList.append(System.getProperty("line.separator"));
			studyLabelList.append(study.getId());
			studyLabelList.append(System.getProperty("line.separator"));
		}
		return studyLabelList.toString();
	}

	public static void main(String[] args) throws URISyntaxException, NotAuthorizedException, InconsistentDDIException, MalformedDDIexception, DDIparsingException, PublishingException {
		NesstarAPI nesstarAPI;
		
		
		try {
			nesstarAPI = new NesstarAPI();
			

			if(args[0].equals("publishStudy")){
				nesstarAPI.publishStudy(args[1]);
				System.out.println("Launching... "+args[0]);
			}
			
			if(args[0].equals("listStudies")){
				String studyListText = nesstarAPI.getListText();
				System.out.println("Launching... "+args[0]);
			}
			
			
			
			//nesstarAPI.getAllStudyDDI();

			//nesstarAPI.publishStudy("test.xml");
			//nesstarAPI.deleteStudy("fr.cdsp.ddi.PEF2007V1P4");


		} catch (IOException ioe) {
			System.err.println("Error: " + ioe);
		}
	}


	public void publishStudy(String DDIresourceName) throws IOException, PublishingException, NotAuthorizedException, URISyntaxException, DDIparsingException, InconsistentDDIException, MalformedDDIexception {

		try {
			StudyPublishingBuilder builder = new StudyPublishingBuilder();
			InputStream ddiFileEN = Thread.currentThread().getContextClassLoader().getResourceAsStream(DDIresourceName);
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


	public void getAllStudyDDI() throws IOException, NotAuthorizedException {
		for (Study study : allStudies) {
			study.getDDI();
			File file = new File("/home/ala/DDIs/"+study.getId()+".xml");
			String content = new String(StreamUtils.getBytes(study.getDDI()));

			try (FileOutputStream fop = new FileOutputStream(file)) {

				// if file doesn't exists, then create it
				if (!file.exists()) {
					file.createNewFile();
				}

				// get the content in bytes
				byte[] contentInBytes = content.getBytes();

				fop.write(contentInBytes);
				fop.flush();
				fop.close();

				System.out.println("Done");

			} catch (IOException e) {
				e.printStackTrace();
			}

		}


	}

	public void deleteStudy(String id) {
		try {
			server.deleteStudy(id);
		} catch (NotAuthorizedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


}
