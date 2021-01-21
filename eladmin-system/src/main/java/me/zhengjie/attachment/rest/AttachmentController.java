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
package me.zhengjie.attachment.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.attachment.domain.Attachment;
import me.zhengjie.attachment.service.AttachmentService;
import me.zhengjie.attachment.service.dto.AttachmentQueryCriteria;
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
 * todo 通用附件实现
* @website https://el-admin.vip
* @author NickShao
* @date 2021-01-20
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "通用附件管理")
@RequestMapping("/api/attachment")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('attachment:list')")
    public void download(HttpServletResponse response, AttachmentQueryCriteria criteria) throws IOException {
        attachmentService.download(attachmentService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询通用附件")
    @ApiOperation("查询通用附件")
    @PreAuthorize("@el.check('attachment:list')")
    public ResponseEntity<Object> query(AttachmentQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(attachmentService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增通用附件")
    @ApiOperation("新增通用附件")
    @PreAuthorize("@el.check('attachment:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Attachment resources){
        return new ResponseEntity<>(attachmentService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改通用附件")
    @ApiOperation("修改通用附件")
    @PreAuthorize("@el.check('attachment:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Attachment resources){
        attachmentService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除通用附件")
    @ApiOperation("删除通用附件")
    @PreAuthorize("@el.check('attachment:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        attachmentService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}