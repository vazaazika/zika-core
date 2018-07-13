/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.les.opus.gamification.repositories;

import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.Team;
import br.les.opus.gamification.domain.TeamFeed;
import br.les.opus.gamification.services.MembershipService;
import br.les.opus.test.util.DbTestUtil;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 *
 * @author marcio
 */
public class TeamRepositoryTest extends DbTestUtil {
    
    @Autowired
    private TeamRepository teamRepository;
    
    @Autowired
    private MembershipService membershipService;
    
    @Autowired
    private PlayerRepository playerDao;
    
    public TeamRepositoryTest() {
    }
    
    @Before
    public void setUp() {
        teamRepository.save(Arrays.asList(create("Time 1"), create("Time 2")));
    }

    @Test
    public void testTeamRepository() {
        Team time1 = teamRepository.findByName("Time 1");
        assertEquals((long) 0, (long) teamRepository.countActiveMembers(time1.getId()));
        
        List<Player> players = Arrays.asList(playerDao.findOne(1L), playerDao.findOne(2L));
        membershipService.addMember(time1, players.get(0));
        assertEquals((long) 1, (long) teamRepository.countActiveMembers(time1.getId()));
        membershipService.addMember(time1, players.get(1));
        assertEquals((long) 2, (long) teamRepository.countActiveMembers(time1.getId()));
        
        List<Player> findAllActiveMembers = teamRepository.findAllActiveMembers(new PageRequest(0, 10), time1.getId()).getContent();
        
        assertEquals(players, findAllActiveMembers);
        
        assertEquals(0, teamRepository.findAllActiveMembers(new PageRequest(0, 10), teamRepository.findByName("Time 2").getId()).getNumberOfElements());
        
        Assert.assertNull(teamRepository.findByName("Not Found"));
        
        List<TeamFeed> content = teamRepository.findAllTeamsWithActiveMembers(new PageRequest(0, 10)).getContent();
        
//        assertEquals(1, content.size());
//        assertEquals(content.get(0).getTeam(), time1);
    }
    
    private Team create(String name) {
        Team team = new Team();
        team.setName(name);
        return team;
    }
}
