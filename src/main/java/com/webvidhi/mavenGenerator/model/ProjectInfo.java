package com.webvidhi.mavenGenerator.model;

import java.util.ArrayList;
import java.util.List;

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

	public boolean getIsSpringBootApp() {
		return isSpringBootApp;
	}

	public void setIsSpringBootApp(boolean isSpringBootApp) {
		this.isSpringBootApp = isSpringBootApp;
	}

	public Endpoints getEndpoints() {
		return endpoints;
	}

	public void setEndpoints(Endpoints endpoints) {
		this.endpoints = endpoints;
	}

	private String groupName;
	private String artifactName;
	
	private int javaVersion;
	private boolean isSpringBootApp;
	
	public enum HTTP_METHOD{
		GET,
		POST,
		PUT,
		DELETE
	}
	public class Mapping {
		
		private HTTP_METHOD method;
		private String methodName;
		private String path;
		public HTTP_METHOD getMethod() {
			return method;
		}
		public void setMethod(HTTP_METHOD method) {
			this.method = method;
		}
		public String getMethodName() {
			return methodName;
		}
		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}
		public String getPath() {
			return path;
		}
		public void setPath(String path) {
			this.path = path;
		}
	}
	public class Endpoint {
		private List<Mapping> mappings = new ArrayList<Mapping>();
		
		private String name;
		
		public List<Mapping> getMappings() {
			return mappings;
		}

		public void setMappings(List<Mapping> mappings) {
			this.mappings = mappings;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void addMapping(Mapping m) {
			this.mappings.add(m);
			
		}
	}
	public class Endpoints{
		
		private List<Endpoint> endpointList = new ArrayList<Endpoint>();

		public List<Endpoint> getEndpointList() {
			return endpointList;
		}

		public void setEndpointList(List<Endpoint> endpointList) {
			this.endpointList = endpointList;
		}

		public void addEndpoint(Endpoint ep) {
			this.endpointList.add(ep);
			
		}
		
	}
	
    private Endpoints endpoints;
	

}
