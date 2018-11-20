package br.les.opus.gamification.repositories;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.challenge.StrikeChallenge;

@Repository
public class StrikeChallengeRepository extends HibernateAbstractRepository<StrikeChallenge, Long>{
	
	@SuppressWarnings("unchecked")
	public List<StrikeChallenge> findIncompletePerformedChallenge() {
		String hql = "from StrikeChallenge where complete='f'";
		Query query = getSession().createQuery(hql);
		
		List<StrikeChallenge> list = query.list();
		
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<StrikeChallenge> findAllStrikeChallengeByPlayer(Player player) {
		String hql = "select distinct s from StrikeChallenge s where s.player.id = :pId";
		Query query = getSession().createQuery(hql);
		query.setParameter("pId", player.getId());
		
		return query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<StrikeChallenge> findAllIncompleteStrikeChallengeByPlayer(Player player) {
		String hql = "from StrikeChallenge s where s.complete = 'f' and s.player.id = :pId";
		Query query = getSession().createQuery(hql);
		query.setParameter("pId", player.getId());
		
		List<StrikeChallenge> lista = query.list();
		
		return lista;
	}


	public boolean isPlayerEnrolledInChallenge(Player player) {
		List<StrikeChallenge> lista = findAllIncompleteStrikeChallengeByPlayer(player);
		if(lista  == null || lista.isEmpty()) {
			return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	public List<StrikeChallenge> findWins(Player player){
		String hql = "from StrikeChallenge s where s.complete = 't' and s.succeed = 't' and s.player.id = :pId";
		Query query = getSession().createQuery(hql);
		query.setParameter("pId", player.getId());
		
		return query.list();
	}

	public boolean isPlayerWinner(Player player) {
		List<StrikeChallenge> lista = findWins(player);
		if(lista  == null || lista.isEmpty()) {
			return false;
		}
		return true;
	}

}
