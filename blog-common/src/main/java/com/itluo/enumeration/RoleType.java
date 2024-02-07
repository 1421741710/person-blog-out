package com.itluo.enumeration;

/**
 * @author Administrator
 */

public enum RoleType {

    /**
     * 超级管理员
     */
    SUPER_ADMINISTRATOR(1L),

    /**
     * 管理员
     */
    ADMINISTRATOR(2L);

    private Long role;

    RoleType(Long role){
        this.role = role;
    }
    public Long getRole(){
        return this.role;
    }
}
