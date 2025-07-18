package ai.shreds.application.dtos;

import lombok.Data;
import java.util.List;
import java.util.UUID;

@Data
public class ApplicationBatchReservationResponseDTO {
    private UUID productionRunId;
    private List<ApplicationReservationResponseDTO> reservations;
}