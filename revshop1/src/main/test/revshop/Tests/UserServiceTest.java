package com.Revshop.revshop.Tests;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.Revshop.revshop.model.User;
import com.Revshop.revshop.repository.UserRepository;
import com.Revshop.revshop.service.UserService;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setUserId(1L);
        user.setFirstName("John Doe");
        user.setEmail("john.doe@example.com");
    }

    @Test
    public void testGetUserById() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("John Doe", result.get().getFirstName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testUpdateUserDetails() {
        // Arrange
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User updatedUser = userService.updateUserDetails(user);

        // Assert
        assertNotNull(updatedUser);
        assertEquals("John Doe", updatedUser.getFirstName());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeleteUserById() {
        // Act
        userService.deleteUserById(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }
}
