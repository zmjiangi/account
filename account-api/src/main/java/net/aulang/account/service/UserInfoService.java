package net.aulang.account.service;

import net.aulang.account.model.UserInfo;

/**
 * 账号服务
 *
 * @author Aulang
 */
public interface UserInfoService {
    UserInfo get(String accountId);
}
