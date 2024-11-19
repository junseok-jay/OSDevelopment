package fundsite.fund_web_backend.controller;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import fundsite.fund_web_backend.model.User;
import fundsite.fund_web_backend.service.UserService;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(User user) {
        userService.registerUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/perform_login")
    public String loginUser(String username, String password, Model model) {
        Optional<User> user = userService.loginUser(username, password);
        if (user.isPresent()) {
            return "redirect:/mypage";
        } else {
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "login";
        }
    }

    @GetMapping("/mypage")
    public String showMyPage(Model model) {
        // 현재 로그인된 사용자 정보를 SecurityContext에서 가져옴
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/login"; // 인증되지 않은 경우 로그인 페이지로 리다이렉트
        }

        String username = authentication.getName();
        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            model.addAttribute("user", user);
        }

        return "mypage";
    }
}
