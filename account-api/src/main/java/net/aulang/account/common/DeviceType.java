package net.aulang.account.common;

/**
 * 设备类型
 * 
 * @author Aulang
 *
 */
public enum DeviceType {
	PC("计算机"), Mobile("手机");

	private final String name;

	public String getName() {
		return name;
	}

	private DeviceType(String name) {
		this.name = name;
	}
}
