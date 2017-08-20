package net.aulang.account.action;

import net.aulang.account.authentication.AccountCredential;
import net.aulang.account.authentication.PasswordChangeBean;
import net.aulang.account.manage.AccountBiz;
import net.aulang.account.model.Account;
import org.apereo.cas.web.support.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.stereotype.Component;
import org.springframework.webflow.action.AbstractAction;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.execution.RequestContext;

@Component
public class PasswordChangeAction extends AbstractAction {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordChangeAction.class);

    @Autowired
    private AccountBiz accountBiz;

    @Override
    protected Event doExecute(RequestContext context) throws Exception {
        try {
            AccountCredential credential = (AccountCredential) WebUtils.getCredential(context);
            PasswordChangeBean bean = context.getFlowScope().get("password", PasswordChangeBean.class);

            Account account = accountBiz.changePassword(credential.getId(), bean.getPassword());
            if (account != null) {
                return success();
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        context.getMessageContext().addMessage(new MessageBuilder().error().code("pm.updateFailure")
                .defaultText("修改密码失败！").build());
        return error();
    }
}
