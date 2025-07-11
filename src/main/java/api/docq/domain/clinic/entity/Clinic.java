package api.docq.domain.clinic.entity;

import api.docq.common.entity.TimeStamped;
import api.docq.domain.clinic.enums.Department;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@Entity
@Table(name = "clinics")
@NoArgsConstructor
public class Clinic extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Department department;

    private LocalTime openTime;

    private LocalTime closeTime;

    @Builder()
    private Clinic (String name, String address, Department department, LocalTime openTime, LocalTime closeTime) {
        this.name = name;
        this.address = address;
        this.department =department;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public static Clinic of(String name, String address, Department department, LocalTime openTime, LocalTime closeTime) {
        return Clinic.builder()
                .name(name)
                .address(address)
                .department(department)
                .openTime(openTime)
                .closeTime(closeTime)
                .build();
    }
}
