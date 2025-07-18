package ai.shreds;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.utility.DockerImageName;

// NOTE: The @Testcontainers annotation is removed from the base class.
// It must be present on each concrete test class that extends this base class.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public abstract class BaseIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("component_reservation_test")
            .withUsername("testuser")
            .withPassword("testpass")
            .withInitScript("test-schema.sql");

    @Container
    static GenericContainer<?> activemq = new GenericContainer<>(DockerImageName.parse("apache/activemq-classic:latest"))
            .withExposedPorts(61616, 8161);

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);

        registry.add("spring.activemq.broker-url", () -> "tcp://" + activemq.getHost() + ":" + activemq.getMappedPort(61616));
        registry.add("spring.activemq.user", () -> "admin");
        registry.add("spring.activemq.password", () -> "admin");
    }
}
