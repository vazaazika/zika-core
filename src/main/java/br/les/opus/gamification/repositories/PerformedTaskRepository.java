package br.les.opus.gamification.repositories;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.PerformedTask;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Task;
import br.les.opus.gamification.domain.pojos.DailyRecord;

@Repository
public class PerformedTaskRepository extends HibernateAbstractRepository<PerformedTask, Long> {
	
	@SuppressWarnings("unchecked")
	public Page<PerformedTask> findAllOrderedByDateDesc(Pageable pageable) {
		String hql = "from PerformedTask order by date desc";
		Query query = getSession().createQuery(hql);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return new PageImpl<>(query.list(), pageable, this.count());
	}
	
	@SuppressWarnings("unchecked")
	public Page<PerformedTask> findAllPoiPerformedTaskOrderedByDataDesc(Pageable pageable){
		String hql = "from PerformedTask where object_type='poi' order by date desc";
		Query query = getSession().createQuery(hql);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return new PageImpl<>(query.list(), pageable, this.count());
	}

	public Long countByPlayerAndTask(Player player, Task task) {
		String hql = "select count(*) from PerformedTask p where p.task.id = :task and p.player.id = :player";
		Query query = getSession().createQuery(hql);
		query.setParameter("task", task.getId());
		query.setParameter("player", player.getId());
		Object result = query.uniqueResult();
		if (result == null) {
			return 0l;
		}
		return (Long)result;
	}
	
	
	public Long sumXpByPlayerAndInterval(Player player, Date begin, Date end) {
		StringBuilder builder = new StringBuilder();
		builder.append("select sum(pt.task.givenXp) from PerformedTask pt ");
		builder.append("where ");
		builder.append("  pt.player.id = :player ");
		builder.append("  and pt.date >= :begin ");
		builder.append("  and pt.date <= :end ");
		
		Query query = getSession().createQuery(builder.toString());
		query.setParameter("begin", begin);
		query.setParameter("end", end);
		query.setParameter("player", player.getId());
		Object result = query.uniqueResult();
		if (result == null) {
			return 0l;
		}
		return (Long)result;
	}
	
	@SuppressWarnings("unchecked")
	public DailyRecord dailyRecord(Player player) {
		StringBuilder builder = new StringBuilder();
		builder.append("select year(pt.date), month(pt.date), day(pt.date), sum(pt.task.givenXp) from PerformedTask pt ");
		builder.append("where ");
		builder.append("  pt.player.id = :player ");
		builder.append("group by ");
		builder.append("  year(pt.date), month(pt.date), day(pt.date) ");
		builder.append("order by ");
		builder.append(" sum(pt.task.givenXp) desc ");
		
		Query query = getSession().createQuery(builder.toString());
		query.setParameter("player", player.getId());
		
		List<Object[]> result = query.list();
		if (result.isEmpty()) {
			return null;
		}
		Object[] data = result.iterator().next();
		DailyRecord record = new DailyRecord();
		record.setDay(new DateTime((Integer)data[0], (Integer)data[1], (Integer)data[2], 0, 0).toDate());
		record.setXp(((Long)data[3]).intValue());
		return record;
	}
	
}
