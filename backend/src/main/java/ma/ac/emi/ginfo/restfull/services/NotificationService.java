package ma.ac.emi.ginfo.restfull.services;

import ma.ac.emi.ginfo.restfull.entities.Notification;
import ma.ac.emi.ginfo.restfull.repositories.NotificationRepository;
import ma.ac.emi.ginfo.restfull.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository,
                               UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }
    public Notification findById(Long id) {
        try {
            return notificationRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException());
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Notification> showHistoryByUser(Long id){
        try {
            return notificationRepository.findByUser(userRepository.findById(id).orElseThrow(() -> new ChangeSetPersister.NotFoundException()));
        } catch (ChangeSetPersister.NotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Notification update(Long id, Notification notification) {
        Notification old = notificationRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        old.setNotOppend(notification.isNotOppend());
        return notificationRepository.saveAndFlush(old);
    }

    public void delete(Long id){
        notificationRepository.deleteById(id);
    }

    public List<Notification> searchByType(String type) {
        return notificationRepository.searchByTypeIgnoreCase(type);
    }
}
