package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchLogParam {

    // LogType
    private Integer type;

    private String beforeSeg;

    private String afterSeg;

    private String operator;

    //yyyy-MM-dd HH:mm:ss
    private String fromTime;

    private String toTime;
}
