package net.rahuls.hitpixel.api.validator;

import jakarta.validation.ConstraintValidatorContext;
import net.rahuls.hitpixel.data.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserValidatorTest {

    @InjectMocks
    private UserValidator userValidator;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConstraintValidatorContext constraintValidatorContext;

    @Test
    void testIsValid_whenUserIdIsNull() {
        assertFalse(userValidator.isValid(null, constraintValidatorContext));
    }

    @Test
    void testIsValid_whenUserIdDoesNotExist() {
        Long nonExistingUserId = 123L;

        when(userRepository.existsById(nonExistingUserId)).thenReturn(false);

        assertFalse(userValidator.isValid(nonExistingUserId, constraintValidatorContext));
    }

    @Test
    void testIsValid_whenUserIdExists() {
        Long existingUserId = 123L;

        when(userRepository.existsById(existingUserId)).thenReturn(true);

        assertTrue(userValidator.isValid(existingUserId, constraintValidatorContext));
    }
}
