package com.mengyunzhi.auth_annotation_sample.service;

import com.mengyunzhi.auth_annotation_sample.entity.Teacher;

public interface TeacherService {
    Teacher getCurrentLoginTeacher();
    void setCurrentLoginTeacher(Teacher teacher);
}
