package com.ruoyi.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.ruoyi.common.core.constant.UserConstants;
import com.ruoyi.common.core.enums.CommonEnum;
import com.ruoyi.common.core.exception.ServiceException;
import com.ruoyi.common.core.service.impl.BaseServiceImpl;
import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.bean.BeanValidators;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.forum.domain.UserForum;
import com.ruoyi.forum.mapper.UserForumMapper;
import com.ruoyi.forum.service.UserForumService;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Validator;
import java.util.*;

/**
 * 参数配置 服务层实现
 *
 * @author ruoyi
 */
@Service
public class UserForumServiceImpl extends BaseServiceImpl<UserForumMapper, UserForum , UserForum> implements UserForumService {

    @Autowired
    protected Validator validator;

    @Override
    public List<UserForum> selectList(UserForum userForum) {
        LambdaQueryWrapper lambdaQueryWrapper = this.getQueryWrapper(userForum);
        List<UserForum> list = this.list(lambdaQueryWrapper);
        return list;
    }


    public String checkDeptUnique(UserForum userForum) {
        LambdaQueryWrapper<UserForum> lambdaQueryWrapper = new LambdaQueryWrapper();
        if(!Objects.equals(userForum.getForumId(),null)) {
            UserForum userForum1 = this.getById(userForum.getForumId());
            lambdaQueryWrapper.eq(UserForum::getForumName,userForum1.getForumName());
        } else {
            lambdaQueryWrapper.eq(UserForum::getForumName,userForum.getForumName());
        }
        lambdaQueryWrapper.eq(UserForum::getStatus,CommonEnum.OK.getCode()).last("LIMIT 1");
        UserForum userForum1 = this.getOne(lambdaQueryWrapper);
        if (!Objects.equals(userForum1,null) && Objects.equals(userForum.getStatus(),CommonEnum.OK.getCode())
            && !Objects.equals(userForum1.getForumId(),userForum.getForumId())) {
            return UserConstants.NOT_UNIQUE;
        }
        return UserConstants.UNIQUE;
    }

    @Override
    public Boolean updateForumStatus(UserForum userForum) {
        if (Objects.equals(userForum.getForumId(),null) || Objects.equals(userForum.getStatus(),null)) {
            throw new ServiceException("必填参数不能为空");
        }
        if (Objects.equals(this.checkDeptUnique(userForum),"0")) {
            LambdaUpdateWrapper<UserForum> lambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            lambdaUpdateWrapper.eq(UserForum::getForumId, userForum.getForumId())
            .set(UserForum::getStatus, userForum.getStatus());
            return this.update(lambdaUpdateWrapper);
        } else {
            throw new ServiceException("贴吧名称重复，请修改后上架");
        }
    }

