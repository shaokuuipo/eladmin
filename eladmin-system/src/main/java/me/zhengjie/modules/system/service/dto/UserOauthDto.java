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
package me.zhengjie.modules.system.service.dto;

import lombok.Data;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author NickShao
* @date 2021-01-22
**/
@Data
public class UserOauthDto implements Serializable {

    /** ID */
    private Long oauthId;

    /** 用户主键 */
    private Long userId;

    /** 用户名 */
    private String username;

    /** 昵称 */
    private String nickname;

    /** 头像 */
    private String avatar;

    /** 应用主页 */
    private String blog;

    /** 公司名 */
    private String company;

    /** 地址 */
    private String location;

    /** 邮件 */
    private String email;

    /** 备注 */
    private String remark;

    /** 性别 */
    private String gender;

    /** 来源 */
    private String source;

    /** 第三方系统用户ID */
    private String uuid;

    /** 微信开放平台联合id */
    private String unionid;
}