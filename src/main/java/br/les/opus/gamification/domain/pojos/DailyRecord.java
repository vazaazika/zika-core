package br.les.opus.gamification.domain.pojos;

import java.util.Date;

public class DailyRecord {
	
	private Date day;
	
	private Integer xp;

	public Date getDay() {
		return day;
	}

	public void setDay(Date day) {
		this.day = day;
	}

	public Integer getXp() {
		return xp;
	}

	public void setXp(Integer xp) {
		this.xp = xp;
	}
}
