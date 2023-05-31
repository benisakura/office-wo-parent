package com.office.process.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.office.common.result.Result;
import com.office.model.process.Process;
import com.office.process.service.OaProcessService;
import com.office.vo.process.ProcessQueryVo;
import com.office.vo.process.ProcessVo;
import io.swagger.annotations.ApiOperation;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 审批类型 前端控制器
 * </p>
 *
 * @author chp
 * @since 2023-05-25
 */
@RestController
@RequestMapping("/admin/process")
public class OaProcessController {
    @Autowired
    private OaProcessService processService;

    //审批管理列表
//    @PreAuthorize("hasAuthority('bnt.process.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("{page}/{limit}")
    public Result index(@PathVariable Long page, @PathVariable Long limit, ProcessQueryVo processQueryVo){
        Page<ProcessVo> pageParam = new Page<>(page,limit);
        IPage<ProcessVo> pageModel=processService.selectPage(pageParam,processQueryVo);
        return Result.ok(pageModel);
    }
}

