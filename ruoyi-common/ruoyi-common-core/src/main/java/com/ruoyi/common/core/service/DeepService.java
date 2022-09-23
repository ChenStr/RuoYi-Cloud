package com.ruoyi.common.core.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 最底层的Service层(方法基本围绕Entity) 继承了mybatis-plus中的方法
 *
 * 用法：你可以将所有service中都要用到的方法并且返回 ENTITY 的方法写在这里
 *
 * T为Entity类型
 *
 * @author Chen Zhenyang
 */
public interface DeepService<T> extends IService<T> {
}
