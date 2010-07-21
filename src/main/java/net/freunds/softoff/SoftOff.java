package net.freunds.softoff;

public class SoftOff implements SoftOffMBean {
	private boolean enabled = true;

	public boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
