package net.aulang.account.restcontroller;

import net.aulang.account.manage.AccountBiz;
import net.aulang.account.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/account")
public class AccountRestController {
    @Autowired
    private AccountBiz accountBiz;

    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public UserInfo me() {
        accountBiz.get("");


        return null;
    }
}
