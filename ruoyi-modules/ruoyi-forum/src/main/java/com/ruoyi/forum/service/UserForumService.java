package com.ruoyi.forum.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.ruoyi.common.core.service.BaseService;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.forum.domain.UserForum;
import com.ruoyi.forum.mapper.UserForumMapper;

import java.util.List;

/**
 * 贴吧 服务层
 *
 * @author ruoyi
 */
public interface UserForumService extends BaseService<UserForum, UserForum> {

    /**
     * 根据条件分页查询字典数据
     *
     * @param userForum 贴吧数据信息
     * @return 贴吧数据集合信息
     */
    public List<UserForum> selectList(UserForum userForum);

}
