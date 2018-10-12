/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.les.opus.auth.core.repositories;

import br.les.opus.auth.core.domain.Role;
import br.les.opus.auth.core.domain.User;
import br.les.opus.auth.core.domain.UserRole;
import br.les.opus.dengue.core.domain.Picture;
import br.les.opus.dengue.core.domain.PoiType;
import br.les.opus.dengue.core.domain.PointOfInterest;
import br.les.opus.dengue.core.repositories.PictureRepository;
import br.les.opus.dengue.core.repositories.PoiTypeRepository;
import br.les.opus.dengue.core.repositories.PointOfInterestRepository;
import br.les.opus.test.util.CreatePoi;
import br.les.opus.test.util.DbTestUtil;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author marcio
 */
public class UserRepositoryTest extends DbTestUtil {
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PictureRepository pictureRepository;
    
    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PointOfInterestRepository poiRepository;
    
    @Autowired
    private PoiTypeRepository poiTypeRepository;
    
    public UserRepositoryTest() {
    }
    
    @Before
    public void setUp() {
        Role role = new Role();
        role.setAuthority("PLAY");
        roleRepository.save(role);
        
        User user = new User();
        Picture picture = new Picture();
        picture.setFileName("test");
        picture = pictureRepository.save(picture);
        
        user.setAvatar(picture);
        user.setEnabled(Boolean.TRUE);
        user.setLocked(Boolean.FALSE);
        user.setName("Marcio");
        user.setPassword("123456");
        user.setUsername("marcioaguiar");
        UserRole userRole = new UserRole();
        userRole.setRole(role);
        userRole.setUser(user);
        user.setUserRoles(Arrays.asList(userRole));
        
        userRepository.save(user);
    }

    @Test
    public void testFindByUsername() {
        assertEquals("Marcio", userRepository.findByUsername("marcioaguiar").getName());
    }

    @Test
    public void testFindByUsernameAndPassword() {
         assertEquals("Marcio", userRepository.findByUsernameAndPassword("marcioaguiar", "123456").getName());
         assertNull(userRepository.findByUsernameAndPassword("marcioaguiar", "12345"));
    }

    @Test
    public void testFindUserByInvitationToken() throws IOException {
        
        User marcio = userRepository.findByUsername("marcioaguiar");
        PoiType type = new PoiType();
        type.setName("POI TYPE 1");
        poiTypeRepository.save(type);
        
        PointOfInterest poi = poiRepository.save(CreatePoi.create(type));
        Picture picture = new Picture();
        
        picture.generateUniqueName("testando");
        assertTrue(picture.getFileName().endsWith("testando"));
        picture.setContentType("image/jpg");
        picture.setDate(Date.valueOf(LocalDate.of(2015, 1, 1)));
        picture.setUser(marcio);
        picture.setPoi(poi);
        picture.setWidth(300);
        picture.setHeight(300);
        picture.setMimeType("image/jpg");
        
        picture = pictureRepository.save(picture);
        
        assertTrue(picture.isOwnedBy(marcio));
        
        picture.saveFileInFileSystem("/tmp/", new FileInputStream("src/test/resources/testImage.png"));
        picture.getScaledInstance("/tmp/", 10, 10);
        picture.deleteFileFromFileSystem("/tmp/");
    }
    
}
