package com.ruoyi.common.core.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.service.DeepService;

/**
 * 最底层的Service层(方法基本围绕Entity) 继承了mybatis-plus中的方法
 *
 * 用法：你可以将所有service中都要用到的方法并且返回 ENTITY 的方法写在这里
 *
 * M 为 Dao 层
 *
 * T 为 Entity 类型
 *
 * @author Chen Zhenyang
 */
public class DeepServiceImpl<M extends BaseMapper<T>,T> extends ServiceImpl<M,T> implements DeepService<T> {
}
