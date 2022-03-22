package TMS_course.TMS_ToDOList.service;

import TMS_course.TMS_ToDOList.persistense.entity.User;
import TMS_course.TMS_ToDOList.persistense.repository.UserRepository;
import TMS_course.TMS_ToDOList.representation.UserRepr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

import static TMS_course.TMS_ToDOList.security.Utils.getCurrentUser;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void create(UserRepr userRepr) {
        User user = new User();
        user.setUsername(userRepr.getUsername());
        user.setPassword(passwordEncoder.encode(userRepr.getPassword()));
        userRepository.save(user);
    }

    public Optional<Long> getCurrentUserId() {
        return getCurrentUser()
                .flatMap(userRepository::getUserByUsername)
                .map(User::getId);
    }
}
