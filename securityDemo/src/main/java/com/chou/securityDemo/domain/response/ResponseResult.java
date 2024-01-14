package com.chou.securityDemo.domain.response;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName ResponseResult
 * @Date 2023/12/31 14:55
 * @Version 1.0
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseResult<T>  {

	private Integer code;

	private String msg;

	private T Data;

	public ResponseResult(Integer code,String msg){
		this.code = code;
		this.msg = msg;
	}

	public ResponseResult(Integer code,T date){
		this.code = code;
		this.Data = date;
	}

	public ResponseResult(Integer code,String msg,T date){
		this.code = code;
		this.msg = msg;
		this.Data = date;
	}


	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return Data;
	}

	public void setData(T data) {
		Data = data;
	}
}