    @Override
    public Boolean insertForum(UserForum userForum) {
        if (Objects.equals(this.checkDeptUnique(userForum),"0")) {
            userForum.setCreateBy(SecurityUtils.getUsername());
            userForum.setCreateTime(new Date());
            return this.save(userForum);
        } else {
            throw new ServiceException("贴吧名称重复，请修改");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateForum(UserForum userForum) {
        if (Objects.equals(this.checkDeptUnique(userForum),"0")) {
            LambdaUpdateWrapper<UserForum> lambdaUpdateWrapper = getUpdateWrapper(userForum);
            return this.update(lambdaUpdateWrapper);
        } else {
            throw new ServiceException("贴吧名称重复，请修改");
        }
    }

    @Override
    public UserForum selectUserForumById(Long forumId) {
        UserForum userForum = this.getById(forumId);
        return userForum;
    }

    @Override
    public void deleteForumByIds(Long[] forumIds) {
        this.removeByIds(Arrays.asList(forumIds));
    }

    @Override
    public String importUserForum(List<UserForum> forumList, Boolean isUpdateSupport, String operName) {
        if (StringUtils.isNull(forumList) || forumList.size() == 0) {
            throw new ServiceException("导入用户数据不能为空！");
        }
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        for (UserForum forum : forumList) {
            try {
                // 验证是否已经存在
                String u = this.checkDeptUnique(forum);
                if (Objects.equals(u, "0")) {
                    {
                        // 不存在直接新增
                        BeanValidators.validateWithException(validator, forumList);
                        forum.setCreateBy(operName);
                        this.insertForum(forum);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、贴吧 " + forum.getForumName() + " 导入成功");
                    }
                }
                else if (isUpdateSupport) {
                        // 存在但是勾选了存在更新的选项，所以进行更新
                        BeanValidators.validateWithException(validator, forum);
                        forum.setUpdateBy(operName);
                        LambdaUpdateWrapper<UserForum> userForumLambdaUpdateWrapper = getUpdateWrapper(forum);
                        this.update(userForumLambdaUpdateWrapper);
                        successNum++;
                        successMsg.append("<br/>" + successNum + "、贴吧 " + forum.getForumName() + " 更新成功");
                    } else {
                        failureNum++;
                        failureMsg.append("<br/>" + failureNum + "、贴吧 " + forum.getForumName() + " 已存在");
                    }
                }
            catch(Exception e)
                {
                    failureNum++;
                    String msg = "<br/>" + failureNum + "、贴吧 " + forum.getForumName() + " 导入失败：";
                    failureMsg.append(msg + e.getMessage());
                    log.error(msg, e);
                }
            }
            if (failureNum > 0) {
                failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
                throw new ServiceException(failureMsg.toString());
            } else {
                successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
            }
            return successMsg.toString();
    }

    public LambdaUpdateWrapper getUpdateWrapper(UserForum dto) {
        LambdaUpdateWrapper<UserForum> lambdaUpdateWrapper = new LambdaUpdateWrapper();
        if (!Objects.equals(dto.getForumId(),null)) {
            lambdaUpdateWrapper.eq(UserForum::getForumId,dto.getForumId());
        } else {
            throw new ServiceException("请选择你要修改的数据");
        }
        if (!Objects.equals(dto.getCreateBy(),null)) {
            lambdaUpdateWrapper.set(UserForum::getCreateBy,dto.getCreateBy());
        }
        if (!Objects.equals(dto.getCreateTime(),null)) {
            lambdaUpdateWrapper.set(UserForum::getCreateTime,dto.getCreateTime());
        }
        if (!Objects.equals(dto.getUpdateBy(),null)) {
            lambdaUpdateWrapper.set(UserForum::getUpdateBy,dto.getUpdateBy());
        }
        if (!Objects.equals(dto.getUpdateTime(),null)) {
            lambdaUpdateWrapper.set(UserForum::getUpdateTime,dto.getUpdateTime());
        }
        if (!Objects.equals(dto.getRemark(),null)) {
            lambdaUpdateWrapper.set(UserForum::getRemark,dto.getRemark());
        }
        if (!Objects.equals(dto.getStatus(),null)) {
            lambdaUpdateWrapper.set(UserForum::getStatus,dto.getStatus());
        }
        lambdaUpdateWrapper.set(UserForum::getForumName,dto.getForumName());
        if (Objects.equals(dto.getUpdateBy(),null)) {
            lambdaUpdateWrapper.set(UserForum::getUpdateBy, SecurityUtils.getUsername());
        } else {
            lambdaUpdateWrapper.set(UserForum::getUpdateBy, dto.getUpdateBy());
        }
        lambdaUpdateWrapper.set(UserForum::getUpdateTime,new Date());
        return lambdaUpdateWrapper;
    }

    private LambdaQueryWrapper getQueryWrapper(UserForum dto) {
        LambdaQueryWrapper<UserForum> userForumLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!Objects.equals(dto.getForumName(), null)) {
            userForumLambdaQueryWrapper.like(UserForum::getForumName, dto.getForumName());
        }
        if (!Objects.equals(dto.getStatus(), null)) {
            userForumLambdaQueryWrapper.eq(UserForum::getStatus, dto.getStatus());
        }
        // 判断param参数中是否有指定的key
        Map<String, Object> map = dto.getParams();
        if (map.containsKey("beginTime")) {
            userForumLambdaQueryWrapper.gt(UserForum::getCreateTime, map.get("beginTime"));
        }
        if (map.containsKey("endTime")) {
            userForumLambdaQueryWrapper.lt(UserForum::getCreateTime, map.get("endTime"));
        }
        return userForumLambdaQueryWrapper;
    }
}
