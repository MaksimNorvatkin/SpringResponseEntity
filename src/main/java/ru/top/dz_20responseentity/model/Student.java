package ru.top.dz_20responseentity.model;

import ru.top.dz_20responseentity.dto.StudentDTO;

public class Student {
    private Integer id;
    private String name;
    private String surName;
    private Integer age;
    private Long phone;
    private String email;
    private static int nextId = 1;

    public Student(String name, String surname, Integer age, Long phone, String email) {
        this.id = ++nextId;
        this.name = name;
        this.surName = surname;
        this.age = age;
        this.phone = phone;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public StudentDTO convert(){
        return new StudentDTO(this.name, this.surName, this.age, this.phone, this.email);
    }
}

