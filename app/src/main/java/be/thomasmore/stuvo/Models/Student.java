package be.thomasmore.stuvo.Models;


import java.time.LocalDate;

public class Student {
    private long id;
    private String number;
    private String password;
    private String firstName;
    private String lastName;


    public Student() {
    }

    public Student(long id, String number, String password, String firstName, String lastName) {
        this.id = id;
        this.number = number;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}