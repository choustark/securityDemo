package com.chou.securityDemo.inf.enums;

import lombok.Getter;

/**
 * 用户性别枚举 性别 0:女士,1:男士,2 未知
 * @author Chou
 * @since 2024年5月19日12:13:43
 */
@Getter
public enum GenderTypeEnum {
    MAN("男士",1),
    WOMAN("女士",0),
    UN_KNOW("未知",2)
    ;
    private String text;
    private Integer value;

    GenderTypeEnum(String text,Integer value){
        this.text = text;
        this.value = value;
    }


}
