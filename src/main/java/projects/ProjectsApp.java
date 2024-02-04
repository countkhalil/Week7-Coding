package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

//Code By Khalil M. 

public class ProjectsApp {

	//@formatter:off
	private List<String> operations = List.of (
			"1) Add a project" ,
			"2) List of projects",
			"3) Select a Project"
			);
	//@formatter:on
	
	private Scanner scanner = new Scanner (System.in);
	private ProjectService projectService = new ProjectService();
	private Project curProject;
	
	
	
	public static void main(String[] args) {
	new ProjectsApp().processUserSelections();
	}







	private void processUserSelections() {
		boolean done = false;
		
		while (!done) {
			try {
				int selection = getUserSelection();
				
				switch (selection) {
				case -1: 
					done = exitMenu();
					break;
				case 1:
					createProject();
					break;
				case 2:
					listProjects();
					break;
				case 3:
					selectProject();
					break;
					
				default:
					System.out.println("\n" + selection + " is not a valid entry. Try again, buddy");
					break;
				}
				
			}catch (Exception e){
				System.out.println("\nError: " + e + " Try Again, buddy....");
			}
		}
		
	}


	private void selectProject() {
		List <Project> projects = listProjects();
		Integer projectId = getIntInput("Enter a Project ID to select a project");
		
		curProject = null;
		
	//	curProject= projectService.fetchByProjectId(projectId);
		
		for (Project project : projects) {
			if (project.getProjectId().equals(projectId)) {
				curProject = projectService.fetchByProjectId(projectId);
				break;
			}
		}
		
		if (Objects.isNull(curProject)) {
		      throw new NoSuchElementException("Project ID " + projectId + " Does Not Currently Belong to a Project!");
		
		      
		
		
	}
	}


	private List<Project> listProjects() {
		List<Project> projects = projectService.fetchAllProjects();
		
		System.out.println("\nProjects: ");
		
		projects.forEach(project -> System.out.println("   " + project.getProjectId() + ": " + project.getProjectName()));
		return projects;
	}



	
	private void createProject() {
		String projectName= getStringInput("Enter the Project Name");
		BigDecimal estimatedHours = getDecimalInput("Enter the Estimated Hours");
		BigDecimal actualHours = getDecimalInput("Enter the Actual Hours");
		Integer difficulty = getIntInput("Enter the Project Difficulty (1-5)");
		String notes = getStringInput("Enter the Project Notes");
		
		Project project = new Project();
		
		project.setProjectName(projectName);
		project.setEstimatedHours(estimatedHours);
		project.setActualHours(actualHours);
		project.setDifficulty(difficulty);
		project.setNotes(notes);
		
		Project dbProject = projectService.addProject(project);
		System.out.println("Project Created Successfully" + dbProject);


	}



	
	private boolean exitMenu() {
		System.out.println("\nNow Exiting the Menu. Stand By!");
		return true;
	}


	private int getUserSelection() {
		printOperations();
		
		Integer input = getIntInput("\nEnter a Selection from the Menu Above");
		
		return Objects.isNull(input) ? -1 : input;	
	}


	private Integer getIntInput(String prompt) {
		String input =getStringInput(prompt);
		if(Objects.isNull(input)) {
			  return null;
		  }
		try {
			return Integer.valueOf(input);
		}catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid entry.");
		}
	}


	private String getStringInput(String prompt) {
		System.out.print(prompt + ": ");
		String input = scanner.nextLine();
		
		return input.isBlank() ? null : input.trim();
 	}


	private BigDecimal getDecimalInput(String prompt) {
		String input =getStringInput(prompt);
		if(Objects.isNull(input)) {
			  return null;
		  }
		try {
			return new BigDecimal(input).setScale(2);
		}catch(NumberFormatException e) {
			throw new DbException(input + " is not a valid decimal entry.");
		}
	}


	private void printOperations() {
		System.out.println("\nHere are the available selections. Press the Enter key to quit: ");
		//Line??>
		operations.forEach(input -> System.out.println("      " + input));
		
		
		if (Objects.isNull(curProject)) {
			System.out.println("\n No Current Project!");
		} else {
			System.out.println("\nYou have selected Project: " + curProject );
		}

	}

	
	
	
}
