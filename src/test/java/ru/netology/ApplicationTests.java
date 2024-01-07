package ru.netology;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;
    private static final GenericContainer<?> devapp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    private static final GenericContainer<?> prodapp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devapp.start();
        prodapp.start();
    }

    @Test
    void contextLoads() {
        Integer devappMappedPort = devapp.getMappedPort(8080);
        Integer prodappMapperPort = prodapp.getMappedPort(8081);

        ResponseEntity<String> forEntityDev = restTemplate.getForEntity("http://localhost:" + devappMappedPort + "/profile", String.class);
        System.out.println(forEntityDev.getBody());
        assertEquals(200, forEntityDev.getStatusCodeValue());

        ResponseEntity<String> forEntityProd = restTemplate.getForEntity("http://localhost:" + prodappMapperPort + "/profile", String.class);
        System.out.println(forEntityProd.getBody());
        assertEquals(200, forEntityProd.getStatusCodeValue());
    }
}