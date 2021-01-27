package me.zhengjie.justauth;

import me.zhyd.oauth.config.AuthSource;

public enum AuthWechatMiniSource implements AuthSource {
    /**
     * WECHAT_MINI
     */
    WECHAT_MINI {
        @Override
        public String authorize() {
            return "";
        }

        @Override
        public String accessToken() {
            return "https://api.weixin.qq.com/sns/jscode2session";
        }

        @Override
        public String userInfo() {
            return "https://api.github.com/user";
        }
    },
}
