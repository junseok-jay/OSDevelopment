package fundsite.fund_web_backend.controller;

import java.security.Principal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fundsite.fund_web_backend.model.User;
import fundsite.fund_web_backend.service.DonationService;
import fundsite.fund_web_backend.service.UserService;


@RestController
@RequestMapping("/donation")
public class DonationController {
    @Autowired
    private DonationService donationService;

    @Autowired
    private UserService userService;

    @PostMapping("/{id}/donate")
    public ResponseEntity<String> donate(
        @PathVariable Long id,
        @RequestBody Map<String, Object> payload,
        Principal principal
    ) {
        double amount = Double.parseDouble(payload.get("amount").toString());
        User user = userService.getUserByUsername(principal.getName());
        donationService.donate(id, user.getId(), amount);
        return ResponseEntity.ok("Donation successful");
    }
}