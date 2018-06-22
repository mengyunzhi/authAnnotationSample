package com.mengyunzhi.auth_annotation_sample.repository;

import com.mengyunzhi.auth_annotation_sample.entity.Teacher;
import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Teacher, Integer> {
}