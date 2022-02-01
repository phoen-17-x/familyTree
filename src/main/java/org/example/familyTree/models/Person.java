package org.example.familyTree.models;


import javax.validation.constraints.*;

public class Person {

    private Integer id;
    private Integer spouse_id;

    @NotBlank(message = "Name should not be empty!")
    @Size(min = 2, max = 30, message = "Your name should be 2-30 characters long!")
    private String name;

    public Person() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpouse_id() {
        return spouse_id;
    }

    public void setSpouse_id(Integer spouse_id) {
        this.spouse_id = spouse_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
