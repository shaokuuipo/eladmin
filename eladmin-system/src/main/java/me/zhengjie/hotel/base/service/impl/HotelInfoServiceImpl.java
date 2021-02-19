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
package me.zhengjie.hotel.base.service.impl;

import me.zhengjie.hotel.base.domain.HotelInfo;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.hotel.base.repository.HotelInfoRepository;
import me.zhengjie.hotel.base.service.HotelInfoService;
import me.zhengjie.hotel.base.service.dto.HotelInfoDto;
import me.zhengjie.hotel.base.service.dto.HotelInfoQueryCriteria;
import me.zhengjie.hotel.base.service.mapstruct.HotelInfoMapper;
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
* @author shaonick
* @date 2021-02-14
**/
@Service
@RequiredArgsConstructor
public class HotelInfoServiceImpl implements HotelInfoService {

    private final HotelInfoRepository hotelInfoRepository;
    private final HotelInfoMapper hotelInfoMapper;

    @Override
    public Map<String,Object> queryAll(HotelInfoQueryCriteria criteria, Pageable pageable){
        Page<HotelInfo> page = hotelInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(hotelInfoMapper::toDto));
    }

    @Override
    public List<HotelInfoDto> queryAll(HotelInfoQueryCriteria criteria){
        return hotelInfoMapper.toDto(hotelInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public HotelInfoDto findById(Long hotelId) {
        HotelInfo hotelInfo = hotelInfoRepository.findById(hotelId).orElseGet(HotelInfo::new);
        ValidationUtil.isNull(hotelInfo.getHotelId(),"HotelInfo","hotelId",hotelId);
        return hotelInfoMapper.toDto(hotelInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HotelInfoDto create(HotelInfo resources) {
        return hotelInfoMapper.toDto(hotelInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(HotelInfo resources) {
        HotelInfo hotelInfo = hotelInfoRepository.findById(resources.getHotelId()).orElseGet(HotelInfo::new);
        ValidationUtil.isNull( hotelInfo.getHotelId(),"HotelInfo","id",resources.getHotelId());
        hotelInfo.copy(resources);
        hotelInfoRepository.save(hotelInfo);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long hotelId : ids) {
            hotelInfoRepository.deleteById(hotelId);
        }
    }

    @Override
    public void download(List<HotelInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (HotelInfoDto hotelInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("组织机构id", hotelInfo.getDeptId());
            map.put("酒店名称", hotelInfo.getHotelName());
            map.put("开业日期", hotelInfo.getStartDate());
            map.put("酒店logo", hotelInfo.getLogo());
            map.put("酒店星级", hotelInfo.getStars());
            map.put("详细地址", hotelInfo.getAddress());
            map.put("地址坐标（经纬度）", hotelInfo.getLal());
            map.put("酒店电话", hotelInfo.getPhone());
            map.put("酒店负责人", hotelInfo.getLeaderId());
            map.put("酒店负责人姓名", hotelInfo.getLeaderName());
            map.put("系统管理员", hotelInfo.getAdminId());
            map.put("系统管理员姓名", hotelInfo.getAdminName());
            map.put("退订规则", hotelInfo.getUnsubscribeRule());
            map.put("温馨提示", hotelInfo.getWenxingtishi());
            map.put("酒店设施", hotelInfo.getJiudiansheshi());
            map.put("房间最低价格", hotelInfo.getZuidijiage());
            map.put("酒店政策", hotelInfo.getJiudianzhengce());
            map.put("酒店简介", hotelInfo.getJiudianjianjie());
            map.put("创建者", hotelInfo.getCreateBy());
            map.put("更新者", hotelInfo.getUpdateBy());
            map.put("创建时间", hotelInfo.getCreateTime());
            map.put("更新时间", hotelInfo.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}