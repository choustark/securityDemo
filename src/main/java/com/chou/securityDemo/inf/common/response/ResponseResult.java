package com.chou.securityDemo.inf.common.response;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Chou
 * @version 1.0
 * @description 统一结果类
 * @className ResponseResult
 * @date 2024/5/5 16:24
 **/

public class ResponseResult<T> implements Serializable {
	public static final long serialVersionUID = -3419191897119327877L;

	private T data;
	private Integer code;
	private String msg;
	private String message;

	public ResponseResult() {
	}

	public ResponseResult(T data) {
		this.data = data;
		this.code = 200;
		this.msg = "success";
	}

	public ResponseResult(T data, Integer code, String msg, String message) {
		this.data = data;
		this.code = code;
		this.msg = msg;
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * 执行成功
	 * @param data
	 * @return
	 */
	public static <T> ResponseResult<T> success(T data, String message) {
		return new ResponseResult<>(data, 200, "success", message);
	}

	/**
	 * 执行失败
	 * @return
	 */
	public static <T> ResponseResult<T> fail(T data){
		return new ResponseResult<>(data, 500, "fail", "请求失败！");
	}

	/**
	 * 执行失败
	 * @return
	 */
	public static <T> ResponseResult<T> fail(String message){
		return new ResponseResult<>(null, 401, "fail", message);
	}


	/**
	 * 执行失败
	 * @return
	 */
	public static <T> ResponseResult<T> fail(Integer code,String message){
		return new ResponseResult<>(null, code, "fail", message);
	}


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ResponseResult<?> that = (ResponseResult<?>) o;
		return Objects.equals(getData(), that.getData()) && Objects.equals(getCode(), that.getCode()) && Objects.equals(getMsg(), that.getMsg()) && Objects.equals(getMessage(), that.getMessage());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getData(), getCode(), getMsg(), getMessage());
	}
}
