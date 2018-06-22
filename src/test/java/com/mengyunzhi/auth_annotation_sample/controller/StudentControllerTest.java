package com.mengyunzhi.auth_annotation_sample.controller;

import com.mengyunzhi.auth_annotation_sample.entity.Student;
import com.mengyunzhi.auth_annotation_sample.entity.Teacher;
import com.mengyunzhi.auth_annotation_sample.repository.StudentRepository;
import com.mengyunzhi.auth_annotation_sample.repository.TeacherRepository;
import com.mengyunzhi.auth_annotation_sample.service.TeacherService;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@Transactional
public class StudentControllerTest {
    private final static Logger logger = LoggerFactory.getLogger(StudentControllerTest.class);
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    TeacherService teacherService;
    @Autowired
    TeacherRepository teacherRepository;

    @Test
    public void findById() throws Exception {
        logger.info("创建两个教师");
        Teacher teacher0 = new Teacher();
        teacherRepository.save(teacher0);
        Teacher teacher1 = new Teacher();
        teacherRepository.save(teacher0);

        logger.debug("创建一个学生，并指明它属于教师0");
        Student student = new Student();
        student.setTeacher(teacher0);
        studentRepository.save(student);

        logger.debug("当前登录为teacher1，断言发生异常");
        teacherService.setCurrentLoginTeacher(teacher1);
        String url = "/Student/" + student.getId().toString();
        Boolean catchException = false;
        try {
            this.mockMvc
                    .perform(MockMvcRequestBuilders
                            .get(url))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
        } catch (Exception e) {
            catchException = true;
            Assertions.assertThat(e.getMessage()).endsWith("权限不允许");
        }
        Assertions.assertThat(catchException).isTrue();

        logger.debug("当前登录为teacher0，断言正常");
        teacherService.setCurrentLoginTeacher(teacher0);
        this.mockMvc
                .perform(MockMvcRequestBuilders
                        .get(url))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}