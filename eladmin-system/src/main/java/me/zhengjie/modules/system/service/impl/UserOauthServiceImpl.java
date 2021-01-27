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
package me.zhengjie.modules.system.service.impl;

import lombok.RequiredArgsConstructor;
import me.zhengjie.modules.system.domain.UserOauth;
import me.zhengjie.modules.system.repository.UserOauthRepository;
import me.zhengjie.modules.system.service.UserOauthService;
import me.zhengjie.modules.system.service.dto.UserOauthDto;
import me.zhengjie.modules.system.service.dto.UserOauthQueryCriteria;
import me.zhengjie.modules.system.service.mapstruct.UserOauthMapper;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author NickShao
* @date 2021-01-22
**/
@Service
@RequiredArgsConstructor
public class UserOauthServiceImpl implements UserOauthService {

    private final UserOauthRepository userOauthRepository;
    private final UserOauthMapper userOauthMapper;

    @Override
    public Map<String,Object> queryAll(UserOauthQueryCriteria criteria, Pageable pageable){
        Page<UserOauth> page = userOauthRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(userOauthMapper::toDto));
    }

    @Override
    public List<UserOauthDto> queryAll(UserOauthQueryCriteria criteria){
        return userOauthMapper.toDto(userOauthRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public UserOauthDto findById(Long oauthId) {
        UserOauth userOauth = userOauthRepository.findById(oauthId).orElseGet(UserOauth::new);
        ValidationUtil.isNull(userOauth.getOauthId(),"UserOauth","oauthId",oauthId);
        return userOauthMapper.toDto(userOauth);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserOauthDto create(UserOauth resources) {
        return userOauthMapper.toDto(userOauthRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserOauth resources) {
        UserOauth userOauth = userOauthRepository.findById(resources.getOauthId()).orElseGet(UserOauth::new);
        ValidationUtil.isNull( userOauth.getOauthId(),"UserOauth","id",resources.getOauthId());
        userOauth.copy(resources);
        userOauthRepository.save(userOauth);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long oauthId : ids) {
            userOauthRepository.deleteById(oauthId);
        }
    }

    @Override
    public void download(List<UserOauthDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserOauthDto userOauth : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("用户主键", userOauth.getUserId());
            map.put("用户名", userOauth.getUsername());
            map.put("昵称", userOauth.getNickname());
            map.put("头像", userOauth.getAvatar());
            map.put("应用主页", userOauth.getBlog());
            map.put("公司名", userOauth.getCompany());
            map.put("地址", userOauth.getLocation());
            map.put("邮件", userOauth.getEmail());
            map.put("备注", userOauth.getRemark());
            map.put("性别", userOauth.getGender());
            map.put("来源", userOauth.getSource());
            map.put("第三方系统用户ID", userOauth.getUuid());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public UserOauthDto findBySourceAndUuid(String source, String uuid) {
        UserOauth userOauth = this.userOauthRepository.findBySourceAndUuid(source, uuid);
        return userOauthMapper.toDto(userOauth);
    }
}