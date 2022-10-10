package com.ruoyi.forum.controller;

import com.ruoyi.common.core.utils.StringUtils;
import com.ruoyi.common.core.utils.poi.ExcelUtil;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.core.web.domain.AjaxResult;
import com.ruoyi.common.core.web.page.TableDataInfo;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.security.annotation.RequiresPermissions;
import com.ruoyi.common.security.utils.SecurityUtils;
import com.ruoyi.forum.domain.UserForum;
import com.ruoyi.forum.service.UserForumService;
import com.ruoyi.system.api.domain.SysDictData;
import com.ruoyi.system.api.domain.SysDictType;
import com.ruoyi.system.api.domain.SysRole;
import com.ruoyi.system.api.domain.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 贴吧 信息操作处理
 *
 * @author Chen Zhenyang
 */
@RestController
@RequestMapping("/forum")
public class UserForumController extends BaseController {

    @Autowired
    UserForumService userForumService;

    /**
     * 获取贴吧列表
     */
    @RequiresPermissions("forum:forum:list")
    @GetMapping("/list")
    public TableDataInfo list(UserForum userForum)
    {
        startPage();
        List<UserForum> list = userForumService.selectList(userForum);
        return getDataTable(list);
    }

    /**
     * 修改贴吧状态
     */
    /**
     * 状态修改
     */
    @RequiresPermissions("forum:forum:edit")
    @Log(title = "贴吧管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody UserForum userForum)
    {
        return toAjax(userForumService.updateForumStatus(userForum));
    }

    /**
     * 新增贴吧
     */
    @RequiresPermissions("forum:forum:add")
    @Log(title = "贴吧管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody UserForum userForum)
    {
        return toAjax(userForumService.insertForum(userForum));
    }

    /**
     * 修改贴吧
     */
    @RequiresPermissions("forum:forum:edit")
    @Log(title = "贴吧管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody UserForum userForum)
    {
        return toAjax(userForumService.updateForum(userForum));
    }

    /**
     * 根据用户编号获取详细信息
     */
    @RequiresPermissions("forum:forum:query")
    @GetMapping(value = { "/", "/{forumId}" })
    public AjaxResult getInfo(@PathVariable(value = "forumId", required = false) Long forumId)
    {
        return AjaxResult.success(userForumService.selectUserForumById(forumId));
    }

    /**
     * 删除贴吧
     */
    @RequiresPermissions("forum:forum:remove")
    @Log(title = "贴吧管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{forumIds}")
    public AjaxResult remove(@PathVariable Long[] forumIds)
    {
        userForumService.deleteForumByIds(forumIds);
        return success();
    }

    /**
     * 贴吧信息导出 Excel
     * @param response
     * @param userForum
     */
    @RequiresPermissions("forum:forum:export")
    @Log(title = "贴吧管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserForum userForum)
    {
        List<UserForum> list = userForumService.selectList(userForum);
        ExcelUtil<UserForum> util = new ExcelUtil<>(UserForum.class);
        util.exportExcel(response, list, "贴吧数据");
    }

    /**
     * 下载贴吧数据模版
     * @param response
     * @throws IOException
     */
    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response) throws IOException
    {
        ExcelUtil<UserForum> util = new ExcelUtil<>(UserForum.class);
        util.importTemplateExcel(response, "贴吧数据");
    }

    /**
     * 导入数据
     * @param file
     * @param updateSupport
     * @return
     * @throws Exception
     */
    @Log(title = "贴吧管理", businessType = BusinessType.IMPORT)
    @RequiresPermissions("forum:forum:import")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<UserForum> util = new ExcelUtil<>(UserForum.class);
        List<UserForum> userForumList = util.importExcel(file.getInputStream());
        String operName = SecurityUtils.getUsername();
        String message = userForumService.importUserForum(userForumList, updateSupport, operName);
        return AjaxResult.success(message);
    }

}
