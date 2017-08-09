package net.aulang.account.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

@ConfigurationProperties
public class CaptchaProperties {
    private Properties captcha = new Properties();

    public Properties getCaptcha() {
        return captcha;
    }

    public void setCaptcha(Properties captcha) {
        this.captcha = captcha;
    }
}
