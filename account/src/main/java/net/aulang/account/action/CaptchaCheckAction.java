package net.aulang.account.action;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import net.aulang.account.authentication.AccountCredential;
import org.apereo.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpSession;

/**
 * 验证码Action
 */
@Component
public class CaptchaCheckAction extends AbstractAction {
	private static final Logger logger = LoggerFactory.getLogger(CaptchaCheckAction.class);
	
	@Autowired
	private DefaultKaptcha kaptcha;

	@Override
	protected Event doExecute(RequestContext context) throws Exception {
		try {
			HttpSession session = WebUtils.getHttpServletRequest(context).getSession();
			Integer loginFailedTimes = (Integer) session.getAttribute("loginFailedTimes");
			if (loginFailedTimes == null) {
				loginFailedTimes = 0;
				session.setAttribute("loginFailedTimes", loginFailedTimes);
			}

			if (loginFailedTimes > 2) {
				AccountCredential credential = (AccountCredential) WebUtils.getCredential(context);
				String captcha = credential.getCaptcha();
				credential.setCaptcha(null);
				String sessionCaptcha = (String) session.getAttribute(kaptcha.getConfig().getSessionKey());
				if (sessionCaptcha == null || sessionCaptcha.equals(captcha)) {
					return success();
				}
				MessageContext messageContext = context.getMessageContext();
				messageContext.addMessage(new MessageBuilder().error().code("captchaError").build());
				return error();
			}
			session.setAttribute("loginFailedTimes", ++loginFailedTimes);
		} catch (Exception e) {
			logger.error("验证码检查出错！", e);
		}
		return success();
	}
}
