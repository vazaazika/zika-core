package br.les.opus.gamification.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.TaskAssignment;

@Repository
public class TaskAssignmentRepository extends HibernateAbstractRepository<TaskAssignment, Long> {

	@SuppressWarnings("unchecked")
	public List<TaskAssignment> findAllIncomplete(PerformedTask performedTask) {
		StringBuilder builder = new StringBuilder();
		builder.append("select ta from TaskAssignment ta ");
		builder.append("left join ta.progressions as ps with ps.player.id = :playerId ");
		builder.append("where ");
		builder.append("  ta.task.id = :taskId ");
		builder.append("  and (ps is null or ps.complete = false) ");
		
		Query query = getSession().createQuery(builder.toString());
		query.setParameter("playerId", performedTask.getPlayer().getId());
		query.setParameter("taskId", performedTask.getTask().getId());
		return query.list();
	}
}
