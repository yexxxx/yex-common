package me.yex.common.sm.statemachine;


import me.yex.common.sm.context.Context;

public interface SmAction {

    default boolean isAsync() {
        return false;
    }

    default boolean condition(Context context) {
        return true;
    }

    void execute(Context context);
}
