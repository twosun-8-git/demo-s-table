package com.fcc.simulation.service;

import com.fcc.simulation.dto.CellUpdateRequest;
import com.fcc.simulation.dto.InventoryRowDto;
import com.fcc.simulation.entity.WhiskyInventory;
import com.fcc.simulation.repository.WhiskyInventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final WhiskyInventoryRepository repository;

    public InventoryService(WhiskyInventoryRepository repository) {
        this.repository = repository;
    }

    public List<InventoryRowDto> findAll() {
        return repository.findAllByOrderBySortOrderAsc().stream()
                .map(this::toDto)
                .toList();
    }

    @Transactional
    public List<InventoryRowDto> updateCells(List<CellUpdateRequest> updates) {
        Map<Long, List<CellUpdateRequest>> byId = updates.stream()
                .collect(Collectors.groupingBy(CellUpdateRequest::id));

        List<WhiskyInventory> entities = repository.findAllById(byId.keySet());
        for (WhiskyInventory entity : entities) {
            List<CellUpdateRequest> cellUpdates = byId.get(entity.getId());
            if (cellUpdates == null) continue;
            for (CellUpdateRequest update : cellUpdates) {
                entity.getYearValues().put(update.year(), update.value());
            }
        }
        repository.saveAll(entities);
        return findAll();
    }

    private InventoryRowDto toDto(WhiskyInventory e) {
        return new InventoryRowDto(e.getId(), e.getAgeLabel(), e.getDistYear(), e.getTtl(), e.getYearValues());
    }
}
