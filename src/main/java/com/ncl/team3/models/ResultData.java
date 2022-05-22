package com.ncl.team3.models;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Lei Chen
 * @version 1.0
 * @StudentNumber: 200936497
 * @date 2022/03/13 18:26:35
 */
@Data
public class ResultData implements Serializable {
    private Object data;
    private Integer code;
    private String message;
    public static final int SUCCESS = 200;
    public static final int ERROR = 400;
    public static final int PARAMETER_ERROR = 404;
    public ResultData(Object data, Integer code, String message) {
        this.data = data;
        this.code = code;
        this.message = message;
    }
    public ResultData( Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
