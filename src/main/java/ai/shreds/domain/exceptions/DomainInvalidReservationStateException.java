package ai.shreds.domain.exceptions;

import ai.shreds.domain.value_objects.DomainValueReservationId;
import ai.shreds.domain.value_objects.DomainValueReservationStatus;

public class DomainInvalidReservationStateException extends RuntimeException {

    private final DomainValueReservationId reservationId;
    private final DomainValueReservationStatus currentStatus;
    private final DomainValueReservationStatus attemptedStatus;

    public DomainInvalidReservationStateException(
            DomainValueReservationId reservationId,
            DomainValueReservationStatus currentStatus,
            DomainValueReservationStatus attemptedStatus) {
        super(String.format("Cannot transition reservation %s from %s to %s.",
                reservationId, currentStatus, attemptedStatus));
        this.reservationId = reservationId;
        this.currentStatus = currentStatus;
        this.attemptedStatus = attemptedStatus;
    }

    public DomainValueReservationId getReservationId() {
        return reservationId;
    }

    public DomainValueReservationStatus getCurrentStatus() {
        return currentStatus;
    }

    public DomainValueReservationStatus getAttemptedStatus() {
        return attemptedStatus;
    }
}