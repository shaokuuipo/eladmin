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
package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.UserOauth;
import me.zhengjie.modules.system.service.dto.UserOauthDto;
import me.zhengjie.modules.system.service.dto.UserOauthQueryCriteria;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author NickShao
* @date 2021-01-22
**/
public interface UserOauthService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(UserOauthQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<UserOauthDto>
    */
    List<UserOauthDto> queryAll(UserOauthQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param oauthId ID
     * @return UserOauthDto
     */
    UserOauthDto findById(Long oauthId);
    /**
     * 根据第三方系统来源和第三方系统唯一标识查询
     * @param source 第三方系统来源
     * @param uuid 第三方系统唯一标识
     * @return UserOauthDto
     */
    UserOauthDto findBySourceAndUuid(String source, String uuid);

    /**
    * 创建
    * @param resources /
    * @return UserOauthDto
    */
    UserOauthDto create(UserOauth resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(UserOauth resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<UserOauthDto> all, HttpServletResponse response) throws IOException;
}