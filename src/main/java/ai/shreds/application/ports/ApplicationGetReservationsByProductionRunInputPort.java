package ai.shreds.application.ports;

import ai.shreds.application.dtos.ApplicationReservationResponseDTO;
import java.util.List;
import java.util.UUID;

public interface ApplicationGetReservationsByProductionRunInputPort {
    List<ApplicationReservationResponseDTO> getReservationsByProductionRun(UUID productionRunId);
}