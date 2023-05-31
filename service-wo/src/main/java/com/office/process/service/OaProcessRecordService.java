package com.office.process.service;

import com.office.model.process.ProcessRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 审批记录 服务类
 * </p>
 *
 * @author chp
 * @since 2023-05-27
 */
public interface OaProcessRecordService extends IService<ProcessRecord> {

    void record(Long processId,Integer status,String description);
}
