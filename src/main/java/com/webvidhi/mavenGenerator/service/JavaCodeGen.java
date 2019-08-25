package com.webvidhi.mavenGenerator.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.springframework.stereotype.Service;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.webvidhi.mavenGenerator.model.ProjectInfo;

/*
 * Code Generator for Java, it uses JavaParser 
 */

@Service
   
public class JavaCodeGen {
	
	private ProjectInfo prjInfo;
	
	public String folder ;
    
	public String projfolder;
	
	public String projResFolder;
	
	public String projTestFolder;

	public String generate() throws IOException {
		
		//Check if windows or linux
		System.out.println("OS: "+System.getProperty("os.name"));
		folder = System.getProperty("user.dir");
	    StringTokenizer stringToken = new StringTokenizer(prjInfo.getGroupName(),".");
		
		String delim = "/";
		
	    if (System.getProperty("os.name").contains("Windows")) {
	    	delim ="\\";
	    }
	    	

    	projfolder = folder+delim+"Generated_"+delim+prjInfo.getArtifactName();
    	folder = folder+delim+"Generated_"+delim+prjInfo.getArtifactName()+delim+"src" +delim+"main"+delim+"java";
    	StringBuilder bld = new StringBuilder();
    	
    	while(stringToken.hasMoreTokens()) {
       		bld.append(delim);
    		bld.append(stringToken.nextToken());
    
    		
    	}
    	folder = folder + bld.toString();
    	System.out.println("folder: "+folder);
    	projResFolder = projfolder + delim+"src" +delim+"main"+delim+"resources";
	    				
		//Create temporary folders for the package
	    Path path = Paths.get(folder);
		Files.createDirectories(path);
		CompilationUnit compilationUnit = new CompilationUnit();
		compilationUnit.addImport("org.springframework.boot.SpringApplication");
		compilationUnit.setPackageDeclaration(prjInfo.getGroupName());
		
		
		ClassOrInterfaceDeclaration myClass = compilationUnit
		        .addClass(prjInfo.getArtifactName() +"Application.java")
		        .setPublic(true);
		
	    MethodDeclaration  mainMethod = myClass.addMethod("main", Modifier.Keyword.PUBLIC,Modifier.Keyword.STATIC);
		
		mainMethod.addAndGetParameter(String[].class,"args");
		mainMethod.setBlockComment("Generated code by javaCodeGen");
		
		NameExpr nameExpr = new NameExpr("SpringApplication");
		MethodCallExpr methodCallExpr = new MethodCallExpr(nameExpr, "run");
		methodCallExpr.addArgument(prjInfo.getArtifactName() +"Application"+".class");
		methodCallExpr.addArgument("args");
		
		BlockStmt blockStmt = new BlockStmt();
		blockStmt.addStatement(methodCallExpr);


		mainMethod.setBody(blockStmt);
		
	    // Create properties file
	    Path resPath = Paths.get(projResFolder);
	    
		Files.createDirectories(resPath);
		File file = null;
		File pom = null;
		 if (System.getProperty("os.name").contains("Windows")) {
			 file = new File(resPath+"\\application.properties");
			 pom = new File(projfolder+"\\pom.xml");
		 }
		 else {
			 file = new File(resPath+"/application.properties");
			 pom = new File(projfolder+"/pom.xml");
			 
		 }
		FileWriter appProp = new FileWriter(file);		
		appProp.write("");
		appProp.flush();
		appProp.close();
		
		FileWriter appPom = new FileWriter(pom);		
		appPom.write("");
		appPom.flush();
		appPom.close();
		
		
		return compilationUnit.toString();
		
	}
	public void setProjectInfo(ProjectInfo info) {
		
		this.prjInfo = info;
	}
	
	public String createProjectZip() throws Exception {
		
		//first generate project 
		
		
		String zipFileName = prjInfo.getArtifactName()+".zip";
		

		FileOutputStream fos = new FileOutputStream(zipFileName);
		ZipOutputStream zos = new ZipOutputStream(fos);
		addDirToZipArchive(zos, new File(projfolder), null);
		
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
	public static void main(String[] Args) throws Exception {
		
		JavaCodeGen generator = new JavaCodeGen();
		String code = generator.generate();
		System.out.println("Code :"+code);
		
		File file = new File(generator.folder+"\\MyClass.java");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(code);
		fileWriter.flush();
		fileWriter.close();
		generator.createProjectZip();
	}

}
