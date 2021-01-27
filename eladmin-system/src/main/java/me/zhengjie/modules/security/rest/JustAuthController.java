package me.zhengjie.modules.security.rest;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
import com.xkcoding.justauth.AuthRequestFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.zhengjie.annotation.rest.AnonymousGetMapping;
import me.zhengjie.annotation.rest.AnonymousPostMapping;
import me.zhengjie.exception.BadRequestException;
import me.zhengjie.modules.security.config.bean.LoginProperties;
import me.zhengjie.modules.security.config.bean.SecurityProperties;
import me.zhengjie.modules.security.security.TokenProvider;
import me.zhengjie.modules.security.service.OnlineUserService;
import me.zhengjie.modules.security.service.dto.JwtUserDto;
import me.zhengjie.modules.system.domain.UserOauth;
import me.zhengjie.modules.system.service.UserOauthService;
import me.zhengjie.modules.system.service.UserService;
import me.zhengjie.modules.system.service.dto.UserDto;
import me.zhengjie.modules.system.service.dto.UserOauthDto;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import me.zhyd.oauth.utils.AuthStateUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/auth/social")
@RequiredArgsConstructor
@Api(tags = "系统：集成JustAuth第三方登录工具")
public class JustAuthController {
    private final AuthRequestFactory factory;
    private final UserDetailsService userDetailsService;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final OnlineUserService onlineUserService;
    private final SecurityProperties properties;
    private final UserOauthService userOauthService;
    private final UserService userService;
    @Resource
    private LoginProperties loginProperties;

    @GetMapping
    public List<String> list() {
        return factory.oauthList();
    }

    @AnonymousGetMapping("/login/{type}")
    public void login(@PathVariable String type, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = factory.get(type);
        String state = AuthStateUtils.createState();
        if (type.toUpperCase().equals("WECHAT_MINI"))//集成了半天发现JustAuth不支持微信小程序。只能单独写一个了。微信小程序在登录前先获取state。
            response.getWriter().write(state);
        else
            response.sendRedirect(authRequest.authorize(state));
    }

    @ApiOperation("第三方登录授权")
    @AnonymousPostMapping("/callback/{type}")
    public ResponseEntity<Object> login(@PathVariable String type, @RequestBody AuthCallback callback, HttpServletRequest request) {
        AuthRequest authRequest = factory.get(type);
        AuthResponse response = authRequest.login(callback);

        AuthUser authUser;//第三方登录返回的用户信息
        if (response.ok()) {
            authUser = (AuthUser) response.getData();
        } else {
            throw new BadRequestException("登录失败！" + response.getMsg());
        }
        String source = authUser.getSource();
        String uuid = authUser.getUuid();
        UserOauthDto userAuth = userOauthService.findBySourceAndUuid(source, uuid);
        if (userAuth == null) {
            //第三方登录信息为空，新建第三方登录信息
            UserOauth newUserOauth = BeanUtil.copyProperties(authUser, UserOauth.class);
            userAuth = userOauthService.create(newUserOauth);
        }

        Long userId = userAuth.getUserId();

        if (ObjectUtil.isNull(userId)) {
            //用户id为空，还没有绑定系统用户。返回第三方系统id和需要绑定的消息给客户端，让用户选择是自动生成账户，还是绑定已有账户
            Map<String, Object> authInfo = new HashMap();
            authInfo.put("oauthId", userAuth.getOauthId());
            authInfo.put("toBind", true);
            String state = AuthStateUtils.createState();
            authInfo.put("authState", state);
            return ResponseEntity.ok(authInfo);
        }

        UserDto userInfo = userService.findById(userId);
        if (userInfo == null) {
            throw new BadRequestException("登录失败！绑定的用户不存在！");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(userInfo.getUsername());

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // 保存在线信息
        onlineUserService.save(jwtUserDto, token, request);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};
        if (loginProperties.isSingleLogin()) {
            //踢掉之前已经登录的token
            onlineUserService.checkLoginOnUser(userDetails.getUsername(), token);
        }

//        log.info("【response】= {}", JSONUtil.toJsonStr(response));
        return ResponseEntity.ok(authInfo);
    }

}
