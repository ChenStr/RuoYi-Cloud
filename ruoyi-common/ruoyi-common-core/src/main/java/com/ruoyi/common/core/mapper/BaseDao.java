package com.ruoyi.common.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 继承了 mybatis-plus的公共 mapper
 * 用处：如果你希望有方法能分配给所有的 mapper 就写在这里
 *
 * @author Chen Zhenyang
 *
 * T 为 ENTITY
 */
public interface BaseDao<T> extends BaseMapper<T> {
}
