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
package me.zhengjie.hotel.base.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author shaonick
* @date 2021-02-14
**/
@Data
public class HotelInfoDto implements Serializable {

    private Long hotelId;

    /** 组织机构id */
    private Long deptId;

    /** 酒店名称 */
    private String hotelName;

    /** 开业日期 */
    private Timestamp startDate;

    /** 酒店logo */
    private String logo;

    /** 酒店星级 */
    private Integer stars;

    /** 详细地址 */
    private String address;

    /** 地址坐标（经纬度） */
    private String lal;

    /** 酒店电话 */
    private String phone;

    /** 酒店负责人 */
    private Long leaderId;

    /** 酒店负责人姓名 */
    private String leaderName;

    /** 系统管理员 */
    private Long adminId;

    /** 系统管理员姓名 */
    private String adminName;

    /** 退订规则 */
    private String unsubscribeRule;

    /** 温馨提示 */
    private String wenxingtishi;

    /** 酒店设施 */
    private String jiudiansheshi;

    /** 房间最低价格 */
    private BigDecimal zuidijiage;

    /** 酒店政策 */
    private String jiudianzhengce;

    /** 酒店简介 */
    private String jiudianjianjie;

    /** 创建者 */
    private String createBy;

    /** 更新者 */
    private String updateBy;

    /** 创建时间 */
    private Timestamp createTime;

    /** 更新时间 */
    private Timestamp updateTime;
}