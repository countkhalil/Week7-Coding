package projects.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import projects.dao.ProjectDao;
import projects.entity.Project;
import projects.exception.DbException;

//Code By Khalil M. 

@SuppressWarnings("unused")
public class ProjectService {

	private ProjectDao projectDao = new ProjectDao();
	
	
	
	
	public Project addProject(Project project) {
	 return projectDao.insertProject(project);
		
		
	}




	public List<Project> fetchAllProjects() {
	return	projectDao.fetchAllProjects();
	}




	public  Project fetchByProjectId(Integer projectId) {
		
	
		return  projectDao.fetchByProjectId(projectId).orElseThrow(() -> new NoSuchElementException("Project ID " + projectId + " Does Not Currently Belong to a Project...."))  ;
	}




	public void modifyProjectDetails(Project project) {
		if(!projectDao.modifyProjectDetails(project)) {
			throw new DbException("A project with the ID " + project.getProjectId() + " does not currently exist");		}
		
	}




	public void deleteProject(Integer projectId) {
		if (!projectDao.deleteProject(projectId)) {
			throw new DbException(projectId + " is not a valid target....");
		}
		
	}

}
