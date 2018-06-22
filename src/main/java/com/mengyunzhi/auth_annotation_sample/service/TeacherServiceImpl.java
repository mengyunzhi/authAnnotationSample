package com.mengyunzhi.auth_annotation_sample.service;

import com.mengyunzhi.auth_annotation_sample.entity.Teacher;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl implements TeacherService {
    private Teacher currentLoginTeacher;
    @Override
    public Teacher getCurrentLoginTeacher() {
        return this.currentLoginTeacher;
    }

    @Override
    public void setCurrentLoginTeacher(Teacher teacher) {
        this.currentLoginTeacher = teacher;
    }
}
