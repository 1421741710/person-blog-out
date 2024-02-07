package com.itluo.mapper;

import com.github.pagehelper.Page;
import com.itluo.annotation.AutoFill;
import com.itluo.dto.UserPageQueryDTO;
import com.itluo.dto.UserUpDTO;
import com.itluo.entity.User;
import com.itluo.enumeration.OperationType;
import com.itluo.vo.StatisticalCountVO;
import com.itluo.vo.UserSelectVO;
import com.itluo.vo.UserVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface UserMapper {

    /**
     * 分页查询
     * @param userPageQueryDTO
     * @return
     */
    Page<UserVO> pageQuery(UserPageQueryDTO userPageQueryDTO);

    /**
     * 根据id查询数据
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User findByUserId(Long id);

    /**
     * 修改信息
     * @param user
     */
    @AutoFill(OperationType.UPDATE)
    void update(User user);

    /**
     * 根据账号查询用户信息
     * @param username
     * @return
     */
    @Select("select u.id,u.password,i.name,i.img from user u " +
            "LEFT JOIN user_info i ON i.user_id = u.id " +
            "where username = #{username};")
    UserSelectVO findByUserName(String username);

    /**
     * 根据账号查询用户信息
     * @param username
     * @return
     */
    @Select("select * from user where username = #{username};")
    User findByUserNameS(String username);

    /**
     * 新增用户
     * @param users
     */
    @Insert("insert into user(username, password, mailbox, role, status,create_time, update_time) " +
            "values(#{username},#{password},#{mailbox},#{role},#{status},#{createTime},#{updateTime});")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    @AutoFill(OperationType.INSERT)
    void insert(User users);

    /**
     * 根据邮箱查询用户信息
     * @param mailbox
     * @return
     */
    @Select("select * from user where mailbox = #{mailbox};")
    User findByUserMailbox(String mailbox);

    /**
     * 统计用户
     * @return
     */
    @Select("select count(*) from user;")
    Long findByUserCount();


    /**
     * 统计同一个用户
     * @return
     */
    @Select("SELECT DATE(create_time) AS create_time,COUNT(*) AS user_count FROM user GROUP BY DATE(create_time);")
    List<StatisticalCountVO> findByUserGrowth();

    /**
     * 批量查询
     * @param ids
     * @return
     */
    List<User> batchSelectUsers(List<Long> ids);
}
