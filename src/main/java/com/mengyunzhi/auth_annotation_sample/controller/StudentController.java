package com.mengyunzhi.auth_annotation_sample.controller;

import com.mengyunzhi.auth_annotation_sample.annotation.AuthorityAnnotation;
import com.mengyunzhi.auth_annotation_sample.entity.Student;
import com.mengyunzhi.auth_annotation_sample.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Student")
public class StudentController {
    @Autowired
    StudentRepository studentRepository;

    @AuthorityAnnotation(repository = StudentRepository.class)
    @GetMapping("{id}")
    public Student findById(@PathVariable("id") Long id) {
        return studentRepository.findById(id).get();
    }
}
