package br.les.opus.gamification.repositories;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.les.opus.dengue.core.domain.PoiStatusUpdate;
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

		builder.append("from Player");

		Query query = getSession().createQuery(builder.toString());

		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<RankedPlayer> players = new ArrayList<>();

		List<Object> result = query.list();

		for (Object object : result) {
			Player player = (Player)object;
			players.add(new RankedPlayer(player, Long.parseLong(player.getReports().size()+"")));
		}

		Collections.sort(players, new Comparator<RankedPlayer>() {
			@Override
			public int compare(RankedPlayer o1, RankedPlayer o2) {
				return o1.getCount().intValue() > o2.getCount().intValue() ? -1 : 1;
			}
		});

		return new PageImpl<>(players, pageable, this.count());
	}

	@SuppressWarnings("unchecked")
	public Page<RankedPlayer> findOrderedByVerificationNumber(Pageable pageable) {
		StringBuilder builder = new StringBuilder();
		builder.append("from Player");

		Query query = getSession().createQuery(builder.toString());
		//query.setParameter("verificationId", PoiStatusUpdateType.VERIFICATION);
		query.setFirstResult(pageable.getOffset());
		query.setMaxResults(pageable.getPageSize());

		List<RankedPlayer> players = new ArrayList<>();
		List<Object> result = query.list();

		for (Object object : result) {
			Player player = (Player)object;
			int c = 0;
			for(PoiStatusUpdate ps : player.getPoiUpdates()){
				if(ps.getType().getId() == PoiStatusUpdateType.VERIFICATION)
					c++;
			}
			players.add(new RankedPlayer(player, Long.parseLong(""+c)));
		}

		Collections.sort(players, new Comparator<RankedPlayer>() {
			@Override
			public int compare(RankedPlayer o1, RankedPlayer o2) {
				return o1.getCount().intValue() > o2.getCount().intValue() ? -1 : 1;
			}
		});

		return new PageImpl<>(players, pageable, this.count());
	}
}
