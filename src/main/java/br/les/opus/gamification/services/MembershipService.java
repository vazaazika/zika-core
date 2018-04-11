package br.les.opus.gamification.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.les.opus.gamification.domain.Membership;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Team;
import br.les.opus.gamification.repositories.MembershipRepository;

@Service
public class MembershipService {

	@Autowired
	private MembershipRepository membershipDao;
	
	public Membership findCurrentMembership(Player player) {
		List<Membership> team = membershipDao.findAllActive(player);
		if (team.isEmpty()) {
			return null;
		}
		return team.iterator().next();
	}
	
	public Membership addMember(Team team, Player player) {
		List<Membership> current = membershipDao.findAllActive(player, team);
		if (!current.isEmpty()) {
			return current.iterator().next();
		}
		membershipDao.deactivateAll(player);
		Membership membership = new Membership();
		membership.setPlayer(player);
		membership.setTeam(team);
		membership = membershipDao.save(membership);
		return membership;
	}
	
	public void removeMember(Team team, Player player) {
		membershipDao.deactivate(player, team);
	}
}
