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
package me.zhengjie.hotel.base.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.zhengjie.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
* @website https://el-admin.vip
* @description /
* @author shaonick
* @date 2021-02-14
**/
@Entity
@Data
@Table(name="hotel_info")
public class HotelInfo extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hotel_id")
    @ApiModelProperty(value = "hotelId")
    private Long hotelId;

    @Column(name = "dept_id")
    @ApiModelProperty(value = "组织机构id")
    private Long deptId;

    @Column(name = "hotel_name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "酒店名称")
    private String hotelName;

    @Column(name = "start_date")
    @ApiModelProperty(value = "开业日期")
    private Timestamp startDate;

    @Column(name = "logo",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "酒店logo")
    private String logo;

    @Column(name = "stars",nullable = false)
    @NotNull
    @ApiModelProperty(value = "酒店星级")
    private Integer stars;

    @Column(name = "address",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "详细地址")
    private String address;

    @Column(name = "lal")
    @ApiModelProperty(value = "地址坐标（经纬度）")
    private String lal;

    @Column(name = "phone",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "酒店电话")
    private String phone;

    @Column(name = "leader_id")
    @ApiModelProperty(value = "酒店负责人")
    private Long leaderId;

    @Column(name = "leader_name")
    @ApiModelProperty(value = "酒店负责人姓名")
    private String leaderName;

    @Column(name = "admin_id")
    @ApiModelProperty(value = "系统管理员")
    private Long adminId;

    @Column(name = "admin_name")
    @ApiModelProperty(value = "系统管理员姓名")
    private String adminName;

    @Column(name = "unsubscribe_rule")
    @ApiModelProperty(value = "退订规则")
    private String unsubscribeRule;

    @Column(name = "wenxingtishi")
    @ApiModelProperty(value = "温馨提示")
    private String wenxingtishi;

    @Column(name = "jiudiansheshi")
    @ApiModelProperty(value = "酒店设施")
    private String jiudiansheshi;

    @Column(name = "zuidijiage")
    @ApiModelProperty(value = "房间最低价格")
    private BigDecimal zuidijiage;

    @Column(name = "jiudianzhengce")
    @ApiModelProperty(value = "酒店政策")
    private String jiudianzhengce;

    @Column(name = "jiudianjianjie")
    @ApiModelProperty(value = "酒店简介")
    private String jiudianjianjie;


    public void copy(HotelInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}