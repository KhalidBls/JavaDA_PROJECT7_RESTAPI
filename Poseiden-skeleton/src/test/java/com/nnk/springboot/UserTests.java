package com.nnk.springboot;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {

    @Autowired
    UserService userService;

    @Test
    public void userTest() {
        User user = new User();
        user.setFullname("Bob bobby");
        user.setUsername("bob");
        user.setRole("ADMIN");
        user.setPassword("bobpassword");

        // Save
        userService.save(user);
        assertNotNull(user.getId());
        assertTrue(user.getUsername().equals("bob"));

        // Update
        user.setFullname("Jack update");
        userService.save(user);
        assertTrue(user.getFullname().equals("Jack update"));

        // Find
        List<User> listResult = userService.getAllUsers();
        assertTrue(listResult.size() > 0);

        // Delete
        Integer id = user.getId();
        userService.delete(id);
        Optional<User> user1 = userService.getById(id);
        Assert.assertFalse(user1.isPresent());
    }


}
