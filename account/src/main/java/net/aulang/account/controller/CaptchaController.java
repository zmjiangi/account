package net.aulang.account.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

/**
 * 验证码
 */
@Controller
public class CaptchaController {
    @Autowired
    private DefaultKaptcha kaptcha;

    /**
     * 验证码
     */
    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        String capText = kaptcha.createText();
        request.getSession().setAttribute(kaptcha.getConfig().getSessionKey(), capText);
        request.getSession().setAttribute(kaptcha.getConfig().getSessionDate(), new Date());

        BufferedImage bi = kaptcha.createImage(capText);
        try {
            ImageIO.write(bi, "jpg", response.getOutputStream());
        } catch (IOException e) {
        }
    }

}
