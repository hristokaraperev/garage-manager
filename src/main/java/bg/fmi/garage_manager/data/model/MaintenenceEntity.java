package bg.fmi.garage_manager.data.model;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "maintenences")
public class MaintenenceEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="maintenence_entity_sequence")
    @SequenceGenerator(name="maintenence_entity_sequence", sequenceName="maintenence_entity_sequence", allocationSize=1)
    private Long id;
    
    @CreationTimestamp
    private LocalDateTime created_at;

    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Column(name = "is_active", nullable = false)   
    private Boolean isActive;

    @ManyToOne(optional = false)
    private GarageEntity garage;

    @ManyToOne(optional = false)
    private CarEntity car;

    @Column(name = "service_type", nullable = false, length = 100)
    private String serviceType;

    @Column(name = "scheduled_date", nullable = false)
    private LocalDate scheduledDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedAt() {
        return created_at;
    }

    public void setCreatedAt(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public LocalDateTime getUpdatedAt() {
        return updated_at;
    }

    public void setUpdatedAt(LocalDateTime updated_at) {
        this.updated_at = updated_at;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public GarageEntity getGarage() {
        return garage;
    }

    public void setGarage(GarageEntity garage) {
        this.garage = garage;
    }

    public CarEntity getCar() {
        return car;
    }

    public void setCar(CarEntity car) {
        this.car = car;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public LocalDate getScheduledDate() {
        return scheduledDate;
    }

    public void setScheduledDate(LocalDate scheduledDate) {
        this.scheduledDate = scheduledDate;
    }
}