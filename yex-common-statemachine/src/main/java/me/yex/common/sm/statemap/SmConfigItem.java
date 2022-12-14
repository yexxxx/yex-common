package me.yex.common.sm.statemap;

import lombok.Data;
import lombok.experimental.Accessors;
import java.util.List;

@Data
public class SmConfigItem {
    private String from;
    private String to;
    private List<String> actionList;
}
