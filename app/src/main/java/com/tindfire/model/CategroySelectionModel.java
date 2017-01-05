package com.tindfire.model;

/**
 * Created by vcareall on 27/12/16.
 */
public class CategroySelectionModel {

    private String text;
    private boolean isSelected = false;

    public CategroySelectionModel(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
