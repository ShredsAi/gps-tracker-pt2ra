package ai.shreds.application.dtos;

import lombok.Data;
import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ApplicationReservationItemDTO {
    private UUID itemId;
    private UUID locationId;
    private BigDecimal quantityReserved;
    private String quantityUnit;
}