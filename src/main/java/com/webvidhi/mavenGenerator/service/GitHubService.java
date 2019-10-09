package com.webvidhi.mavenGenerator.service;

import java.io.IOException;
import java.text.MessageFormat;

import org.eclipse.egit.github.core.Repository;
import org.eclipse.egit.github.core.RepositoryContents;
import org.eclipse.egit.github.core.client.GitHubClient;
import org.eclipse.egit.github.core.service.RepositoryService;

public class GitHubService {


	/**
	 * Prints a user's repositories
	 *
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final String user = "mohitdd";
		final String format = "{0}) {1}- created on {2}";
		int count = 1;
		RepositoryService service = new RepositoryService();
		for (Repository repo : service.getRepositories(user))
			System.out.println(MessageFormat.format(format, count++,
					repo.getName(), repo.getCreatedAt()));
		
		
		//login
	/*
		service.getClient().setCredentials("tsameerc@gmail.com", "Eteghaij1@");
		Repository r = new Repository();
		r.setName("API_TEST");
		service.createRepository(r);
		RepositoryContents content = new RepositoryContents();
	*/
		
	}
}