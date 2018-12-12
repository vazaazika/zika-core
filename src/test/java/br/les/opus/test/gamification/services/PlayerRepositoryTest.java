package br.les.opus.test.gamification.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import br.les.opus.auth.core.domain.User;
import br.les.opus.dengue.core.domain.PoiStatusUpdateType;
import br.les.opus.dengue.core.domain.PoiType;
import br.les.opus.dengue.core.domain.PointOfInterest;
import br.les.opus.dengue.core.fields.Field;
import br.les.opus.dengue.core.repositories.FieldRepository;
import br.les.opus.dengue.core.repositories.PoiStatusUpdateTypeRepository;
import br.les.opus.dengue.core.repositories.PoiTypeRepository;
import br.les.opus.dengue.core.repositories.PointOfInterestRepository;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.domain.TaskGroup;
import br.les.opus.gamification.domain.pojos.RankedPlayer;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.test.util.CreatePoi;
import br.les.opus.test.util.DbTestUtil;

public class PlayerRepositoryTest  extends DbTestUtil{
    @Autowired
    private PlayerRepository playerDao;

    @Autowired
    private PointOfInterestRepository poiDao;

    @Autowired
    private FieldRepository fieldDao;

    @Autowired
    private PoiTypeRepository typeDao;

    @Autowired
    private PoiStatusUpdateTypeRepository psutDao;

    private PointOfInterest pois[] = new PointOfInterest[3];
    private PoiType type;


    private User[] users = new User[2];
    private Player[] players = new Player[2];



    /*
     * Create two players for the testing
     */

    @Before
    public void initializer() {
        Field field = fieldDao.findOne(1L);

        type = new PoiType();

        type.setName("Test type");
        type.setFields(Arrays.asList(field));

        type = typeDao.save(type);

        pois[0] = CreatePoi.create(type);
        pois[1] = CreatePoi.create(type);
        pois[2] = CreatePoi.create(type);

        /*
         * Create two new users
         */
        users[0] = new User();
        users[0].setName("User0");
        users[0].setPassword("00000000000000");
        users[0].setUsername("user0@tfbnw.net");

        users[1] = new User();
        users[1].setName("User1");
        users[1].setPassword("111111111111");
        users[1].setUsername("user1@tfbnw.net");

        /*
         * First new user has 1 POI
         * Second new user has 2 POIs
         */

        users[0].setReports(Arrays.asList(pois[0]));
        users[1].setReports(Arrays.asList(pois[1], pois[2]));

        players[0] = new Player(users[0]);
        players[0].setLevel(3);

        players[1] = new Player(users[1]);
        players[1].setLevel(2);

        players[0] = playerDao.save(players[0]);
        players[1] = playerDao.save(players[1]);

        pois[0].setUser(players[0]);
        pois[1].setUser(players[1]);
        pois[2].setUser(players[1]);

        pois[0] = poiDao.save(pois[0]);
        pois[1] = poiDao.save(pois[1]);
        pois[2] = poiDao.save(pois[2]);
    }

    @After
    public void restoreDB() {
        playerDao.delete(Arrays.asList(players));
        poiDao.delete(Arrays.asList(pois));
        typeDao.delete(type);
    }

    @Test
    public void sumCompletedWorkTest() {
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getId()).thenReturn(1L);

        TaskGroup group = Mockito.mock(TaskGroup.class);
        Mockito.when(group.getId()).thenReturn(1L);

        Assert.assertEquals(new Long(1), playerDao.sumCompletedWork(group, player));
    }

    @Test
    public void sumCompleted2WorksTest() {
        Player player = Mockito.mock(Player.class);
        Mockito.when(player.getId()).thenReturn(1L);

        TaskGroup group = Mockito.mock(TaskGroup.class);
        Mockito.when(group.getId()).thenReturn(1L);

        Assert.assertEquals(new Long(1), playerDao.sumCompletedWork(group, player));
    }

    @Test
    public void findOrderedByLevelTest() {
        PageRequest pageRequest = new PageRequest(0, 10);

        /*
         * It should come ordered by level
         */
        Page<Player> pages = playerDao.findOrderedByLevel(pageRequest);

        List<Player> rankedPlayers = new ArrayList<>();
        rankedPlayers.add(players[0]);							//Player 0	Level = 3
        rankedPlayers.add(players[1]);							//Player 1	Level = 2
        rankedPlayers.add(playerDao.findOne(1L));				//Bob		Level = 0
        rankedPlayers.add(playerDao.findOne(2L));				//Alice		Level = 0

        Assert.assertEquals(rankedPlayers, pages.getContent());

    }

    @Test
    public void findOrderedByReportNumberTest() {
        PageRequest pageRequest = new PageRequest(0, 10);

        List<Player> received = new ArrayList<>();
        for(RankedPlayer rp: playerDao.findOrderedByReportNumber(pageRequest)) {
            received.add(rp.getPlayer());
        }

        List<Player> rankedPlayers = new ArrayList<>();

        rankedPlayers.add(players[1]);							//Player 1	Count = 2
        rankedPlayers.add(players[0]);							//Player 0	Count = 1
        rankedPlayers.add(playerDao.findOne(2L));				//Alice		Count = 0
        rankedPlayers.add(playerDao.findOne(1L));				//Bob		Count = 0

        Assert.assertEquals(rankedPlayers, received);
    }



}
