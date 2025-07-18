package ai.shreds.infrastructure.external_services;

import ai.shreds.domain.entities.DomainComponentReservationEntity;
import ai.shreds.domain.ports.DomainOutputPortEventPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("dummyEventPublisher") // Named to avoid conflicts if a real one is added
public class InfrastructureDummyEventPublisherImpl implements DomainOutputPortEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(InfrastructureDummyEventPublisherImpl.class);

    @Override
    public void publishReservationCreated(DomainComponentReservationEntity reservation) {
        logger.info("DUMMY PUBLISH: Reservation CREATED: {}", reservation.getReservationId());
    }

    @Override
    public void publishReservationCancelled(DomainComponentReservationEntity reservation) {
        logger.info("DUMMY PUBLISH: Reservation CANCELLED: {}", reservation.getReservationId());
    }

    @Override
    public void publishReservationConsumed(DomainComponentReservationEntity reservation) {
        logger.info("DUMMY PUBLISH: Reservation CONSUMED: {}", reservation.getReservationId());
    }

    @Override
    public void publishReservationExpired(DomainComponentReservationEntity reservation) {
        logger.info("DUMMY PUBLISH: Reservation EXPIRED: {}", reservation.getReservationId());
    }

    @Override
    public void publishBatchReservationCreated(List<DomainComponentReservationEntity> reservations) {
        logger.info("DUMMY PUBLISH: Batch of {} reservations CREATED.", reservations.size());
    }
}