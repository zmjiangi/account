package net.aulang.account.restcontroller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import net.aulang.account.manage.AccountBiz;
import net.aulang.account.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitialRestController {
    @Autowired
    private AccountBiz accountBiz;

    @ApiOperation("初始化用户信息")
    @ApiImplicitParam(name = "name", value = "初始化账号名", required = true, paramType = "query", dataType = "String")
    @ApiResponse(code = 200, message = "success：成功；fail：失败")
    @GetMapping(path = "/inituser", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
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

}
