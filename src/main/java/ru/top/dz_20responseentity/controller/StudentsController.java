package ru.top.dz_20responseentity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.top.dz_20responseentity.dto.StudentDTO;
import ru.top.dz_20responseentity.model.Student;
import ru.top.dz_20responseentity.model.StudentValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/student")
public class StudentsController {
    List<Student> students;

    {
        students = new ArrayList<>();
        students.add(new Student("Сергей", "Ельшин", 19, 89223181634L, "web@yandex.ru"));
        students.add(new Student("Маким", "Ритор", 29, 892223434L, "web@yandex.ru"));
        students.add(new Student("Валера", "Аляпов", 39, 824181634L, "web@yandex.ru"));
        students.add(new Student("Саша", "Мированов", 49, 8924214634L, "web@yandex.ru"));
        students.add(new Student("Сергей", "Иванов", 23, 8922318351634L, "web@yandex.ru"));
    }


    @GetMapping("/all")
    public ResponseEntity<List<StudentDTO>> getStudents() {
        if (!students.isEmpty()) {
            List<StudentDTO> students1 = students.stream()
                    .map(Student::convert)
                    .toList();
            return ResponseEntity.status(200).body(students1);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Integer id) {
        if (id != null || id >= 0) {
            Optional<Student> student1 = students.stream()
                    .filter(student -> student.getId().equals(id))
                    .findFirst();
            if (student1.isPresent()) {
                return ResponseEntity.ok().body(student1.get().convert());
            }
        }
        return ResponseEntity.status(400).build();
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody StudentDTO studentDTO) {
        String error = StudentValidator.validate(studentDTO, students);
        if (error != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        students.add(studentDTO.convert());
        return ResponseEntity.status(HttpStatus.CREATED).body("success");
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateById(@RequestBody StudentDTO studentDTO,
                                             @PathVariable Integer id) {
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        String error = StudentValidator.validate(studentDTO, students);
        if (error != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
        Student foundStudent = students.stream()
                .filter(s -> s.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (foundStudent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        foundStudent.setName(studentDTO.getName());
        foundStudent.setName(studentDTO.getSurName());
        foundStudent.setAge(studentDTO.getAge());
        foundStudent.setPhone(studentDTO.getPhone());
        foundStudent.setEmail(studentDTO.getEmail());

        return ResponseEntity.status(HttpStatus.OK).body("success");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteStudentById(@PathVariable Integer id) {
        if (id != null && id >= 0) {
            Student foundStudent = students.stream()
                    .filter(s -> s.getId().equals(id))
                    .findFirst()
                    .orElse(null);
            if (foundStudent == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Студента с ID - " + id + " не существует");
            }
            students.removeIf(s -> s.getId().equals(id));
            return ResponseEntity.status(HttpStatus.OK).body(true);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
    }

    @GetMapping("/search")
    public ResponseEntity<List<StudentDTO>> getSearch(@RequestParam(required = false) String firstName,
                                                      @RequestParam(required = false) String lastName) {
        List<StudentDTO> result = students.stream()
                .filter(student ->
                        (firstName == null || student.getName().equalsIgnoreCase(firstName)) &&
                                (lastName == null || student.getSurName().equalsIgnoreCase(lastName))
                )
                .map(Student::convert)
                .toList();
        if (result.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }
    @GetMapping("/search/email")
    public ResponseEntity<List<StudentDTO>> getSearchByEmail(@RequestParam(required = false) String emailStudent) {
        if (emailStudent == null || !emailStudent.contains("@") || !emailStudent.contains(".")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<StudentDTO> result = students.stream()
                .filter(student -> student.getEmail() != null &&
                        student.getEmail().equalsIgnoreCase(emailStudent))
                .map(Student::convert)
                .toList();
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
    @GetMapping("/search/age")
    public ResponseEntity<List<StudentDTO>> getByAge(@RequestParam Integer min,
                                                     @RequestParam Integer max) {
        if (min > max) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        List<StudentDTO> result = students.stream()
                .filter(student -> student.getAge() >= min && student.getAge() <= max)
                .map(Student::convert)
                .toList();
        if (result.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


}

