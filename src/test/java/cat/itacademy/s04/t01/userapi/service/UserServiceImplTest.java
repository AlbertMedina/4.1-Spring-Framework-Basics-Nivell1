package cat.itacademy.s04.t01.userapi.service;

import cat.itacademy.s04.t01.userapi.exceptions.EmailAlreadyExistsException;
import cat.itacademy.s04.t01.userapi.models.User;
import cat.itacademy.s04.t01.userapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_shouldThrowExceptionWhenEmailAlreadyExists() {
        String name = "Albert";
        String email = "albert@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class, () -> userService.createUser(name, email));

        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_shouldSaveUserWhenEmailDoesNotExist() {
        String name = "Albert";
        String email = "albert@example.com";

        when(userRepository.existsByEmail(email)).thenReturn(false);

        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User user = userService.createUser(name, email);

        assertNotNull(user);
        assertEquals(name, user.name());
        assertEquals(email, user.email());
        assertNotNull(user.id());

        verify(userRepository).existsByEmail(email);
        verify(userRepository).save(any(User.class));
    }
}
