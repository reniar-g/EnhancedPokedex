package controller;

import java.util.ArrayList;
import model.Move;

public class MoveController {

    private final ArrayList<Move> moveList;

    public MoveController(ArrayList<Move> moveList) {
        this.moveList = moveList;
    }

    public ArrayList<Move> getMoveList() {
        return moveList;
    }

    // Removed terminal-based methods since we're using GUI only
    /**
     * Adds a new move to the move list via GUI. Returns true if added, false if
     * not (e.g., duplicate name or invalid input).
     */
    public boolean addMove(String name, String type1, String type2, String classification, String description) {
        if (name == null || name.trim().isEmpty()
                || type1 == null || type1.trim().isEmpty()
                || classification == null || classification.trim().isEmpty()
                || description == null || description.trim().isEmpty()) {
            return false;
        }
        if (isMoveNameExists(name)) {
            return false;
        }
        // Only allow HM or TM for classification
        String classUpper = classification.trim().toUpperCase();
        if (!classUpper.equals("HM") && !classUpper.equals("TM")) {
            return false;
        }
        String type2Value = (type2 == null) ? "" : type2.trim();
        Move m = new Move(name.trim(), description.trim(), classUpper, type1.trim(), type2Value);
        moveList.add(m);
        return true;
    }

    // Removed console output methods - GUI handles move display
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
