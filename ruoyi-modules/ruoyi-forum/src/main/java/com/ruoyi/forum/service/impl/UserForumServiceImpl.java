package com.ruoyi.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.enums.CommonEnum;
import com.ruoyi.common.core.service.impl.BaseServiceImpl;
import com.ruoyi.forum.domain.UserForum;
import com.ruoyi.forum.mapper.UserForumMapper;
import com.ruoyi.forum.service.UserForumService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 参数配置 服务层实现
 *
 * @author ruoyi
 */
@Service
public class UserForumServiceImpl extends BaseServiceImpl<UserForumMapper, UserForum , UserForum> implements UserForumService {

    @Override
    public List<UserForum> selectList(UserForum userForum) {
        LambdaQueryWrapper lambdaQueryWrapper = getQueryWrapper(userForum);
        List<UserForum> list = this.list(lambdaQueryWrapper);
        return list;
    }

    private LambdaQueryWrapper getQueryWrapper(UserForum dto){
        LambdaQueryWrapper<UserForum> userForumLambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (!Objects.equals(dto.getForumName(),null)) {
            userForumLambdaQueryWrapper.like(UserForum::getForumName,dto.getForumName());
        }
        if (!Objects.equals(dto.getStatus(),null)) {
            userForumLambdaQueryWrapper.eq(UserForum::getStatus,dto.getStatus());
        } else  {
            // 默认查询没有被停用的
            userForumLambdaQueryWrapper.eq(UserForum::getStatus, CommonEnum.OK.getCode());
        }
        return userForumLambdaQueryWrapper;
    }
}
