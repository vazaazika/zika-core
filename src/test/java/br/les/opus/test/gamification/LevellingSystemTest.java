package br.les.opus.test.gamification;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import br.les.opus.gamification.LevelingSystem;

@RunWith(value = Parameterized.class)
public class LevellingSystemTest {
	@Parameter(value = 0)
	public Integer requiredXP;
	
	@Parameter(value = 1)
	public Integer level;
	
	
	@Parameters(name = "{index}: computeLevelTest({0} = {1})")
	public static Collection<Integer[]> data(){
		return Arrays.asList(new Integer[][] {
			{0,1},
			{100,2},
			{400,3},
			{900,4},
			{1600,5},
			{2500,6},
			{3600,7},
			{4900,8},
			{6400,9},
			{8100,10},
			{10000,11},
			{12100,12},
			{14400,13},
			{16900,14},
			{19600,15},
			{22500,16},
			{25600,17},
			{28900,18},
			{32400,19},
			{36100,20},
			{40000,21}
		});
	}
	
	@Test
	public void computeLevelTest() {
		Assert.assertEquals(level,LevelingSystem.computeLevel(requiredXP));
	}
	
	@Test
	public void requiredXPTest() {
		Assert.assertEquals(requiredXP, LevelingSystem.requiredXp(level));
	}




}
