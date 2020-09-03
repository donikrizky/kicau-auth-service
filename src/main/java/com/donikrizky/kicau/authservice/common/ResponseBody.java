package com.donikrizky.kicau.authservice.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.Data;

import java.time.LocalDateTime;

@JsonInclude(Include.NON_NULL)
@Data
public class ResponseBody {
    
    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String exception;
    private Object message;
    private Object content;
    private String path;
    
    @Override
    public String toString() {
	return "ResponseBody [timestamp=" + timestamp + ", status=" + status + ", error=" + error + ", message="
	        + message + ", content=" + content + ", path=" + path + "]";
    }
}
