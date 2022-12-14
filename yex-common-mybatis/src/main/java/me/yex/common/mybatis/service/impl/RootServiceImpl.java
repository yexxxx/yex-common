package me.yex.common.mybatis.service.impl;

import me.yex.common.mybatis.mapper.RootMapper;
import me.yex.common.mybatis.service.IRootService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collection;

/**
 * @author yex
 * @description cn.zhenhealth.health.common.mybatis.service.impl
 */
public class RootServiceImpl<M extends RootMapper<T>, T>  extends ServiceImpl<M,T> implements IRootService<T> {

    @Override
    public int InsertBatchMysql(Collection<T> entityList) {
        return baseMapper.InsertBatchMysql(entityList);
    }

    @Override
    public int UpdateBatchMysql(Collection<T> entityList) {
        return baseMapper.UpdateBatchMysql(entityList);
    }
}
