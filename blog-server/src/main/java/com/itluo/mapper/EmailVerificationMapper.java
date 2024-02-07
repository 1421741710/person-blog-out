package com.itluo.mapper;

import com.github.pagehelper.Page;
import com.itluo.dto.EmailVerificationPageQueryDTO;
import com.itluo.entity.EmailVerification;
import org.apache.ibatis.annotations.*;

/**
 * @author Administrator
 */
@Mapper
public interface EmailVerificationMapper {

    /**
     * 存储当前邮箱内容
     * @param emailVerification
     */
    @Insert("insert into email_verification(email, request_count, last_request_time, status) " +
            "values(#{email},#{requestCount},#{lastRequestTime},#{status});")
    void insert(EmailVerification emailVerification);

    /**
     * 根据邮箱查询信息
     * @param mailbox
     * @return
     */
    @Select("select * from email_verification where email = #{mailbox};")
    EmailVerification findByEmail(String mailbox);

    /**
     * 根据id删除信息
     * @param id
     */
    @Delete("delete from email_verification where id = #{id};")
    void delete(long id);

    /**
     * 根据id修改信息
     * @param emailVerification
     */
    @Update("update email_verification set request_count = #{requestCount},last_request_time = #{lastRequestTime},status = #{status} where id = #{id};")
    void update(EmailVerification emailVerification);

    /**
     * 分页查询
     * @param emailVerificationPageQueryDTO
     * @return
     */
    Page<EmailVerification> pageQuery(EmailVerificationPageQueryDTO emailVerificationPageQueryDTO);
}
