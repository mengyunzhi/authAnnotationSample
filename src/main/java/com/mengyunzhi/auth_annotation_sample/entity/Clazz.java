package com.mengyunzhi.auth_annotation_sample.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Clazz implements YunZhiEntity {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;

    @ManyToOne
    private Teacher teacher;

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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public Teacher getBelongToTeacher() {
        return this.getTeacher();
    }
}
