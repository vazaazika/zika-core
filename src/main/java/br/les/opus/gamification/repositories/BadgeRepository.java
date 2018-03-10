package br.les.opus.gamification.repositories;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Badge;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskGroupProgression;

@Repository
public class BadgeRepository extends HibernateAbstractRepository<Badge, Long>{

	@SuppressWarnings("unchecked")
	public List<Badge> findAllWithProgressions(Player player) {
		StringBuilder builder = new StringBuilder();
		builder.append("from Badge b ");
		builder.append("  left join b.taskGroup.progressions progs");
		builder.append("  left join progs.player p with p.id = :pId");
		
		Query query = getSession().createQuery(builder.toString());
		query.setParameter("pId", player.getId());
		List<Object[]> badgesAndProgressions = query.list();
		List<Badge> badges = new ArrayList<>();
		for (Object[] objects : badgesAndProgressions) {
			Badge badge = (Badge)objects[0];
			if (objects[1] != null) {
				TaskGroupProgression progression = (TaskGroupProgression)objects[1];
				badge.setCompletion(progression.getProgress());
			}
			badges.add(badge);
		}
		return badges;
	}
}
