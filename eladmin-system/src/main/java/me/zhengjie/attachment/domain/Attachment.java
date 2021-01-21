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
package me.zhengjie.attachment.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import javax.persistence.*;
import javax.validation.constraints.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import org.hibernate.annotations.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author NickShao
* @date 2021-01-20
**/
@Entity
@Data
@Table(name="pub_attachment")
public class Attachment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachment_id")
    @ApiModelProperty(value = "ID")
    private Long attachmentId;

    @Column(name = "real_name")
    @ApiModelProperty(value = "文件真实的名称")
    private String realName;

    @Column(name = "name")
    @ApiModelProperty(value = "文件名")
    private String name;

    @Column(name = "suffix")
    @ApiModelProperty(value = "后缀")
    private String suffix;

    @Column(name = "path")
    @ApiModelProperty(value = "相对路径")
    private String path;

    @Column(name = "type")
    @ApiModelProperty(value = "类型")
    private String type;

    @Column(name = "mime_type")
    @ApiModelProperty(value = "文件类型")
    private String mimeType;

    @Column(name = "size")
    @ApiModelProperty(value = "大小")
    private String size;

    @Column(name = "tag")
    @ApiModelProperty(value = "标签")
    private String tag;

    @Column(name = "business_table")
    @ApiModelProperty(value = "业务表")
    private String businessTable;

    @Column(name = "business_id")
    @ApiModelProperty(value = "业务id")
    private String businessId;

    @Column(name = "create_by")
    @ApiModelProperty(value = "创建者")
    private String createBy;

    @Column(name = "update_by")
    @ApiModelProperty(value = "更新者")
    private String updateBy;

    @Column(name = "create_time")
    @CreationTimestamp
    @ApiModelProperty(value = "创建日期")
    private Timestamp createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @Column(name = "real_size")
    @ApiModelProperty(value = "字节大小")
    private Long realSize;

    @Column(name = "ext_json")
    @ApiModelProperty(value = "扩展信息")
    private String extJson;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    public void copy(Attachment source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}