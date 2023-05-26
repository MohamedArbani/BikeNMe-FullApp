package ma.ac.emi.ginfo.restfull.controllors;

import ma.ac.emi.ginfo.restfull.entities.Contact;
import ma.ac.emi.ginfo.restfull.repositories.ContactRepository;
import ma.ac.emi.ginfo.restfull.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin("*")
@RequestMapping(value = "/contact-us")
public class ContactController {
    @Autowired
    ContactRepository contactRepository;

    @Autowired
    MailService mailService;

    @GetMapping
    public List<Contact> getContacts() {
        return contactRepository.findAll();
    }

    @PostMapping
    public Contact sendMessage(@RequestBody Contact contact) {
        mailService.sendEmail(contact);
        return contactRepository.save(contact);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable Long id) {
        contactRepository.deleteById(id);
    }


}
