package me.yex.common.mybatis.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * @author yex
 * @description cn.zhenhealth.health.common.mybatis.service
 */
public interface IRootService<T> extends IService<T> {

    int InsertBatchMysql(Collection<T> entityList);

    int UpdateBatchMysql(Collection<T> entityList);

}
