package api.docq.domain.clinic.entity;

import api.docq.common.entity.TimeStamped;
import api.docq.domain.clinic.enums.DepartMent;
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

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private DepartMent departMent;

    private LocalTime openTime;

    private LocalTime closeTime;

    private boolean isDeleted;

    @Builder()
    private Clinic (String name, String address, DepartMent departMent, LocalTime openTime, LocalTime closeTime) {
        this.name = name;
        this.address = address;
        this.departMent =departMent;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.isDeleted = false;
    }

    public static Clinic of(String name,String address, DepartMent departMent, LocalTime openTime, LocalTime closeTime) {
        return Clinic.builder()
                .name(name)
                .address(address)
                .departMent(departMent)
                .openTime(openTime)
                .closeTime(closeTime)
                .build();
    }
}
