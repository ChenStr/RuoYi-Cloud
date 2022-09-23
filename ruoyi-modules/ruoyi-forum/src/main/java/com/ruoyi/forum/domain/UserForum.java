package com.ruoyi.forum.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ruoyi.common.core.web.domain.BaseEntity;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 贴吧表 t_user_forum
 *
 * @author chen Zhenyang
 */
@Data
@ToString
@TableName("t_user_forum")
public class UserForum extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 参数主键 */
    private Long forumId;

    /** 贴吧名称 */
    @NotNull(message = "贴吧名称不能为空")
    @Size(min = 0, max = 32, message = "参数键值长度不能超过32个字符")
    private String forumName;

    /** 贴吧状态:0正常,1停用 */
    private String status;

}
