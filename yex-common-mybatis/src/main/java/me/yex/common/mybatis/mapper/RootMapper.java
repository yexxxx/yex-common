package me.yex.common.mybatis.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;

/**
 * @author yex
 * @description cn.zhenhealth.health.common.mybatis.mapper
 */
public interface RootMapper<T> extends BaseMapper<T> {

    int InsertBatchMysql(@Param("list") Collection<T> batchList);

    int UpdateBatchMysql(@Param("list") Collection<T> batchList);
}
