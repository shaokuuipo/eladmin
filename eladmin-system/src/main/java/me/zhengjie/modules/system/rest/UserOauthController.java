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
package me.zhengjie.modules.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.UserOauth;
import me.zhengjie.modules.system.service.UserOauthService;
import me.zhengjie.modules.system.service.dto.UserOauthQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author NickShao
* @date 2021-01-22
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "第三方登录信息管理")
@RequestMapping("/api/userOauth")
public class UserOauthController {

    private final UserOauthService userOauthService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('userOauth:list')")
    public void download(HttpServletResponse response, UserOauthQueryCriteria criteria) throws IOException {
        userOauthService.download(userOauthService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询第三方登录信息")
    @ApiOperation("查询第三方登录信息")
    @PreAuthorize("@el.check('userOauth:list')")
    public ResponseEntity<Object> query(UserOauthQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(userOauthService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增第三方登录信息")
    @ApiOperation("新增第三方登录信息")
    @PreAuthorize("@el.check('userOauth:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody UserOauth resources){
        return new ResponseEntity<>(userOauthService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改第三方登录信息")
    @ApiOperation("修改第三方登录信息")
    @PreAuthorize("@el.check('userOauth:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody UserOauth resources){
        userOauthService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除第三方登录信息")
    @ApiOperation("删除第三方登录信息")
    @PreAuthorize("@el.check('userOauth:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        userOauthService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}