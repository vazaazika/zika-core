/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.les.opus.auth.core.repositories;

import br.les.opus.gamification.repositories.BadgeRepository;
import br.les.opus.test.util.DbTestUtil;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author edge
 */
public class ResourceRepositoryTest extends DbTestUtil {
	
	@Autowired
	private ResourceRepository resourceDao;
	
	@Autowired
	private UserRepository userDao;
	
	public ResourceRepositoryTest() {
	}
	
	@Before
	public void setUp() {
	}

	@Test
	public void testFindAllByUser() {
		assertEquals(3, resourceDao.findAll().size());
		assertEquals(1, resourceDao.findAllByUser(userDao.findByUsername("username@hotmail.com")).size());
	}

	@Test
	public void testFindAllOpen() {
		assertEquals(2, resourceDao.findAllOpen().size());
	}
	
}
