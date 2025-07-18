package ai.shreds.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "component_reservation")
@Getter
@Setter
public class InfrastructureComponentReservationJpaEntity {

    @Id
    private UUID reservationId;

    @Column(nullable = false)
    private UUID itemId;

    @Column(nullable = false)
    private UUID locationId;

    @Column(nullable = false)
    private UUID productionRunId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal quantityReserved;

    @Column(nullable = false, length = 50)
    private String quantityUnit;

    @Column(nullable = false)
    private UUID reservedBy;

    @Column(nullable = false)
    private Instant reservedAt;

    @Column(nullable = false)
    private Instant expiresAt;

    @Column(nullable = false, length = 20)
    private String status;

    @Version
    private Long version;
}