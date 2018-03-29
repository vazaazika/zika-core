package br.les.opus.test.gamification.repositories;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.les.opus.commons.persistence.HibernateAbstractRepository;
import br.les.opus.gamification.repositories.PlayerRepository;
import br.les.opus.test.util.DbTestUtil;


/**
 * O objetivo dessa classe é testar {@link HibernateAbstractRepository}
 * Para tal eu useira a classe {@link PlayerRepository}
 * @author leonardo
 *
 */
public class HibernateAbstractRepositoryTest extends DbTestUtil{
	@Autowired
	private PlayerRepository dao;
	
	@Test
	public void countTest() {
		Assert.assertEquals(2L, dao.count());
	}
	
}
