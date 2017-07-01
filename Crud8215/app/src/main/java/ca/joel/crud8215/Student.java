package ca.joel.crud8215;

public class Student {

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
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public int getMark() {
        return mark;
    }
    public void setMark(int mark) {
        this.mark = mark;
    }
}
