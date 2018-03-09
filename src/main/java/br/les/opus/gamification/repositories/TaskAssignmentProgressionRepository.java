package br.les.opus.gamification.repositories;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskAssignment;
import br.les.opus.gamification.domain.TaskAssignmentProgression;

@Repository
public class TaskAssignmentProgressionRepository extends HibernateAbstractRepository<TaskAssignmentProgression, Long>{

	public TaskAssignmentProgression findByPlayer(TaskAssignment assignment, Player player) {
		StringBuilder builder = new StringBuilder();
		builder.append("from TaskAssignmentProgression tp ");
		builder.append("where ");
		builder.append("  tp.taskAssignment.id = :taskAssignmentId ");
		builder.append("  and tp.player.id = :playerId ");
		
		Query query = getSession().createQuery(builder.toString());
		query.setParameter("playerId", player.getId());
		query.setParameter("taskAssignmentId", assignment.getId());
		Object obj = query.uniqueResult();
		if (obj == null) {
			return null;
		}
		return (TaskAssignmentProgression)obj;
	}
}
