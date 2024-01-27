package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

//Code By Khalil M. 

public class ProjectsApp {

	//@formatter:off
	private List<String> operations = List.of (
			"1) Add a project"
			);
	//@formatter:on
	
	private Scanner scanner = new Scanner (System.in);
	private ProjectService projectService = new ProjectService();
	
	
	
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
					
				default:
					System.out.println("\n" + selection + " is not a valid entry. Try again, buddy");
				}
				
			}catch (Exception e){
				System.out.println("\nError: " + e + " Try Again, buddy....");
			}
		}
		
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

	}

	
	
	
}
