package me.yex.common.sm.statemachine;

import me.yex.common.sm.context.Flow;

public interface FlowProvider<F extends Flow> {
    F getFlow(String id);

    F lockFlow(F flow);

    void updateStatus(F flow, String fromStatus, String toStatus);
}
