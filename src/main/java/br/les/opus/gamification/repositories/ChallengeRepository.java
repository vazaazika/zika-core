package br.les.opus.gamification.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.constraints.AssignmentConstraint;
import br.les.opus.gamification.constraints.DurationConstraint;
import br.les.opus.gamification.constraints.QuantityConstraint;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskAssignment;
import br.les.opus.gamification.domain.challenge.Challenge;
import br.les.opus.gamification.domain.challenge.ChallengeEntity;
import br.les.opus.gamification.domain.challenge.ChallengeName;
import br.les.opus.gamification.domain.challenge.PerformedChallenge;

@Repository
public class ChallengeRepository extends HibernateAbstractRepository<Challenge, Long>{
	@Autowired
	private ChallengeEntityRepository ceDao;
	
	@Autowired
	private OnTopRepository onTopDao;
	
	
	
	@SuppressWarnings("unchecked")
	public List<Challenge> findAllOpenedChallengesByPlayer(Long playerId) {
		
		//get all ChallengeEntity
		List<ChallengeEntity> entities = ceDao.getEntitiesByPlayer(playerId);
		
		if (entities == null || entities.isEmpty()) {
			return null;
		}
		
		
		//get all performedChallenge
		List<Long> performedIds = new ArrayList<>();
		
		for(ChallengeEntity entity: entities) {
			PerformedChallenge pc = entity.getPerformedChallenge();
			
			if(!pc.isComplete()) {
				performedIds.add(entity.getPerformedChallenge().getChallenge().getId());
			}
		}
		
		if(performedIds.isEmpty()) {
			return null;
		}
		
		
		Criteria criteria = getSession().createCriteria(getEntityClass());
		criteria.add(Restrictions.in("id", performedIds));
		
		
		List<Challenge> challenges = getUniqueObjects(criteria.list());;
		
		return challenges;
	}
	
	public List<Challenge> findAllOpenedChallengesByPlayerTeam(Player player) {
		if(onTopDao.isPlayerTeamEnrolledInOnTopChallenge(player)) {
			return Arrays.asList(findChallengeByName(ChallengeName.ONTOP.getName()));
		}else {
			return null;
		}
		
	}


	public boolean hasConstraint(Long challengeId) {
		Challenge cons = findOne(challengeId);
		List<TaskAssignment> assignments = cons.getAssignments();
		
		if (assignments == null || assignments.isEmpty()) {
			return false;
		}
		
		for(TaskAssignment ta: assignments) {
			List<AssignmentConstraint> constraints = ta.getConstraints();
			
			if(constraints != null && !constraints.isEmpty()) {
				return true;
			}
		}
		return false;
	}
	
	public QuantityConstraint getQuantityConstraint(Long challengeId) {
		Challenge cons = findOne(challengeId);
		
		if(!hasConstraint(challengeId)) {
			return null;
		}
		List<TaskAssignment> assignments = cons.getAssignments();
		
		for(TaskAssignment ta: assignments) {
			List<AssignmentConstraint> constraints = ta.getConstraints();
			
			if(constraints != null && !constraints.isEmpty()) {
				
				for(AssignmentConstraint ac: constraints) {
					if (ac instanceof QuantityConstraint) {
						return (QuantityConstraint) ac;
					}
				}
			}
		}
		return null;
	}
	
	public DurationConstraint getDurationConstraint(Long challengeId) {
		Challenge cons = findOne(challengeId);
		
		if(!hasConstraint(challengeId)) {
			return null;
		}
		List<TaskAssignment> assignments = cons.getAssignments();
		
		for(TaskAssignment ta: assignments) {
			List<AssignmentConstraint> constraints = ta.getConstraints();
			
			if(constraints != null && !constraints.isEmpty()) {
				
				for(AssignmentConstraint ac: constraints) {
					if (ac instanceof DurationConstraint) {
						return (DurationConstraint) ac;
					}
				}
			}
		}
		return null;
	}
	
	public Challenge findChallengeByName(String challengeName) {
		String hql = "from Challenge where name = :cName";
		Query query = getSession().createQuery(hql);
		query.setParameter("cName", challengeName);
		
		return (Challenge) query.uniqueResult();
	}

}
