package com.fcc.simulation.dto;

import java.util.Map;

public record InventoryRowDto(
        Long id,
        String ageLabel,
        String distYear,
        String ttl,
        Map<String, String> yearValues
) {}
