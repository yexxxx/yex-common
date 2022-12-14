package me.yex.common.sm.statemap;

import java.util.Arrays;
import java.util.List;

public class SmConfig {
    private List<SmConfigItem> states;

    public SmConfig(){

    }

    public SmConfig(SmConfigItem ...smConfigItems){
        this.states = Arrays.asList(smConfigItems);
    }

    public List<SmConfigItem> getStates() {
        return states;
    }

    public void setStates(List<SmConfigItem> states) {
        this.states = states;
    }
}
