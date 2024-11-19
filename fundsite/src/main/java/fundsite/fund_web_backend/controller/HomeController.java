package fundsite.fund_web_backend.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import fundsite.fund_web_backend.model.Donation;
import fundsite.fund_web_backend.service.DonationService;

@Controller
public class HomeController {

    private final DonationService donationService;

    public HomeController(DonationService donationService) {
        this.donationService = donationService;
    }

    // 홈 페이지
    @GetMapping("/")
    public String home(Model model) {
        List<Donation> donations = donationService.getTop3Donations();
        model.addAttribute("donations", donations);
        return "home";
    }

    // 상세 페이지
    @GetMapping("/donations/{id}")
    public String donationDetail(@PathVariable("id") Long id, Model model) {
        Donation donation = donationService.getDonationById(id);
        model.addAttribute("donation", donation);
        return "donation-detail";
    }
    
    
}
