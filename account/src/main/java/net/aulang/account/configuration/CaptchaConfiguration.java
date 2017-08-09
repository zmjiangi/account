package net.aulang.account.configuration;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(CaptchaProperties.class)
public class CaptchaConfiguration {
    @Autowired
    private CaptchaProperties captchaProperties;

    @Bean
    public DefaultKaptcha kaptcha() {
        DefaultKaptcha kaptcha = new DefaultKaptcha();

        Config config = new Config(captchaProperties.getCaptcha());

        kaptcha.setConfig(config);

        return kaptcha;
    }
}
