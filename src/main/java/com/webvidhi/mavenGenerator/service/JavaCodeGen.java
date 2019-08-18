package com.webvidhi.mavenGenerator.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Modifier.Keyword;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;

/*
 * Code Generator for Java, it uses JavaParser 
 */

@Service

public class JavaCodeGen {
	
	


	public String generate() {
		CompilationUnit compilationUnit = new CompilationUnit();
		ClassOrInterfaceDeclaration myClass = compilationUnit
		        .addClass("MyClass")
		        .setPublic(true);
		myClass.addField(int.class, "B_CONSTANT", Modifier.Keyword.PUBLIC, Modifier.Keyword.STATIC);
		myClass.addField(String.class, "name",  Modifier.Keyword.PRIVATE);
	//	ImportDeclaration imports = new ImportDeclaration("org.doublecloud.parserdemo", false, false);
		//imports.setName(new NameExpr("org.doublecloud.parserdemo.*"));
	//	compilationUnit.setImport(0, imports);
		return myClass.toString();
	}
	
	public static void main(String[] Args) throws IOException {
		
		JavaCodeGen generator = new JavaCodeGen();
		String code = generator.generate();
		System.out.println("Code :"+code);
		
		File file = new File("MyClass.java");
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(code);
		fileWriter.flush();
		fileWriter.close();
	}

}
