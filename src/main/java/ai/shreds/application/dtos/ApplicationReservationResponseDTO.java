package ai.shreds.application.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class ApplicationReservationResponseDTO {
    private UUID reservationId;
    private UUID itemId;
    private UUID locationId;
    private UUID productionRunId;
    private BigDecimal quantityReserved;
    private String quantityUnit;
    private UUID reservedBy;
    private LocalDateTime reservedAt;
    private LocalDateTime expiresAt;
    private String status;
}