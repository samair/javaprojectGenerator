package com.webvidhi.mavenGenerator.controller;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.maven.shared.invoker.MavenInvocationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.webvidhi.mavenGenerator.model.ProjectInfo;
import com.webvidhi.mavenGenerator.service.JavaCodeGen;
import com.webvidhi.mavenGenerator.service.MavenService;
@CrossOrigin
@RestController
@RequestMapping("/generate/")
public class MavenGeneratorController {
	
	@Autowired
	MavenService mvnService;
	
	@Autowired
	JavaCodeGen genService;
	
	@GetMapping("/test")
	public ResponseEntity<Resource> createFile() throws Exception {
		String code = genService.generate();
		File file = new File(genService.folder+"/MyClass.java");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(code);
		fileWriter.flush();
		fileWriter.close();
		genService.createProjectZip();
		String contentHeader = "attachment; filename=" + "MyClass.zip";
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, contentHeader);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path path = Paths.get(file.getAbsolutePath());
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

		return ResponseEntity.ok().headers(header).contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream")).body(resource);
	}
	
	@PostMapping("/setInfo")
    public void updateProjectInfo(@RequestBody ProjectInfo pInfo) {

		mvnService.setProjectInfo(pInfo);
		
	}
	@GetMapping("/maven")
	public ResponseEntity<Resource> generatMavenProject() throws Exception {
		
		//create maven project based on the request 
		
		String projectZipName = mvnService.createProjectZip(); 
		
		File file = new File(projectZipName);
		String filename=projectZipName;
		String contentHeader = "attachment; filename="+filename;

        HttpHeaders header = new HttpHeaders();
        header.add(HttpHeaders.CONTENT_DISPOSITION, contentHeader);
        header.add("Cache-Control", "no-cache, no-store, must-revalidate");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");

        Path path = Paths.get(file.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return ResponseEntity.ok()
                .headers(header)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(resource);
	}
}
