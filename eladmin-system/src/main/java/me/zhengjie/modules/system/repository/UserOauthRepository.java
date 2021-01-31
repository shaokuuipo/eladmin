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
package me.zhengjie.modules.system.repository;

import me.zhengjie.modules.system.domain.UserOauth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

/**
 * @author NickShao
 * @website https://el-admin.vip
 * @date 2021-01-22
 **/
public interface UserOauthRepository extends JpaRepository<UserOauth, Long>, JpaSpecificationExecutor<UserOauth> {
    UserOauth findBySourceAndUuid(String source, String uuid);

    @Modifying
    @Query( value = "update UserOauth uo set uo.userId = ?1 where uo.oauthId = ?2")
    void bindUser(Long userId, Long authId);
}