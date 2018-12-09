package br.les.opus.gamification.domain;

public class XPInfo {
	private Long xp;
	private Badge badge;

	public XPInfo() {
		// TODO Auto-generated constructor stub
		xp = 0L;
		badge = null;
	}

	public Long getXp() {
		return xp;
	}

	public void setXp(Long xp) {
		this.xp = xp;
	}

	public Badge getBadge() {
		return badge;
	}

	public void setBadge(Badge badge) {
		this.badge = badge;
	}
	
	

}
