package controller;

import java.util.ArrayList;
import model.*;

public class ItemController {

    private final ArrayList<Item> itemList;

    public ItemController(ArrayList<Item> itemList) {
        this.itemList = itemList;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }
}
