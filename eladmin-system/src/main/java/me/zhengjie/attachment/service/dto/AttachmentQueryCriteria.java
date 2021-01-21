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
package me.zhengjie.attachment.service.dto;

import lombok.Data;
import java.sql.Timestamp;
import java.util.List;
import me.zhengjie.annotation.Query;

/**
* @website https://el-admin.vip
* @author NickShao
* @date 2021-01-20
**/
@Data
public class AttachmentQueryCriteria{

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String realName;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String name;

    /** 精确 */
    @Query
    private String suffix;

    /** 精确 */
    @Query
    private String path;

    /** 精确 */
    @Query
    private String type;

    /** 精确 */
    @Query
    private String mimeType;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String tag;

    /** 精确 */
    @Query
    private String businessTable;

    /** 精确 */
    @Query
    private String businessId;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String extJson;

    /** 模糊 */
    @Query(type = Query.Type.INNER_LIKE)
    private String remark;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> updateTime;
    /** BETWEEN */
    @Query(type = Query.Type.BETWEEN)
    private List<Long> realSize;
}