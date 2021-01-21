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
package me.zhengjie.attachment.service.impl;

import me.zhengjie.attachment.domain.Attachment;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.attachment.repository.AttachmentRepository;
import me.zhengjie.attachment.service.AttachmentService;
import me.zhengjie.attachment.service.dto.AttachmentDto;
import me.zhengjie.attachment.service.dto.AttachmentQueryCriteria;
import me.zhengjie.attachment.service.mapstruct.AttachmentMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author NickShao
* @date 2021-01-20
**/
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;
    private final AttachmentMapper attachmentMapper;

    @Override
    public Map<String,Object> queryAll(AttachmentQueryCriteria criteria, Pageable pageable){
        Page<Attachment> page = attachmentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(attachmentMapper::toDto));
    }

    @Override
    public List<AttachmentDto> queryAll(AttachmentQueryCriteria criteria){
        return attachmentMapper.toDto(attachmentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public AttachmentDto findById(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElseGet(Attachment::new);
        ValidationUtil.isNull(attachment.getAttachmentId(),"Attachment","attachmentId",attachmentId);
        return attachmentMapper.toDto(attachment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AttachmentDto create(Attachment resources) {
        return attachmentMapper.toDto(attachmentRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Attachment resources) {
        Attachment attachment = attachmentRepository.findById(resources.getAttachmentId()).orElseGet(Attachment::new);
        ValidationUtil.isNull( attachment.getAttachmentId(),"Attachment","id",resources.getAttachmentId());
        attachment.copy(resources);
        attachmentRepository.save(attachment);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long attachmentId : ids) {
            attachmentRepository.deleteById(attachmentId);
        }
    }

    @Override
    public void download(List<AttachmentDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (AttachmentDto attachment : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("文件真实的名称", attachment.getRealName());
            map.put("文件名", attachment.getName());
            map.put("后缀", attachment.getSuffix());
            map.put("相对路径", attachment.getPath());
            map.put("类型", attachment.getType());
            map.put("文件类型", attachment.getMimeType());
            map.put("大小", attachment.getSize());
            map.put("标签", attachment.getTag());
            map.put("业务表", attachment.getBusinessTable());
            map.put("业务id", attachment.getBusinessId());
            map.put("创建者", attachment.getCreateBy());
            map.put("更新者", attachment.getUpdateBy());
            map.put("创建日期", attachment.getCreateTime());
            map.put("更新时间", attachment.getUpdateTime());
            map.put("字节大小", attachment.getRealSize());
            map.put("扩展信息", attachment.getExtJson());
            map.put("备注", attachment.getRemark());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}