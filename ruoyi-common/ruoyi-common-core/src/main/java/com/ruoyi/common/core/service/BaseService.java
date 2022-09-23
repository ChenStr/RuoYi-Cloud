package com.ruoyi.common.core.service;

/**
 * 浮桥Service层 (方法基本围绕DTO)
 *
 * 用法：你可以将所有service中都要用到的方法并且返回 DTO 的方法写在这里
 *
 * @author Chen Zhenyang
 *
 * T为Entity类型
 *
 * D为DTO类型
 */
public interface BaseService<T,D> extends DeepService<T> {
}
