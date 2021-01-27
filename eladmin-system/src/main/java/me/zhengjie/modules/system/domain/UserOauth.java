/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.system.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author NickShao
* @date 2021-01-22
**/
@Entity
@Data
@Table(name="sys_user_oauth")
public class UserOauth implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "oauth_id")
    @ApiModelProperty(value = "ID")
    private Long oauthId;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户主键")
    private Long userId;

    @Column(name = "username")
    @ApiModelProperty(value = "用户名")
    private String username;

    @Column(name = "nickname")
    @ApiModelProperty(value = "昵称")
    private String nickname;

    @Column(name = "avatar")
    @ApiModelProperty(value = "头像")
    private String avatar;

    @Column(name = "blog")
    @ApiModelProperty(value = "应用主页")
    private String blog;

    @Column(name = "company")
    @ApiModelProperty(value = "公司名")
    private String company;

    @Column(name = "location")
    @ApiModelProperty(value = "地址")
    private String location;

    @Column(name = "email")
    @ApiModelProperty(value = "邮件")
    private String email;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Column(name = "gender")
    @ApiModelProperty(value = "性别")
    private String gender;

    @Column(name = "source")
    @ApiModelProperty(value = "来源")
    private String source;

    @Column(name = "uuid")
    @ApiModelProperty(value = "第三方系统用户ID")
    private String uuid;

    @Column(name = "unionid")
    @ApiModelProperty(value = "微信开放平台联合id")
    private String unionid;

    public void copy(UserOauth source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}