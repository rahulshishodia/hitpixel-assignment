package net.rahuls.hitpixel.api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import net.rahuls.hitpixel.data.repository.UserRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserValidator implements ConstraintValidator<UserId, Long> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext constraintValidatorContext) {
        if (userId == null) {
            return false;
        }
        return userRepository.existsById(userId);
    }
}
