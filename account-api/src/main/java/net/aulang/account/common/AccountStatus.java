package net.aulang.account.common;

public enum AccountStatus {
    Deleted(-1, "删除"),
    Disabled(0, "禁用"),
    Enabled(1, "可用");

    public final int state;
    public final String name;

    AccountStatus(int state, String name) {
        this.state = state;
        this.name = name;
    }
}
