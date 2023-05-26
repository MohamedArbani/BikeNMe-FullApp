package ma.ac.emi.ginfo.restfull.repositories;

import ma.ac.emi.ginfo.restfull.entities.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}