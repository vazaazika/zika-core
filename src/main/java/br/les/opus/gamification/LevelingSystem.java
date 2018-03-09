package br.les.opus.gamification;

public class LevelingSystem {
	private static final Float CONSTANT = 0.1f;
	
	public static Integer computeLevel(Integer xp) {
		Double level = Math.sqrt(xp) * CONSTANT + 1;
		return level.intValue();
	}
	
	public static Integer requiredXp(Integer level) {
		float realLevel = level - 1;
		Double xp = Math.pow(realLevel/CONSTANT, 2);
		return (int)Math.ceil(xp);
	}
	
}
