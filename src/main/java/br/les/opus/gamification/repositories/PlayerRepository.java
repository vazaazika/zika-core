package br.les.opus.gamification.repositories;


import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.dengue.core.domain.PoiStatusUpdateType;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskGroup;
import br.les.opus.gamification.domain.pojos.RankedPlayer;

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
	
	@SuppressWarnings("unchecked")
	public Page<Player> findOrderedByLevel(Pageable pageable) {
		String hql = "from Player order by level desc";
		Query query = getSession().createQuery(hql);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		return new PageImpl<>(query.list(), pageable, this.count());
	}
	
	@SuppressWarnings("unchecked")
	public Page<RankedPlayer> findOrderedByReportNumber(Pageable pageable) {
		StringBuilder builder = new StringBuilder();
		builder.append("select distinct p, count(report) from Player p");
		builder.append(" left join p.reports report ");
		builder.append("group by  ");
		builder.append(" p.id, ");
		builder.append(" p.enabled, ");
		builder.append(" p.locked, ");
		builder.append(" p.name, ");
		builder.append(" p.password, ");
		builder.append(" p.username, ");
		builder.append(" p.version, ");
		builder.append(" p.level, ");
		builder.append(" p.nickname, ");
		builder.append(" p.xp ");
		builder.append("order by count(report) desc ");
		
		Query query = getSession().createQuery(builder.toString());
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		
		List<RankedPlayer> players = new ArrayList<>();
		List<Object[]> result = query.list();
		for (Object[] objects : result) {
			Player player = (Player)objects[0];
			Long count = (Long)objects[1];
			players.add(new RankedPlayer(player, count));
		}
		
		return new PageImpl<>(players, pageable, this.count());
	}
	
	@SuppressWarnings("unchecked")
	public Page<RankedPlayer> findOrderedByVerificationNumber(Pageable pageable) {
		StringBuilder builder = new StringBuilder();
		builder.append("select distinct p, count(u) from Player p");
		builder.append(" left join p.poiUpdates as u with u.type.id = :verificationId ");
		builder.append("group by  ");
		builder.append(" p.id, ");
		builder.append(" p.enabled, ");
		builder.append(" p.locked, ");
		builder.append(" p.name, ");
		builder.append(" p.password, ");
		builder.append(" p.username, ");
		builder.append(" p.version, ");
		builder.append(" p.level, ");
		builder.append(" p.nickname, ");
		builder.append(" p.xp ");
		builder.append("order by count(u) desc ");
		
		Query query = getSession().createQuery(builder.toString());
		query.setParameter("verificationId", PoiStatusUpdateType.VERIFICATION);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());
		
		List<RankedPlayer> players = new ArrayList<>();
		List<Object[]> result = query.list();
		for (Object[] objects : result) {
			Player player = (Player)objects[0];
			Long count = (Long)objects[1];
			players.add(new RankedPlayer(player, count));
		}
		
		return new PageImpl<>(players, pageable, this.count());
	}
	
}
