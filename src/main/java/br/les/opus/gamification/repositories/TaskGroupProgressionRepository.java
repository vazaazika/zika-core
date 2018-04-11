package br.les.opus.gamification.repositories;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskGroup;
import br.les.opus.gamification.domain.TaskGroupProgression;

@Repository
public class TaskGroupProgressionRepository extends HibernateAbstractRepository<TaskGroupProgression, Long>{
	public TaskGroupProgression findByPlayer(TaskGroup group, Player player) {
		StringBuilder builder = new StringBuilder();
		builder.append("from TaskGroupProgression tp ");
		builder.append("where ");
		builder.append("  tp.taskGroup.id = :groupId ");
		builder.append("  and tp.player.id = :playerId ");
		
		Query query = getSession().createQuery(builder.toString());
		query.setParameter("playerId", player.getId());
		query.setParameter("groupId", group.getId());
		Object obj = query.uniqueResult();
		if (obj == null) {
			return null;
		}
		return (TaskGroupProgression)obj;
	}
}
