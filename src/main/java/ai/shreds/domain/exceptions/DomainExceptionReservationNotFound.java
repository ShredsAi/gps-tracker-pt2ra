package ai.shreds.domain.exceptions;

import ai.shreds.domain.value_objects.DomainValueReservationId;

public class DomainExceptionReservationNotFound extends RuntimeException {
    public DomainExceptionReservationNotFound(DomainValueReservationId reservationId) {
        super("Reservation not found with ID: " + reservationId);
    }
}