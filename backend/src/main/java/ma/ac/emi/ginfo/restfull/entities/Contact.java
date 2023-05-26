package ma.ac.emi.ginfo.restfull.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "contact")
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private String name;
    private String email;
    private String number;
    private String subject;
    private String message;
    @DateTimeFormat(pattern="yyyy-MM-dd'T'HH:mm")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date = LocalDateTime.now();

    public Contact() {
    }

    public Contact(String name, String email, String number, String subject, String message, LocalDateTime date) {
        this.name = name;
        this.email = email;
        this.number = number;
        this.subject = subject;
        this.message = message;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", subject='" + subject + '\'' +
                ", message='" + message + '\'' +
                ", date=" + date +
                '}';
    }
}