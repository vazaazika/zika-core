package br.les.opus.gamification.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import br.les.opus.gamification.services.ChallengeService;

@Configuration
@EnableScheduling
public class ScheduleChallengerChecker {
	@Autowired
	private ChallengeService service;


	public ScheduleChallengerChecker() {
		// TODO Auto-generated constructor stub
	}
	
	@Scheduled(fixedRate = 10000)
	public void checkOpenChallenges() {
		service.verifyOpenChallenges();
	}
	
	@Bean
	public TaskScheduler poolScheduler() {
		ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
		scheduler.setThreadNamePrefix("poolScheduler");
		
		return scheduler;
	}
	
	public static void main(String[] args) {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
				ScheduleChallengerChecker.class);
		
		try{
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			context.close();
		}
	}

}
