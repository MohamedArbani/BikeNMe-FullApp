package ma.ac.emi.ginfo.restfull.repositories;

import ma.ac.emi.ginfo.restfull.entities.CustomizedPricing;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomizedPricingRepository extends JpaRepository<CustomizedPricing, Long> {
}