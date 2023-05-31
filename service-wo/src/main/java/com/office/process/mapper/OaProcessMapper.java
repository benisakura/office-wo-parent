package com.office.process.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.office.model.process.Process;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.office.vo.process.ProcessQueryVo;
import com.office.vo.process.ProcessVo;
import org.apache.ibatis.annotations.Param;


/**
 * <p>
 * 审批类型 Mapper 接口
 * </p>
 *
 * @author chp
 * @since 2023-05-25
 */
public interface OaProcessMapper extends BaseMapper<Process> {
     IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam,@Param("vo") ProcessQueryVo processQueryVo);
}
