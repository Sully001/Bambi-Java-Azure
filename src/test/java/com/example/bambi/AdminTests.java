package com.example.bambi;

import com.example.bambi.entity.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AdminTests {

    @Test
    public void testAdmin() {
        Admin admin = new Admin();
        admin.setId(1L);
        admin.setUsername("admin");
        admin.setPassword("password");

        assertEquals(1L, (long)admin.getId());
        assertEquals("admin", admin.getUsername());
        assertEquals("password", admin.getPassword());
    }
}
