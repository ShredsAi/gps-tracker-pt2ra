package ai.shreds.application.ports;

import ai.shreds.application.dtos.ApplicationReservationResponseDTO;
import java.util.UUID;

public interface ApplicationConsumeReservationInputPort {
    ApplicationReservationResponseDTO consumeReservation(UUID reservationId);
}