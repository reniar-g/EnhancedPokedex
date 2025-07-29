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

    // adds a new move to the list
    public boolean addMove(String name, String type1, String type2, String classification, String description) {
        // validate the inputs
        if (name == null || name.trim().isEmpty()
                || type1 == null || type1.trim().isEmpty()
                || classification == null || classification.trim().isEmpty()
                || description == null || description.trim().isEmpty()) {
            return false;
        }
        if (isMoveNameExists(name)) {
            return false;
        }
        // only accepts HM or TM
        String classUpper = classification.trim().toUpperCase();
        if (!classUpper.equals("HM") && !classUpper.equals("TM")) {
            return false;
        }
        String type2Value = (type2 == null) ? "" : type2.trim();
        Move m = new Move(name.trim(), description.trim(), classUpper, type1.trim(), type2Value);
        moveList.add(m);
        return true;
    }

    // checks if a move name already exists in the list
    private boolean isMoveNameExists(String name) {
        for (Move m : moveList) {
            if (m.getMoveName().equalsIgnoreCase(name)) {
                return true; // move name already exists
            }
        }
        return false; // move name does not exist
    }
}
