package com.entity;

public class Intern {
    private int id;
    private String firstname;
    private String lastname;
    private String email;
    private int groupId;

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    @Override
    public String toString() {
        return "{\n" +
                " id=" + id +
                "\n firstname=" + firstname +
                "\n lastname=" + lastname +
                "\n email=" + email +
                "\n groupId=" + groupId +
                "\n}\n";
    }
}
