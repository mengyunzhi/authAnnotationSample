package com.mengyunzhi.auth_annotation_sample.aspect;

import com.mengyunzhi.auth_annotation_sample.annotation.AuthorityAnnotation;
import com.mengyunzhi.auth_annotation_sample.entity.Teacher;
import com.mengyunzhi.auth_annotation_sample.entity.YunZhiEntity;
import com.mengyunzhi.auth_annotation_sample.service.TeacherService;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Aspect
@Component
public class AuthorityAspect implements ApplicationContextAware{
    private final static Logger logger = LoggerFactory.getLogger(AuthorityAspect.class);
    private ApplicationContext applicationContext = null;           // spring上下文，用于使用spring获取Bean

    @Autowired
    TeacherService teacherService;

    // 定义切点。使用 && 来获取多个参数，使用@annotation(authorityAnnotation)来获取authorityAnnotation注解
    @Pointcut("@annotation(com.mengyunzhi.auth_annotation_sample.annotation.AuthorityAnnotation) && args(id,..) && @annotation(authorityAnnotation)")
    public void doAccessCheck(Object id, AuthorityAnnotation authorityAnnotation) {
    }

    @Before("doAccessCheck(id, authorityAnnotation)")
    public void before(Object id, AuthorityAnnotation authorityAnnotation) {
        logger.debug("获取注解上的repository, 并通过applicationContext来获取bean");
        Class<?> repositoryClass = authorityAnnotation.repository();
        Object object = applicationContext.getBean(repositoryClass);

        logger.debug("将Bean转换为CrudRepository");
        CrudRepository<YunZhiEntity, Object> crudRepository = (CrudRepository<YunZhiEntity, Object>)object;

        logger.debug("获取实体对象");
        Optional<YunZhiEntity> yunZhiEntityOptional = crudRepository.findById(id);
        if(!yunZhiEntityOptional.isPresent()) {
            throw new RuntimeException("对不起，未找到相关的记录");
        }
        YunZhiEntity yunZhiEntity = yunZhiEntityOptional.get();

        logger.debug("获取登录教师以及拥有者，并进行比对");
        Teacher belongToTeacher  = yunZhiEntity.getBelongToTeacher();
        Teacher currentLoginTeacher = teacherService.getCurrentLoginTeacher();
        if (currentLoginTeacher != null && belongToTeacher != null) {
            if (!belongToTeacher.getId().equals(currentLoginTeacher.getId())) {
                throw new RuntimeException("权限不允许");
            }
        }
    }

    /**
     * 将应用上下文绑定到私有变量中
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
