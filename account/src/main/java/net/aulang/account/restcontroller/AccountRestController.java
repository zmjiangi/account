package net.aulang.account.restcontroller;

import net.aulang.account.manage.AccountBiz;
import net.aulang.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountRestController {
    @Autowired
    private AccountBiz accountBiz;

    @GetMapping(path = "/init", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String init(String name) {
        if ("aulang".equals(name)) {
            long count = accountBiz.count();
            if (count == 0) {
                Account account = new Account();
                account.setUsername("aulang");
                account.setEmail("aulang@qq.com");
                account.setMobile("17620157736");
                account.setPassword("aulang88");
                account.setNickname("Aulang");
                account = accountBiz.register(account);
                if (account != null) {
                    return "success";
                }
            }
        }
        return "fail";
    }

    @GetMapping(path = "/{name}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Account> getByName(@PathVariable String name) {
        Account account = accountBiz.getByLoginName(name);

        if (account == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(account);
        }
    }

}
