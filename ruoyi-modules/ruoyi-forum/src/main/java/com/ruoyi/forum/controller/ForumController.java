package com.ruoyi.forum.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.forum.domain.UserForum;
import com.ruoyi.forum.service.UserForumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 贴吧 信息操作处理
 *
 * @author Chen Zhenyang
 */
@RestController
@RequestMapping("/forum")
public class ForumController extends BaseController {

    @Autowired
    UserForumService userForumService;

    /**
     * 获取贴吧列表
     */
//    @RequiresPermissions("system:config:list")
    @GetMapping("/list")
    public TableDataInfo list(UserForum userForum)
    {
        startPage();
        List<UserForum> list = userForumService.selectList(userForum);
        return getDataTable(list);
    }

}
