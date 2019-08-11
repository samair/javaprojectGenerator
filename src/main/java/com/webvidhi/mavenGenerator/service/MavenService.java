package com.webvidhi.mavenGenerator.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.springframework.stereotype.Service;

import com.webvidhi.mavenGenerator.model.ProjectInfo;

@Service
public class MavenService {
	
	private ProjectInfo prjInfo;
	
	public String generateProject() throws MavenInvocationException, IOException {
		
		

        InvocationRequest request = new DefaultInvocationRequest();
        request.setGoals( Collections.singletonList("archetype:generate") );
        request.setShowErrors(true);
        Properties properties = new Properties();
        properties.setProperty("groupId", this.prjInfo.getGroupName());
        properties.setProperty("artifactId", this.prjInfo.getArtifactName());
        properties.setProperty("archetypeVersion", "1.1");
        properties.setProperty("archetypeGroupId", "org.apache.maven.archetypes");
        properties.setProperty("archetypeArtifactId", "maven-archetype-quickstart");
        properties.setProperty("interactiveMode", "false");
        properties.setProperty("package", this.prjInfo.getGroupName());
        request.setProperties(properties);
        Invoker invoker = new DefaultInvoker();
        invoker.setMavenHome(new File("/usr/local/"));
        invoker.setWorkingDirectory(new File("/Users/samair/"));
        InvocationResult result = invoker.execute( request );
        
        return this.prjInfo.getArtifactName();
	}
	
	public String createProjectZip() throws Exception {
		
		//first generate project 
		
		
		String zipFileName = generateProject()+".zip";
		
		FileOutputStream fos = new FileOutputStream(zipFileName);
		ZipOutputStream zos = new ZipOutputStream(fos);
		addDirToZipArchive(zos, new File("/Users/samair/"+this.prjInfo.getArtifactName()), null);
		
	    zos.flush();
	    fos.flush();
	    zos.close();
	    fos.close();
	    
		return zipFileName;
		
	}


	public  void addDirToZipArchive(ZipOutputStream zos, File fileToZip, String parrentDirectoryName) throws Exception {
	    if (fileToZip == null || !fileToZip.exists()) {
	        return;
	    }

	    String zipEntryName = fileToZip.getName();
	    if (parrentDirectoryName!=null && !parrentDirectoryName.isEmpty()) {
	        zipEntryName = parrentDirectoryName + "/" + fileToZip.getName();
	    }

	    if (fileToZip.isDirectory()) {
	        System.out.println("+" + zipEntryName);
	        for (File file : fileToZip.listFiles()) {
	            addDirToZipArchive(zos, file, zipEntryName);
	        }
	    } else {
	        System.out.println("   " + zipEntryName);
	        byte[] buffer = new byte[1024];
	        FileInputStream fis = new FileInputStream(fileToZip);
	        zos.putNextEntry(new ZipEntry(zipEntryName));
	        int length;
	        while ((length = fis.read(buffer)) > 0) {
	            zos.write(buffer, 0, length);
	        }
	        zos.closeEntry();
	        fis.close();
	    }
	}
	public void setProjectInfo(ProjectInfo info) {
	
		this.prjInfo = info;
	}

	public static void main(String[] args) throws Exception {
		
		MavenService msvc = new MavenService();
		FileOutputStream fos = new FileOutputStream("/Users/samair/maven.zip");
		ZipOutputStream zos = new ZipOutputStream(fos);
		msvc.addDirToZipArchive(zos, new File("/Users/samair/maven"), null);
	    zos.flush();
	    fos.flush();
	    zos.close();
	    fos.close();
	
	}
}
