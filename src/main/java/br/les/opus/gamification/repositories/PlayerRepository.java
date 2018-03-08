package br.les.opus.gamification.repositories;


import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;

@Repository
public class PlayerRepository extends HibernateAbstractRepository<Player, Long> {

	public Long countPerformedTasks(Player player, Task task) {
		String hql = "select count(*) from PerformedTask p where p.task.id = :task and p.player.id = :player";
		Query query = getSession().createQuery(hql);
		query.setParameter("task", task.getId());
		query.setParameter("player", player.getId());
		return (Long)query.uniqueResult();
	}
}
