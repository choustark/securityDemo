package com.chou.securityDemo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chou.securityDemo.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author Chou
 * @Description TODO
 * @ClassName UserMapper
 * @Date 2023/12/30 19:57
 * @Version 1.0
 **/
@Mapper
public interface UserMapper  extends BaseMapper<User> {

	/**
	 * 相同名字的用户
	 * @param name 姓名
	 * @return 个数
	 */
	Integer alikeUserNameCount(@Param("name") String name);

}
