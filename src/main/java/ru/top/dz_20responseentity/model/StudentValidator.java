package ru.top.dz_20responseentity.model;

import ru.top.dz_20responseentity.dto.StudentDTO;

import java.util.List;

public class StudentValidator {
    public static String validate(StudentDTO studentDTO, List<Student> student){
        if (studentDTO.getName() == null || studentDTO.getName().isEmpty()){
            return "Введите Имя верно";
        }
        if (studentDTO.getSurName() == null || studentDTO.getSurName().isEmpty()){
            return "Введите фамилию верно";
        }
        if (studentDTO.getAge() < 18 || studentDTO.getAge() > 50) {
            return "Не подходит возраст студента";
        }
        String email = studentDTO.getEmail();
        if (email == null || !email.contains("@") || !email.contains(".")) {
            return "Неверный формат email'a";
        }
        boolean duplicateEmail = student.stream()
                .anyMatch(s -> s.getEmail().equals(email));
        if (duplicateEmail) {
            return "Студент с таким email уже существует";
        }
       return  null;
    }
}
