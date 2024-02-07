package com.itluo.mapper;

import com.github.pagehelper.Page;
import com.itluo.annotation.AutoFill;
import com.itluo.dto.AdminPageQueryDTO;
import com.itluo.entity.Admin;
import com.itluo.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * @author Administrator
 */
@Mapper
public interface AdminMapper {

    /**
     * 根据账号查询信息
     * @param username
     * @return
     */
    @Select("select * from admin where username = #{username}")
    Admin findByAdminName(String username);

    /**
     * 根据id查询信息
     * @param id
     * @return
     */
    @Select("select * from admin where id = #{id}")
    Admin findByAdminId(Long id);

    /**
     * 修改数据
     * @param admin
     */
    @AutoFill(OperationType.UPDATE)
    void update(Admin admin);

    /**
     * 分页查询
     * @param adminPageQueryDTO
     * @return
     */
    Page<Admin> pageQuery(AdminPageQueryDTO adminPageQueryDTO);

    /**
     * 添加管理员
     * @param admin
     */
    @Insert("insert into admin (username, password, role, status, create_time, update_time) " +
            "values (#{username},#{password},#{role},#{status},#{createTime},#{updateTime});")
    @AutoFill(OperationType.INSERT)
    void insert(Admin admin);
}
