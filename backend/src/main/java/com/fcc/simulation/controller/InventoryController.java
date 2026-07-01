package com.fcc.simulation.controller;

import com.fcc.simulation.dto.CellUpdateRequest;
import com.fcc.simulation.dto.InventoryRowDto;
import com.fcc.simulation.service.InventoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping
    public List<InventoryRowDto> getAll() {
        return service.findAll();
    }

    @PatchMapping("/cells")
    public ResponseEntity<List<InventoryRowDto>> updateCells(@RequestBody List<CellUpdateRequest> updates) {
        if (updates == null || updates.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(service.updateCells(updates));
    }
}
