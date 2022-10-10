package com.ruoyi.forum.service;


import com.ruoyi.common.core.service.BaseService;
import com.ruoyi.forum.domain.UserForum;
import com.ruoyi.system.api.domain.SysUser;

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

    /**
     * 判断贴吧名在数据库中是否重复
     * @param userForum
     * @return
     */
    public String checkDeptUnique(UserForum userForum);

    /**
     * 修改贴吧状态
     * @param userForum
     * @return
     */
    public Boolean updateForumStatus(UserForum userForum);

    /**
     * 新增贴吧
     */
    public Boolean insertForum(UserForum userForum);

    /**
     * 修改贴吧
     */
    public Boolean updateForum(UserForum userForum);

    /**
     * 根据贴吧id查询贴吧详细信息
     */
    public UserForum selectUserForumById(Long forumId);

    /**
     * 批量删除贴吧信息
     *
     * @param forumIds 需要删除的字典ID
     */
    public void deleteForumByIds(Long[] forumIds);

    /**
     * 导入用户数据
     *
     * @param forumList 贴吧数据列表
     * @param isUpdateSupport 是否更新支持，如果已存在，则进行更新数据
     * @param operName 操作用户
     * @return 结果
     */
    public String importUserForum(List<UserForum> forumList, Boolean isUpdateSupport, String operName);

}
