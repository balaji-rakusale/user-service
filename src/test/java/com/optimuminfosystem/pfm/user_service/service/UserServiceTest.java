package com.optimuminfosystem.pfm.user_service.service;

import com.optimuminfosystem.pfm.user_service.exception.UserServiceException;
import com.optimuminfosystem.pfm.user_service.model.User;
import com.optimuminfosystem.pfm.user_service.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataAccessException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void testCreateUser_Success() {
        User user = new User();
        user.setId(1L);
        user.setName("Balaji");
        when(userRepository.existsById(1L)).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        User saved = userService.createUser(user);
        assertEquals("Balaji", saved.getName());
    }
    @Test
    void testGetUser_Success() {
        User user = new User();
        user.setId(1L);
        user.setName("Balaji");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.get(1L);
        assertEquals("Balaji", result.getName());
    }

    @Test
    void testGetUser_NotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserServiceException exception = assertThrows(UserServiceException.class, () -> userService.get(1L));
        assertEquals("NOT FOUND", exception.getErrorCode());
    }

    @Test
    void testGetAllUsers() {
        User user1 = new User();
        user1.setName("Balaji");
        User user2 = new User();
        user2.setName("Rockstar");

        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));
        List<User> users = userService.getAll();
        assertEquals(2, users.size());
    }

    @Test
    void testDeleteUser_Success() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doNothing().when(userRepository).deleteById(1L);

        assertDoesNotThrow(() -> userService.deleteUser(1L));
        verify(userRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        when(userRepository.existsById(1L)).thenReturn(false);
        UserServiceException exception = assertThrows(UserServiceException.class, () -> userService.deleteUser(1L));
        assertEquals("USER_NOT_FOUND", exception.getErrorCode());
    }

    @Test
    void testDeleteUser_DataAccessException() {
        when(userRepository.existsById(1L)).thenReturn(true);
        doThrow(new DataAccessException("DB Error") {}).when(userRepository).deleteById(1L);

        UserServiceException exception = assertThrows(UserServiceException.class, () -> userService.deleteUser(1L));
        assertEquals("DATABASE_ERROR", exception.getErrorCode());
    }
}
