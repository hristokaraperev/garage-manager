package bg.fmi.garage_manager.data.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.security.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "garages")
public class GarageEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="garage_entity_sequence")
    @SequenceGenerator(name="garage_entity_sequence", sequenceName="garage_entity_sequence", allocationSize=1)
    private Long id;

    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "location", nullable = false, length = 255)
    private String location;

    @Column(name = "city", nullable = false, length = 100)
    private String city;

    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(Timestamp updated_at) {
        this.updated_at = updated_at;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
}