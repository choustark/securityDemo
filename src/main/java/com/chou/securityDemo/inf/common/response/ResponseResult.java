package com.chou.securityDemo.inf.common.response;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Chou
 * @version 1.0
 * @description 统一结果类
 * @className ResponseResult
 * @date 2024/5/5 16:24
 **/

@Setter
@Getter
public class ResponseResult<T> implements Serializable {
	public static final long serialVersionUID = -3419191897119327877L;

	private T data;
	private Integer code;
	private String msg;

	public ResponseResult() {
	}

	public ResponseResult(T data) {
		this.data = data;
		this.code = 200;
		this.msg = "success";
	}

	public ResponseResult(T data, Integer code, String msg) {
		this.data = data;
		this.code = code;
		this.msg = msg;
	}

    /**
	 * 执行成功
	 * @param data
	 * @return
	 */
	public static <T> ResponseResult<T> success(T data) {
		return new ResponseResult<>(data, 200, "操作成功");
	}

	/**
	 * 执行失败
	 * @return
	 */
	public static <T> ResponseResult<T> fail(T data){
		return new ResponseResult<>(data, 500, "系统维护中，请稍后重试...");
	}

	/**
	 * 执行失败
	 * @return
	 */
	public static <T> ResponseResult<T> fail(){
		return new ResponseResult<>(null, 401, "认证失败，请稍后重试");
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ResponseResult<?> that = (ResponseResult<?>) o;
		return Objects.equals(getData(), that.getData()) && Objects.equals(getCode(), that.getCode()) && Objects.equals(getMsg(), that.getMsg());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getData(), getCode(), getMsg());
	}
}
