package me.yex.common.mybatis.Injector;

import me.yex.common.mybatis.methods.InsertBatch;
import me.yex.common.mybatis.methods.InsertBatchNoKey;
import me.yex.common.mybatis.methods.UpdateBatch;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;


import java.util.List;

/**
 * 自定义 SqlInjector
 *
 * @author yex
 */
public class RootSqlInjector extends DefaultSqlInjector {

    /**
     * 如果只需增加方法，保留MP自带方法
     * 可以super.getMethodList() 再add
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass, tableInfo);
        methodList.add(new InsertBatch());
        methodList.add(new InsertBatchNoKey());
        methodList.add(new UpdateBatch());
        return methodList;
    }
}
