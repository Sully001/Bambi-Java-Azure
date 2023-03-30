package com.example.bambi;

import com.example.bambi.entity.Users;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UsersTests {
    @Test
    public void testUsers() {
        Users user = new Users();
        user.setId(1L);
        user.setFirst_name("John");
        user.setLast_name("Doe");
        user.setEmail("john.doe@example.com");

        assertEquals(1L, (long)user.getId());
        assertEquals("John", user.getFirst_name());
        assertEquals("Doe", user.getLast_name());
        assertEquals("john.doe@example.com", user.getEmail());
    }
}
