package fundsite.fund_web_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import fundsite.fund_web_backend.model.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
