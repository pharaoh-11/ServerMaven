package com.entity;

public class Intern extends Entity {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private int groupId;

    public Intern() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public int getGroupId() {
        return groupId;
    }

    public boolean isEmpty() {
        return (id == 0 || firstName == null || lastName == null || email == null || groupId == 0);
    }

    @Override
    public String toString() {
        return "{\n" +
                " id=" + id +
                "\n firstName=" + firstName +
                "\n lastName=" + lastName +
                "\n email=" + email +
                "\n groupId=" + groupId +
                "\n}\n";
    }
}
