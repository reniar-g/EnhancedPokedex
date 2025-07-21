package controller;

import java.util.ArrayList;
import model.*;
import util.*;

public class MoveController {

    private final ArrayList<Move> moveList;

    public MoveController(ArrayList<Move> moveList) {
        this.moveList = moveList;
    }

    /**
     * Moves Management submenu
     */
    public void moveManagement() {
        boolean running = true;
        while (running) {
            MenuArtUtils.movesArt();
            MenuArtUtils.printMovesMenu();
            int choice = InputUtils.getIntInput("Enter your choice: ");
            switch (choice) {
                case 1 ->
                    addMove();
                case 2 ->
                    viewAllMoves();
                case 3 ->
                    searchMoves();
                case 0 ->
                    running = false;
                default ->
                    System.out.println("\u001B[31mInvalid choice. Please try again.\u001B[0m");
            }
        }
    }

    /**
     * Adds a new move to the move list.
     */
    private void addMove() {
        System.out.println("\n-- Add New Move --");
        String name = InputUtils.getStringInput("Move Name: ");
        if (isMoveNameExists(name)) {
            System.out.println("\u001B[31mError: Move name already exists.\u001B[0m");
            return;
        }
        String description = InputUtils.getStringInput("Description: ");
        String classification;
        while (true) {
            classification = InputUtils.getStringInput("Classification (HM/TM): ").toUpperCase();
            if (classification.equals("HM") || classification.equals("TM")) {
                break;
            }
            System.out.println("\u001B[31mInvalid classification. Please enter HM or TM.\u001B[0m");
        }
        String type1 = InputUtils.getValidTypeInput("Type 1: ", false);
        String type2 = InputUtils.getValidTypeInput("Type 2 (press Enter if none): ", true);
        Move m = new Move(name, description, classification, type1, type2);
        moveList.add(m);
        System.out.println("\n\u001B[32mMove added successfully!\u001B[0m");
    }

    /**
     * Displays all moves in the move list.
     */
    private void viewAllMoves() {
        MenuArtUtils.allMoves();
        if (moveList.isEmpty()) {
            System.out.println("\u001B[31mNo moves in the database.\u001B[0m");
            return;
        }
        Move.displayMoveHeader();
        for (Move m : moveList) {
            m.displayMove();
        }
        System.out.println();
    }

    /**
     * Searches moves by keyword or filter.
     */
    private void searchMoves() {
        System.out.println("\n-- Search Moves --");
        String keyword = InputUtils.getStringInput("Enter keyword (name/type/classification): ").toLowerCase();
        boolean found = false;
        boolean headerDisplayed = false;
        for (Move m : moveList) {
            if (m.getMoveName().toLowerCase().contains(keyword)
                    || m.getMoveType1().toLowerCase().contains(keyword)
                    || m.getMoveClassification().toLowerCase().contains(keyword)
                    || m.getMoveDescription().toLowerCase().contains(keyword)) {
                if (!headerDisplayed) {
                    Move.displayMoveHeader();
                    headerDisplayed = true;
                }
                m.displayMove();
                found = true;
            }
        }
        if (!found) {
            System.out.println("\u001B[31mNo moves found matching the search criteria.\u001B[0m");
        }
    }

    /**
     * Checks if a move name already exists in the move list.
     */
    private boolean isMoveNameExists(String name) {
        for (Move m : moveList) {
            if (m.getMoveName().equalsIgnoreCase(name)) {
                return true; // Move name already exists
            }
        }
        return false; // Move name does not exist
    }

}
