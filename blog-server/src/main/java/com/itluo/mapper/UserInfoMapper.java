package com.itluo.mapper;

import com.github.pagehelper.Page;
import com.itluo.annotation.AutoFill;
import com.itluo.dto.UserInfoPageQueryDTO;
import com.itluo.entity.UserInfo;
import com.itluo.enumeration.OperationType;
import com.itluo.vo.UserInfoVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @author Administrator
 */
@Mapper
public interface UserInfoMapper {

    /**
     * 分页查询
     * @param userInfoPageQueryDTO
     * @return
     */
    Page<UserInfo> pageQuery(UserInfoPageQueryDTO userInfoPageQueryDTO);

    /**
     * 根据用户信息主键获取用户信息
     * @param id
     * @return
     */
    @Select("select ui.id,ui.name,ui.img,ui.phone,ui.address,u.mailbox from user_info ui left join user u on u.id = ui.user_id where ui.user_id = #{id};")
    UserInfoVO findByUserInfoId(Long id);

    /**
     * 修改用户信息
     * @param userInfos
     */
    @AutoFill(OperationType.UPDATE)
    void update(UserInfo userInfos);

    /**
     * 根据用户id查询用户信息
     * @param id
     * @return
     */
    @Select("select * from user_info where user_id = #{id};")
    UserInfo findByUserId(Long id);

    /**
     * 根据用户信息主键获取用户信息
     * @param id
     * @return
     */
    @Select("select * from user_info where id = #{id}")
    UserInfo findByUserInfoIds(Long id);

    /**
     * 新增用户信息
     * @param userInfo
     */
    @Insert("insert into user_info(user_id, name, img, create_time, update_time) " +
            "values(#{userId},#{name},#{img},#{createTime},#{updateTime});")
    @AutoFill(OperationType.INSERT)
    void insert(UserInfo userInfo);
}
