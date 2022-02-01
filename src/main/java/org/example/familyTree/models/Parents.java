package org.example.familyTree.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Parents {

    @NotBlank(message = "Name should not be empty!")
    @Size(min = 2, max = 30, message = "Your name should be 2-30 characters long!")
    private String parentName1;

    @NotBlank(message = "Name should not be empty!")
    @Size(min = 2, max = 30, message = "Your name should be 2-30 characters long!")
    private String parentName2;

    private Integer childID;

    public Parents() {}

    public String getParentName1() {
        return parentName1;
    }

    public void setParentName1(String parentName1) {
        this.parentName1 = parentName1;
    }

    public String getParentName2() {
        return parentName2;
    }

    public void setParentName2(String parentName2) {
        this.parentName2 = parentName2;
    }

    public Integer getChildID() {
        return childID;
    }

    public void setChildID(Integer childID) {
        this.childID = childID;
    }
}
