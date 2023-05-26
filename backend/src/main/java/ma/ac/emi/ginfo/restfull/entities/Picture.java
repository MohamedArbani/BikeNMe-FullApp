package ma.ac.emi.ginfo.restfull.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Arrays;

@Entity
public class Picture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "bike_id")
    @JsonBackReference
    private Bike bike;

    @Lob
    private byte[] data;

    private String url;

    public Picture() {
    }

    public Picture(String url) {
        this.url = url;
    }

    public Picture(Bike bike, byte[] data) {
        this.bike = bike;
        this.data = data;
    }

    public Picture(Bike bike, String url) {
        this.bike = bike;
        this.url = url;
    }

    public Picture(Bike bike, byte[] data, String url) {
        this.bike = bike;
        this.data = data;
        this.url = url;
    }

    public Bike getBike() {
        return bike;
    }

    public void setBike(Bike bike) {
        this.bike = bike;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "id=" + id +
                ", data=" + Arrays.toString(data) +
                ", url='" + url + '\'' +
                '}';
    }
}