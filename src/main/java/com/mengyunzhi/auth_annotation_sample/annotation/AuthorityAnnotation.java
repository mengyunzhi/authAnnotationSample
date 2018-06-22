package com.mengyunzhi.auth_annotation_sample.annotation;

import org.springframework.beans.factory.annotation.Required;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 申请表单权限认证
 * @author panjie
 */
@Target({ElementType.METHOD})       // 方法注解
@Retention(RetentionPolicy.RUNTIME) // 在运行时生效
public @interface AuthorityAnnotation {
    // 仓库名称
    @Required
    Class repository();
}

