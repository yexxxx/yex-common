package me.yex.common.sm.context;

import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
public class Context<T extends Flow> {

    private String flowId;
    private String currentState = null;
    private String nextState = null;
    private T flow;
    private Map attributes = null;

    public Context(String flowId, String currentState, String nextState){
        this.flowId = flowId;
        this.currentState = currentState;
        this.nextState = nextState;
    }


    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public <K, V> V getAttribute(K key) {
        return (V) this.getAttributes().get(key);
    }

    public String getCurrentState() {
        return this.currentState;
    }

    public void setCurrentState(String state) {
        this.currentState = state;
    }


    public void setNextState(String nextState) {
        this.nextState = nextState;
    }

    public String getNextState() {
        return this.nextState;
    }


    public void setFlow(T flow) {
        this.flow = flow;
    }


    public T getFlow() {
        return this.flow;
    }


    public <K, V> void setAttribute(K key, V value) {
        this.getAttributes().put(key, value);
    }


    protected <K, V> Map<K, V> getAttributes() {
        if (this.attributes == null) {
            this.attributes = new HashMap();
        }
        return this.attributes;
    }

    public String getTransition() {
        return this.currentState + this.nextState;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("StateContext[");
        sb.append("currentState=").append(this.currentState);
        sb.append(",attributes=").append(this.attributes);
        sb.append("]");
        return sb.toString();
    }
}