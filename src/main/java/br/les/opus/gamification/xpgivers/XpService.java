package br.les.opus.gamification.xpgivers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.interceptors.PlayerLocalStorage;

@Service
public class XpService {
	
	@Autowired
	private ApplicationContext context;
	
	public GivenXP computeGivenXp(PerformedTask performedTask) {
		PlayerLocalStorage storage = PlayerLocalStorage.getInstance();
		List<Object> arguments = storage.getArguments();
		XpRule rule = (XpRule)context.getBean(performedTask.getTask().getXpRuleBeanName());
		return rule.xpFor(performedTask, arguments);
	}
}
