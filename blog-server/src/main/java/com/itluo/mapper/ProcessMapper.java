package com.itluo.mapper;

import com.github.pagehelper.Page;
import com.itluo.annotation.AutoFill;
import com.itluo.dto.ProcessPageQueryDTO;
import com.itluo.entity.Process;
import com.itluo.enumeration.OperationType;
import com.itluo.vo.ProcessVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author Administrator
 */
@Mapper
public interface ProcessMapper {

    /**
     * 分页查询
     * @param processPageQueryDTO
     * @return
     */
    Page<ProcessVO> pageQuery(ProcessPageQueryDTO processPageQueryDTO);

    /**
     * 修改审核
     * @param process
     */
    @AutoFill(OperationType.UPDATE)
    void update(Process process);

    /**
     * 根据审核单id查询审核信息
     * @param id
     * @return
     */
    @Select("select * from process where id = #{id};")
    Process findByProcessId(Long id);

    /**
     * 添加审核
     * @param process
     */
    @Insert("insert into process(user_id,type,content,status,create_time,update_time) " +
            "values(#{userId},#{type},#{content},#{status},#{createTime},#{updateTime});")
    @AutoFill(OperationType.INSERT)
    void insert(Process process);

    /**
     * 查询审核状态为未审核的
     * @return
     */
    @Select("select * from process where status = 1;")
    List<Process> findByProcess();
}
