package com.mengyunzhi.auth_annotation_sample.repository;

import com.mengyunzhi.auth_annotation_sample.entity.Student;
import org.springframework.data.repository.CrudRepository;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
