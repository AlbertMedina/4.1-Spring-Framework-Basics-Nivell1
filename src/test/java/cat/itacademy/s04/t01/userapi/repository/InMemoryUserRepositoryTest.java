package cat.itacademy.s04.t01.userapi.repository;

import cat.itacademy.s04.t01.userapi.models.User;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class InMemoryUserRepositoryTest {

    @Test
    void saveAndFindById_shouldWork() {
        InMemoryUserRepository repository = new InMemoryUserRepository();

        UUID userId = UUID.randomUUID();
        repository.save(new User(userId, "Albert", "albert@example.com"));

        Optional<User> found = repository.findById(userId);
        assertTrue(found.isPresent());
        assertEquals("Albert", found.get().name());
    }

    @Test
    void searchByName_shouldReturnMatchingUsers() {
        InMemoryUserRepository repository = new InMemoryUserRepository();

        repository.save(new User(UUID.randomUUID(), "ALBERT", "albert@example.com"));
        repository.save(new User(UUID.randomUUID(), "albert123", "albert123@example.com"));

        assertEquals(2, repository.searchByName("albert").size());
    }
}
