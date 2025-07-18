package ai.shreds.domain.services;

import ai.shreds.domain.entities.DomainComponentReservationEntity;
import ai.shreds.domain.exceptions.DomainExceptionInsufficientStock;
import ai.shreds.domain.exceptions.DomainExceptionReservationNotFound;
import ai.shreds.domain.ports.DomainOutputPortEventPublisher;
import ai.shreds.domain.ports.DomainOutputPortReservationRepository;
import ai.shreds.domain.ports.DomainOutputPortStockValidationService;
import ai.shreds.domain.value_objects.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DomainServiceReservationManager {

    private final DomainOutputPortReservationRepository reservationRepository;
    private final DomainOutputPortStockValidationService stockValidationService;
    private final DomainOutputPortEventPublisher eventPublisher;

    public DomainServiceReservationManager(DomainOutputPortReservationRepository reservationRepository, DomainOutputPortStockValidationService stockValidationService, DomainOutputPortEventPublisher eventPublisher) {
        this.reservationRepository = reservationRepository;
        this.stockValidationService = stockValidationService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public DomainComponentReservationEntity createReservation(DomainValueItemId itemId, DomainValueLocationId locationId, DomainValueProductionRunId productionRunId, DomainValueQuantity quantityReserved, DomainValueUserId reservedBy, Instant expiresAt) {
        if (!stockValidationService.isStockAvailable(itemId, locationId, quantityReserved)) {
            throw new DomainExceptionInsufficientStock(itemId, locationId, quantityReserved, stockValidationService.getAvailableQuantity(itemId, locationId));
        }

        stockValidationService.reserveStock(itemId, locationId, quantityReserved);

        DomainComponentReservationEntity reservation = new DomainComponentReservationEntity(
                DomainValueReservationId.generate(),
                itemId, locationId, productionRunId, quantityReserved, reservedBy,
                Instant.now(), expiresAt, DomainValueReservationStatus.active()
        );

        DomainComponentReservationEntity savedReservation = reservationRepository.save(reservation);
        eventPublisher.publishReservationCreated(savedReservation);
        return savedReservation;
    }

    @Transactional
    public List<DomainComponentReservationEntity> createBatchReservations(List<ReservationRequest> requests) {
        // Group by item and location to check stock once per item
        // This is a simplified check; a more robust implementation would handle this carefully.
        for (ReservationRequest request : requests) {
            if (!stockValidationService.isStockAvailable(request.getItemId(), request.getLocationId(), request.getQuantity())) {
                throw new DomainExceptionInsufficientStock(request.getItemId(), request.getLocationId(), request.getQuantity(), stockValidationService.getAvailableQuantity(request.getItemId(), request.getLocationId()));
            }
        }

        List<DomainComponentReservationEntity> createdReservations = new ArrayList<>();
        for (ReservationRequest request : requests) {
            stockValidationService.reserveStock(request.getItemId(), request.getLocationId(), request.getQuantity());
            DomainComponentReservationEntity reservation = new DomainComponentReservationEntity(
                    DomainValueReservationId.generate(),
                    request.getItemId(), request.getLocationId(), request.getProductionRunId(),
                    request.getQuantity(), request.getReservedBy(), Instant.now(), request.getExpiresAt(),
                    DomainValueReservationStatus.active()
            );
            createdReservations.add(reservationRepository.save(reservation));
        }

        eventPublisher.publishBatchReservationCreated(createdReservations);
        return createdReservations;
    }

    @Transactional
    public DomainComponentReservationEntity cancelReservation(DomainValueReservationId reservationId) {
        DomainComponentReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new DomainExceptionReservationNotFound(reservationId));

        reservation.cancel();
        stockValidationService.releaseStock(reservation.getItemId(), reservation.getLocationId(), reservation.getQuantityReserved());

        DomainComponentReservationEntity updatedReservation = reservationRepository.update(reservation);
        eventPublisher.publishReservationCancelled(updatedReservation);
        return updatedReservation;
    }

    @Transactional
    public DomainComponentReservationEntity consumeReservation(DomainValueReservationId reservationId) {
        DomainComponentReservationEntity reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new DomainExceptionReservationNotFound(reservationId));

        reservation.consume();
        // Note: Stock is not released upon consumption

        DomainComponentReservationEntity updatedReservation = reservationRepository.update(reservation);
        eventPublisher.publishReservationConsumed(updatedReservation);
        return updatedReservation;
    }

    @Transactional
    public List<DomainComponentReservationEntity> processExpiredReservations() {
        List<DomainComponentReservationEntity> expiredReservations = reservationRepository.findExpiredActiveReservations(Instant.now());
        for (DomainComponentReservationEntity reservation : expiredReservations) {
            reservation.expire();
            stockValidationService.releaseStock(reservation.getItemId(), reservation.getLocationId(), reservation.getQuantityReserved());
            reservationRepository.update(reservation);
            eventPublisher.publishReservationExpired(reservation);
        }
        return expiredReservations;
    }

    public Optional<DomainComponentReservationEntity> getReservationById(DomainValueReservationId reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public List<DomainComponentReservationEntity> getReservationsByProductionRun(DomainValueProductionRunId productionRunId) {
        return reservationRepository.findByProductionRunId(productionRunId);
    }

    public static class ReservationRequest {
        private final DomainValueItemId itemId;
        private final DomainValueLocationId locationId;
        private final DomainValueProductionRunId productionRunId;
        private final DomainValueQuantity quantity;
        private final DomainValueUserId reservedBy;
        private final Instant expiresAt;

        public ReservationRequest(DomainValueItemId itemId, DomainValueLocationId locationId, DomainValueProductionRunId productionRunId, DomainValueQuantity quantity, DomainValueUserId reservedBy, Instant expiresAt) {
            this.itemId = itemId;
            this.locationId = locationId;
            this.productionRunId = productionRunId;
            this.quantity = quantity;
            this.reservedBy = reservedBy;
            this.expiresAt = expiresAt;
        }

        public DomainValueItemId getItemId() { return itemId; }
        public DomainValueLocationId getLocationId() { return locationId; }
        public DomainValueProductionRunId getProductionRunId() { return productionRunId; }
        public DomainValueQuantity getQuantity() { return quantity; }
        public DomainValueUserId getReservedBy() { return reservedBy; }
        public Instant getExpiresAt() { return expiresAt; }
    }
}