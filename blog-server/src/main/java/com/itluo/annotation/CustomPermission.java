package com.itluo.annotation;


import com.itluo.enumeration.RoleType;

import javax.management.relation.Role;
import java.lang.annotation.*;

/**
 * @author Administrator
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomPermission {

    RoleType value() ;

}
