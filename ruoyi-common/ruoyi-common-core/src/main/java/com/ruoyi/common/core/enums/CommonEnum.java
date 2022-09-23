package com.ruoyi.common.core.enums;


import lombok.Getter;

/**
 * 公共的枚举类
 *
 * @author Chen Zhenyang
 */
@Getter
public enum CommonEnum {

    OK("0", "正常"), DISABLE("1", "停用");

    private final String code;
    private final String info;

    CommonEnum(String code, String info)
    {
        this.code = code;
        this.info = info;
    }

}
