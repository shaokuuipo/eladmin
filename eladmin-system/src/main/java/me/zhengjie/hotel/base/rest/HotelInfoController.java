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
package me.zhengjie.hotel.base.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.hotel.base.domain.HotelInfo;
import me.zhengjie.hotel.base.service.HotelInfoService;
import me.zhengjie.hotel.base.service.dto.HotelInfoQueryCriteria;
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
* @author shaonick
* @date 2021-02-14
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "酒店基本信息管理")
@RequestMapping("/api/hotelInfo")
public class HotelInfoController {

    private final HotelInfoService hotelInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('hotelInfo:list')")
    public void download(HttpServletResponse response, HotelInfoQueryCriteria criteria) throws IOException {
        hotelInfoService.download(hotelInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询酒店基本信息")
    @ApiOperation("查询酒店基本信息")
    @PreAuthorize("@el.check('hotelInfo:list')")
    public ResponseEntity<Object> query(HotelInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(hotelInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增酒店基本信息")
    @ApiOperation("新增酒店基本信息")
    @PreAuthorize("@el.check('hotelInfo:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody HotelInfo resources){
        return new ResponseEntity<>(hotelInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改酒店基本信息")
    @ApiOperation("修改酒店基本信息")
    @PreAuthorize("@el.check('hotelInfo:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody HotelInfo resources){
        hotelInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除酒店基本信息")
    @ApiOperation("删除酒店基本信息")
    @PreAuthorize("@el.check('hotelInfo:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        hotelInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}