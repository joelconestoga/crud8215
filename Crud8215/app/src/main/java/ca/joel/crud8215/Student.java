package ca.joel.crud8215;

import java.io.Serializable;

//Student class with only getters/setters
public class Student implements Serializable{

    private int id;
    private String firstName;
    private String lastName;
    private int mark;

    public Student(int id, String firstName, String lastName, int mark) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mark = mark;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public int getMark() {
        return mark;
    }
}
