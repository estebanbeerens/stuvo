package be.thomasmore.stuvo.Models;


import java.time.LocalDate;

public class Activity {
    private long id;
    private String name;
    private String date;
    private String address;
    private int price;
    private int amountOfStudents;
    private String description;
    private String campus;
    private boolean accepted;
    private long studentId;

    public Activity() {

    }

    public Activity(long id, String name, String date, String address, int price, int amountOfStudents, String description, String campus, boolean accepted, long studentId) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.address = address;
        this.price = price;
        this.amountOfStudents = amountOfStudents;
        this.description = description;
        this.campus = campus;
        this.accepted = accepted;
        this.studentId = studentId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmountOfStudents() {
        return amountOfStudents;
    }

    public void setAmountOfStudents(int amountOfStudents) {
        this.amountOfStudents = amountOfStudents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public long getStudentId() {
        return studentId;
    }

    public void setStudentId(long studentId) {
        this.studentId = studentId;
    }
}
