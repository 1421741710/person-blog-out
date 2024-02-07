package com.itluo.mapper;

import com.itluo.annotation.AutoFill;
import com.itluo.entity.UserIp;
import com.itluo.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface UserIpMapper {

    /**
     * 添加登录ip地址
     * @param userIp
     */
    @Insert("insert into user_ip(user_id, user_ip, create_time, update_time) " +
            "values(#{userId},#{userIp},#{createTime},#{updateTime});")
    @AutoFill(OperationType.INSERT)
    void insert(UserIp userIp);
}
