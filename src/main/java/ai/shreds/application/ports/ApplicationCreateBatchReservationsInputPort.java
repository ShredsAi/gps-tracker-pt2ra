package ai.shreds.application.ports;

import ai.shreds.application.dtos.ApplicationBatchReservationRequestDTO;
import ai.shreds.application.dtos.ApplicationBatchReservationResponseDTO;

public interface ApplicationCreateBatchReservationsInputPort {
    ApplicationBatchReservationResponseDTO createBatchReservations(ApplicationBatchReservationRequestDTO request);
}