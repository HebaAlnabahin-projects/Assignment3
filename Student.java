//Heba Alnabahin 220231513
package com.example.demo1.assignment3;

class Student implements Comparable<Student> {
    private Integer id;
    private String name;
    private String major;
    private Double grade;

    public Student(Integer id, String name, String major, Double grade) {
        this.id = id;
        this.name = name;
        this.major = major;
        this.grade = grade;
    }

    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getMajor() { return major; }
    public Double getGrade() { return grade; }

    @Override
    public int compareTo(Student other) {
        return other.getGrade().compareTo(this.grade);
    }

    @Override
    public String toString() {
        return String.format("ID: %-5d | Name: %-10s | Major: %-10s | Grade: %.1f", id, name, major, grade);
    }
}

