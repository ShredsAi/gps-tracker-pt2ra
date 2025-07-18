package ai.shreds.infrastructure.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "stock_level")
@IdClass(InfrastructureStockLevelId.class)
@Getter
@Setter
public class InfrastructureStockLevelJpaEntity {

    @Id
    private java.util.UUID itemId;

    @Id
    private java.util.UUID locationId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal quantityOnHand;

    @Column(name = "reserved_qty", nullable = false, precision = 19, scale = 4)
    private BigDecimal reservedQty;

    private LocalDateTime lastUpdated;

    @Version
    private Long version;
}