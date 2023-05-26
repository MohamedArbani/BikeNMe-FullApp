package ma.ac.emi.ginfo.restfull.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String type;

    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bike_id")
    private Bike bike;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime startTime;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime endTime;
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "destinataire_id")
    private User destinataire;

    @Column(nullable = false)
    private boolean notOppend = true;

    public Notification() {
    }

    public Notification(Long id, Bike bike, User user, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime date, User destinataire, boolean notOppend) {
        this.id = id;
        this.bike = bike;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.destinataire = destinataire;
        this.notOppend = notOppend;
    }
    public Notification(Bike bike, User user, LocalDateTime startTime, LocalDateTime endTime, LocalDateTime date, User destinataire, boolean notOppend) {
        this.bike = bike;
        this.user = user;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.destinataire = destinataire;
        this.notOppend = notOppend;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", bike=" + bike.getName() +
                ", user=" + user.getFirstName() +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", date=" + date +
                ", destinataire=" + destinataire +
                ", notOppend=" + notOppend +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getDestinataire() {
        return destinataire;
    }

    public void setDestinataire(User destinataire) {
        this.destinataire = destinataire;
    }

    public boolean isNotOppend() {
        return notOppend;
    }

    public void setNotOppend(boolean notOppend) {
        this.notOppend = notOppend;
    }


}
