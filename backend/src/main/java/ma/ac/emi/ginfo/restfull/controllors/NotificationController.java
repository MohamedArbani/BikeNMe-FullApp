package ma.ac.emi.ginfo.restfull.controllors;

import ma.ac.emi.ginfo.restfull.entities.Notification;
import ma.ac.emi.ginfo.restfull.entities.User;
import ma.ac.emi.ginfo.restfull.repositories.NotificationRepository;
import ma.ac.emi.ginfo.restfull.repositories.UserRepository;
import ma.ac.emi.ginfo.restfull.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@CrossOrigin(origins = "*")
@RestController
public class NotificationController {
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NotificationService notificationService;
    @Autowired
    RentalService rentalService;
    @Autowired
    BikeService bikeService;
    @Autowired
    UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    @GetMapping("/allNotifs")
    public List<Notification> showHistory() {
        return notificationRepository.findAll();
    }
    @GetMapping("/allNotifs/new")
    public List<Notification> showNotifs() {
        return notificationRepository.findByNotOppend(true);
    }
    @GetMapping("/notifs/new/{destId}")
    public List<Notification> showNotifsNotOppendByDestinataire(@PathVariable Long destId) {
        return notificationRepository.findByNotOppendAndDestinataire(true, userRepository.findById(destId));
    }

    @GetMapping("/notifs/dest/{destId}")
    public List<Notification> showNotifsByDestinataire(@PathVariable Long destId) {
        return notificationRepository.findByDestinataire(userRepository.findById(destId).orElseThrow(IllegalArgumentException::new));
    }

    @GetMapping("/historyOwner/{destId}")
    public List<Notification> showHistoryByDestinataire(@PathVariable Long destId) {
        User owner = null;
        try {
            owner = userRepository.findById(destId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
        return notificationRepository.findByDestinataire(owner);
    }
    @GetMapping("/historyUser/{userId}")
    public List<Notification> showHistoryByUser(@PathVariable Long userId) {
        return notificationService.showHistoryByUser(userId);
    }
    @GetMapping("/notifs/types/{type}")
    public List<Notification> searchByType(@PathVariable String type) {
        return notificationService.searchByType(type);
    }

    @GetMapping("/notifs/{id}")
    public Notification getNotificationById(@PathVariable("id") Long id){
        return notificationService.findById(id);
    }

    @PutMapping("/notifs/update/{id}")
    public ResponseEntity<Void> updateNotification(@PathVariable("id") Long id, @RequestBody Map<String, Object> updates) {
        Notification notification = notificationService.findById(id);
        notification.setNotOppend((Boolean) updates.get("notOppend"));
        notificationService.save(notification);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/notifs/{id}")
    public ResponseEntity<Void> sendNotification(@PathVariable("id") Long id,@RequestBody Notification notification){
        notification.setUser(userService.getUserById(notification.getUser().getId()));
        notification.setBike(bikeService.getBikeById(notification.getBike().getId()));
        notification.setDestinataire(userService.getUserById(id));

        notificationService.save(notification);
        mailService.sendNotificationMail(notification.getUser().getEmail(), "Owner notification", notification);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/notifs/{id}")
    public ResponseEntity<Notification> updateNotification(@PathVariable("id") Long id, @RequestBody Notification notification) {
        notificationService.update(id, notification);
        return ResponseEntity.ok().body(notification);
    }

    @DeleteMapping("/notifs/{id}")
    public ResponseEntity<?> deleteNotification(@PathVariable("id") Long id){
        this.notificationService.delete(id);
        Map<String,String> message = new HashMap<>();
        message.put("message","Notification Deleted successfully");
        return ResponseEntity.ok().body(message);
    }



}
