package fundsite.fund_web_backend.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fundsite.fund_web_backend.model.User;
import fundsite.fund_web_backend.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;

    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User registerUser(User user) {
        // 비밀번호는 반드시 암호화하여 저장하는 것이 좋습니다 (예: BCrypt 사용)
//        user.setPassword(user.getPassword()); // 비밀번호 암호화 생략 (BCryptEncoder 추천)
        user.setBalance(0.0); // 신규 회원의 보유 금액은 0으로 초기화
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return user;
        }
        return Optional.empty();
    }
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
