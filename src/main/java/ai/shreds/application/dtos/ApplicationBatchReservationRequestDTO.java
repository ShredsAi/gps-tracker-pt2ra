package ai.shreds.application.dtos;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class ApplicationBatchReservationRequestDTO {
    private UUID productionRunId;
    private UUID reservedBy;
    private LocalDateTime expiresAt;
    private List<ApplicationReservationItemDTO> items;
}