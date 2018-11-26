package br.les.opus.test.gamification.repositories;

import br.les.opus.auth.core.domain.User;
import br.les.opus.dengue.core.domain.PoiType;
import br.les.opus.dengue.core.domain.PointOfInterest;
import br.les.opus.dengue.core.fields.Field;
import br.les.opus.dengue.core.repositories.FieldRepository;
import br.les.opus.dengue.core.repositories.PoiTypeRepository;
import br.les.opus.dengue.core.repositories.PointOfInterestRepository;
import br.les.opus.gamification.domain.DashboardResults;
import br.les.opus.gamification.domain.HealthAgent;
import br.les.opus.gamification.domain.Player;
import br.les.opus.gamification.repositories.HealthAgentRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.test.util.CreatePoi;
import br.les.opus.test.util.DbTestUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HealthAgentRepositoryTest extends DbTestUtil {


    @Autowired
    PointOfInterestRepository poiDao;


    @Autowired
    private PlayerRepository playerDao;


    @Autowired
    private PoiTypeRepository typeDao;

    @Autowired
    private FieldRepository fieldDao;

    @Autowired
    private HealthAgentRepository agentDao;



    private PointOfInterest pois[] = new PointOfInterest[3];
    private PoiType type;


    private User[] users = new User[4];
    private HealthAgent[] agents = new HealthAgent[2];
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

        users[2] = new User();
        users[2].setName("Agent1");
        users[2].setPassword("111111111111");
        users[2].setUsername("agent1@tfbnw.net");


        users[3] = new User();
        users[3].setName("Agent2");
        users[3].setPassword("111111111111");
        users[3].setUsername("agent2@tfbnw.net");



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

        agents[0] = new HealthAgent(users[2]);
        agents[0].setCity("Rio de Janeiro");
        agents[0].setState("Rio de Janeiro");
        agents[0].setOrganization("Secretaria da Saude do Rio de Janeiro");

        agents[1] = new HealthAgent(users[3]);
        agents[1].setCity("Fortaleza");
        agents[1].setState("Ceará");
        agents[1].setOrganization("Secretaria da Saude do Ceará");


        agents[0] = agentDao.save(agents[0]);
        agents[1] = agentDao.save(agents[1]);



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
        agentDao.delete(Arrays.asList(agents));
        poiDao.delete(Arrays.asList(pois));
        typeDao.delete(type);

    }

    @Test
    public void findAllPoiByFilters() {

        HealthAgent healthAgentRio = agents[0];


        HealthAgent healthAgentFortaleza = agents[1];


        PointOfInterest point = pois[0];

        PointOfInterest point2 = pois[1];

        PointOfInterest point3 = pois[2];


        System.out.println(healthAgentRio.getCity());
        System.out.println(healthAgentRio.getState());

        System.out.println(healthAgentFortaleza.getCity());
        System.out.println(healthAgentFortaleza.getState());

        System.out.println(point.getCity());
        System.out.println(point.getState());

        System.out.println(point2.getCity());
        System.out.println(point2.getState());

        System.out.println(point3.getCity());
        System.out.println(point3.getState());

        PageRequest pageRequest = new PageRequest(0, 10);

        DashboardResults db = poiDao.findAllPoiByFilters(healthAgentRio, point, pageRequest);
          System.out.println(db.getPointOfInterestPage().getContent());

        List<PointOfInterest> pointOfInterests = new ArrayList<>();
        pointOfInterests.add(pois[0]);
        pointOfInterests.add(pois[1]);
        pointOfInterests.add(pois[2]);

        Assert.assertNotEquals(pointOfInterests, db.getPointOfInterestPage().getContent());
    }
}
