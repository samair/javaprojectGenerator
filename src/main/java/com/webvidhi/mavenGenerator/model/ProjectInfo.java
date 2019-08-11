package com.webvidhi.mavenGenerator.model;

public class ProjectInfo {

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getArtifactName() {
		return artifactName;
	}

	public void setArtifactName(String artifactName) {
		this.artifactName = artifactName;
	}

	public int getJavaVersion() {
		return javaVersion;
	}

	public void setJavaVersion(int javaVersion) {
		this.javaVersion = javaVersion;
	}

	public boolean isSpringBootApp() {
		return isSpringBootApp;
	}

	public void setSpringBootApp(boolean isSpringBootApp) {
		this.isSpringBootApp = isSpringBootApp;
	}

	private String groupName;
	private String artifactName;
	
	private int javaVersion;
	private boolean isSpringBootApp;
	

	

}
