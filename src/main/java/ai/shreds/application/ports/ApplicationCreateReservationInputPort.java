package ai.shreds.application.ports;

import ai.shreds.application.dtos.ApplicationReservationRequestDTO;
import ai.shreds.application.dtos.ApplicationReservationResponseDTO;

public interface ApplicationCreateReservationInputPort {
    ApplicationReservationResponseDTO createReservation(ApplicationReservationRequestDTO request);
}