package ru.top.dz_20responseentity.dto;

import ru.top.dz_20responseentity.model.Student;

public class StudentDTO {
    private String name;
    private String surName;
    private Integer age;
    private Long phone;
    private String email;

    public StudentDTO(String name, String surName, Integer age, Long phone, String email) {
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.phone = phone;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Student convert(){
        return new Student(this.name, this.surName, this.age, this.phone, this.email);
    }
}

