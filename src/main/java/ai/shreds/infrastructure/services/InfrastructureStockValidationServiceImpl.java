package ai.shreds.infrastructure.services;

import ai.shreds.domain.entities.DomainStockLevelEntity;
import ai.shreds.domain.ports.DomainOutputPortStockLevelRepository;
import ai.shreds.domain.ports.DomainOutputPortStockValidationService;
import ai.shreds.domain.value_objects.DomainUnitOfMeasureValue;
import ai.shreds.domain.value_objects.DomainValueItemId;
import ai.shreds.domain.value_objects.DomainValueLocationId;
import ai.shreds.domain.value_objects.DomainValueQuantity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InfrastructureStockValidationServiceImpl implements DomainOutputPortStockValidationService {

    private final DomainOutputPortStockLevelRepository stockLevelRepository;

    @Override
    @Transactional(readOnly = true)
    public boolean isStockAvailable(DomainValueItemId itemId, DomainValueLocationId locationId, DomainValueQuantity requestedQuantity) {
        return getAvailableQuantity(itemId, locationId).isGreaterThanOrEqual(requestedQuantity);
    }

    @Override
    @Transactional(readOnly = true)
    public DomainValueQuantity getAvailableQuantity(DomainValueItemId itemId, DomainValueLocationId locationId) {
        Optional<DomainStockLevelEntity> stockLevelOpt = stockLevelRepository.findByItemAndLocation(itemId, locationId);
        if (stockLevelOpt.isEmpty()) {
            // If stock level does not exist, available quantity is zero. Use a default unit.
            return DomainValueQuantity.zero(DomainUnitOfMeasureValue.PIECES);
        }
        DomainStockLevelEntity stockLevel = stockLevelOpt.get();
        return stockLevel.getAvailableQuantity();
    }

    @Override
    @Transactional
    public void reserveStock(DomainValueItemId itemId, DomainValueLocationId locationId, DomainValueQuantity quantity) {
        DomainStockLevelEntity stockLevel = stockLevelRepository.lockForUpdate(itemId, locationId)
                .orElseThrow(() -> new IllegalStateException("Stock level not found for item " + itemId + " at location " + locationId));

        if (!stockLevel.canReserve(quantity)) {
            throw new IllegalStateException("Insufficient stock to reserve.");
        }

        stockLevel.reserveQuantity(quantity);
        stockLevelRepository.save(stockLevel);
    }

    @Override
    @Transactional
    public void releaseStock(DomainValueItemId itemId, DomainValueLocationId locationId, DomainValueQuantity quantity) {
        DomainStockLevelEntity stockLevel = stockLevelRepository.lockForUpdate(itemId, locationId)
                .orElseThrow(() -> new IllegalStateException("Stock level not found for item " + itemId + " at location " + locationId));

        stockLevel.releaseReservedQuantity(quantity);
        stockLevelRepository.save(stockLevel);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean itemExistsAtLocation(DomainValueItemId itemId, DomainValueLocationId locationId) {
        return stockLevelRepository.findByItemAndLocation(itemId, locationId).isPresent();
    }
}