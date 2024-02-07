package com.itluo.mapper;

import com.github.pagehelper.Page;
import com.itluo.annotation.AutoFill;
import com.itluo.dto.LogPageQueryDTO;
import com.itluo.entity.Log;
import com.itluo.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Administrator
 */
@Mapper
public interface LogMapper {

    /**
     * 分页查询
     * @param logPageQueryDTO
     * @return
     */
    Page<Log> pageQuery(LogPageQueryDTO logPageQueryDTO);

    /**
     * 添加日志
     * @param logs
     */
    @Insert("insert into log(admin_name, module, operator, class_name, params, ip, execution_time, create_time, update_time) " +
            "VALUES(#{adminName},#{module},#{operator},#{className},#{params},#{ip},#{executionTime},#{createTime},#{updateTime})")
    @AutoFill(OperationType.INSERT)
    void insertLog(Log logs);
}
