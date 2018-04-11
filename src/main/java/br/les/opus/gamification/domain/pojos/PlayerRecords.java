package br.les.opus.gamification.domain.pojos;

public class PlayerRecords {
	private Long todayXp;
	
	private Long totalXp;
	
	private DailyRecord bestDayRecord;

	public Long getTodayXp() {
		return todayXp;
	}

	public void setTodayXp(Long todayXp) {
		this.todayXp = todayXp;
	}

	public Long getTotalXp() {
		return totalXp;
	}

	public void setTotalXp(Long totalXp) {
		this.totalXp = totalXp;
	}

	public DailyRecord getBestDayRecord() {
		return bestDayRecord;
	}

	public void setBestDayRecord(DailyRecord bestDayRecord) {
		this.bestDayRecord = bestDayRecord;
	}
}
