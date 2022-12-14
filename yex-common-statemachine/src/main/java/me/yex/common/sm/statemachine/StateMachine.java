package me.yex.common.sm.statemachine;

import cn.hutool.core.collection.CollectionUtil;
import me.yex.common.sm.context.Context;
import me.yex.common.sm.context.Flow;
import me.yex.common.sm.statemap.SmConfig;
import me.yex.common.sm.statemap.SmConfigItem;
import me.yex.common.sm.support.ExecutorFactory;
import me.yex.common.sm.support.HandlerMatcher;
import com.alibaba.fastjson.JSON;

import com.google.common.base.Preconditions;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

public class StateMachine<F extends Flow> {

    private HandlerMatcher handlerMatcher;
    private FlowProvider<F> flowProvider;
    private Map<String, List<SmAction>> statusMap;
    private ExecutorService executorService;

    public StateMachine(String config, HandlerMatcher handlerMatcher,
        FlowProvider<F> flowProvider) {
        statusMap = new HashMap<>();
        this.handlerMatcher = handlerMatcher;
        initStatusMap(config);
        executorService = ExecutorFactory.makeExecutor("stateMachine");
        this.flowProvider = flowProvider;
    }

    private void initStatusMap(String config) {
        SmConfig smConfig = JSON.parseObject(config,SmConfig.class);
        List<SmConfigItem> configItemList = smConfig.getStates();
        for (SmConfigItem smConfigItem : configItemList) {
            List<SmAction> actionList = parseActions(smConfigItem.getActionList());
            statusMap.putIfAbsent(smConfigItem.getFrom() + smConfigItem.getTo(), actionList);
        }
    }

    private List<SmAction> parseActions(List<String> actionList) {
        List<SmAction> smActionList = new ArrayList<>();
        for (String action : actionList) {
            smActionList.add(handlerMatcher.match(SmAction.class, action));
        }
        return smActionList;
    }


    /**
     * 1 定义好action
     * 2 根据smconfig 配置好状态机器
     * 3 Context 状态操作的上下文信息
     * 4 FlowProvider  提供flow、加锁flow、更新flow状态
     * 5 Flow 被处理的原始数据    订单
     * 6 Action  分为同步异步     根据传入的contxt执行操作
     */
    @Transactional
    public void tryPushStatus(Context<F> context) {
        F flow = flowProvider.lockFlow(context.getFlow());
        context.setFlow(flow);
        Preconditions.checkArgument(flow.status().equals(context.getCurrentState()), new RuntimeException(
            "state disagree:" + context.getCurrentState() + ":" + flow.status()));
        //todo transition 重复
        List<SmAction> smActions = statusMap.get(context.getTransition());
        Preconditions.checkArgument(CollectionUtil.isNotEmpty(smActions),
            new RuntimeException("invalid transition:" + context.getTransition()));

        //todo 这里更新状态  那action是来做什么的
        flowProvider.updateStatus(flow, context.getCurrentState(), context.getNextState());
        for (SmAction smAction : smActions) {
            if (smAction.condition(context)){
                if (smAction.isAsync()) {
                    executorService.submit(() -> smAction.execute(context));
                } else {
                    smAction.execute(context);
                }
            }
        }
    }
}