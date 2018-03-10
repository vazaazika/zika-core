package br.les.opus.gamification.repositories;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskGroup;

@Repository
public class PlayerRepository extends HibernateAbstractRepository<Player, Long> {
	public Long sumCompletedWork(TaskGroup group, Player player) {
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(tp.completedWork) from TaskAssignmentProgression tp ");
		builder.append("where ");
		builder.append("  tp.taskAssignment.taskGroup.id = :groupId ");
		builder.append("  and tp.player.id = :playerId ");
		
		Query query = getSession().createQuery(builder.toString());
		query.setParameter("playerId", player.getId());
		query.setParameter("groupId", group.getId());
		
		return (Long)query.uniqueResult();
	}
}
