package com.chou.securityDemo.domain.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author Chou
 * @Description 用戶表
 * @ClassName User
 * @Date 2023/12/30 19:22
 * @Version 1.0
 **/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("vista_user")
public class User implements Serializable {
	public static final long serialVersionUID = 6054144112660315561L;
	/**
	 * 主键
	 */
	private Long id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 用户编号
	 */
	private String userNo;

	/**
	 * 性别 0:女士,1:男士,3 未知
	 */
	private Integer gender;

	/**
	 * 邮箱
	 */
	private String email;

	/**
	 * 手机号码
	 */
	private String phoneNumber;

	/**
	 * 身份证'
	 */
	private String identityCard;

	/**
	 * 头像
	 */
	private String headPortrait;

	/**
	 * 省份省份
	 */
	private String province;

	/**
	 * 所属市
	 */
	private String city;

	/**
	 * 所属区或者县
	 */
	private String region;

	/**
	 * 用户最后一次登录时间'
	 */
	private Date lastLogin;

	/**
	 * 状态,0:正常，1:冻结
	 */
	private Integer status;

	/**
	 * 是否删除标识 1：是，0否
	 */
	private Integer isDel;

	/**
	 *  创建人
	 */
	private Long createdBy;

	/**
	 * 创建时间
	 */
	private Date createdDate;

	/**
	 * 更新人
	 */
	private Long updatedBy;

	/**
	 * 更新时间
	 */
	private Date updatedDate;
}
