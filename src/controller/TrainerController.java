package controller;

import util.MenuArtUtils;
import model.*;
import util.*;

import java.util.ArrayList;

public class TrainerController {

	private final ArrayList<Trainer> trainerList;

	public TrainerController(ArrayList<Trainer> trainerList) {
	this.trainerList = trainerList;
	}

	/**
	 * Trainer Management submenu
	 */
	public void trainerManagement() {
		boolean running = true;
		while (running) {
			MenuArtUtils.trainersArt();
			MenuArtUtils.printTrainerMenu();
			int choice = InputUtils.getIntInput("Enter your choice: ");
			switch (choice) {
				case 1:
					addTrainer();
					break;
				case 2:
					viewAllTrainers();
					break;
				case 3:
					searchTrainers();
					break;
				case 4:
					manageTrainer();
					break;
				case 0:
					running = false;
					break;
				default:
					System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
			}
		}
	}

	/**
	 * Adds a new trainer to the system.
	 */
	private void addTrainer() {
		System.out.println("\n-- Add New Trainer --");
		int trainerId = InputUtils.getIntInput("Trainer ID: ");
		if (isTrainerIdExists(trainerId)) {
			System.out.println("Error: Trainer ID already exists.");
			return; // Exit if ID already exists
		}

		String name = InputUtils.getStringInput("Name: ");
		String birthdate = InputUtils.getStringInput("Birthdate (YYYY-MM-DD): ");
		String sex = getValidSex();
		String hometown = InputUtils.getStringInput("Hometown: ");
		String description = InputUtils.getStringInput("Description: ");

		Trainer trainer = new Trainer(trainerId, name, birthdate, sex, hometown, description);
		trainerList.add(trainer);
		System.out.println("Trainer " + name + " added successfully!");
	}

	/**
	 * Displays all trainers in the system.
	 */
	private void viewAllTrainers() {
		System.out.println("\n-- All Trainers --");
		if (trainerList.isEmpty()) {
			System.out.println("No trainers in the database.");
			return; // Exit if no trainers are available
		}
		for (Trainer trainer : trainerList) {
			trainer.displayTrainer();
		}
	}

	/**
	 * Searches trainers by keyword.
	 */
	private void searchTrainers() {
		System.out.println("\n-- Search Trainers --");
		String keyword = InputUtils.getStringInput("Enter keyword (name/hometown/description): ").toLowerCase();
		boolean found = false;
		for (Trainer trainer : trainerList) {
			if (trainer.getTrainerName().toLowerCase().contains(keyword)
					|| trainer.getTrainerHometown().toLowerCase().contains(keyword)
					|| trainer.getTrainerDescription().toLowerCase().contains(keyword)
					|| String.valueOf(trainer.getTrainerId()).equals(keyword)) {
				trainer.displayTrainer();
				found = true;
			}
		}
		if (!found) {
			System.out.println("No trainers found matching the search criteria.");
		}
	}

	/**
	 * Checks if a trainer ID already exists in the trainer list.
	 */
	private boolean isTrainerIdExists(int trainerId) {
		for (Trainer trainer : trainerList) {
			if (trainer.getTrainerId() == trainerId) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Gets a valid sex input from the user for Trainer.
	 */
	public static String getValidSex() {
		while (true) {
			String sex = InputUtils.getStringInput("Sex (M/F): ").toUpperCase();
			if (sex.equals("M") || sex.equals("F")) {
				return sex;
			}
			System.out.println("Please enter M or F only.");
		}
	}


	/**
	 * Manages a selected trainer (placeholder implementation lang muna to).
	 */
	private void manageTrainer() {
		System.out.println("\n-- Manage Trainer --");
		if (trainerList.isEmpty()) {
			System.out.println("No trainers available to manage.");
			return;
		}
		// List trainers
		for (int i = 0; i < trainerList.size(); i++) {
			Trainer trainer = trainerList.get(i);
			System.out.println((i + 1) + ". " + trainer.getTrainerName() + " (ID: " + trainer.getTrainerId() + ")");
		}
		int choice = InputUtils.getIntInput("Select trainer by number: ") - 1;
		if (choice < 0 || choice >= trainerList.size()) {
			System.out.println("Invalid selection.");
			return;
		}
		Trainer selectedTrainer = trainerList.get(choice);
		System.out.println("Managing trainer: " + selectedTrainer.getTrainerName());
		// add future stuffhere
	}
}
