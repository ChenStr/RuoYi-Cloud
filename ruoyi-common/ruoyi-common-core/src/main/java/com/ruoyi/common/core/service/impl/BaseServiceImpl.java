package com.ruoyi.common.core.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.ruoyi.common.core.service.BaseService;

import java.util.Objects;

/**
 * 浮桥Service层 (方法基本围绕DTO)
 *
 * 用法：你可以将所有service中都要用到的方法并且返回 DTO 的方法写在这里
 *
 * @author Chen Zhenyang
 *
 * M 为 Dao 层
 *
 * T为Entity类型
 *
 * D为DTO类型
 */
public class BaseServiceImpl<M extends BaseMapper<T> , T , D> extends DeepServiceImpl<M,T> implements BaseService<T , D> {


}
