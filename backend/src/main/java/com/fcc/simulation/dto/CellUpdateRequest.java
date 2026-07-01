package com.fcc.simulation.dto;

public record CellUpdateRequest(
        Long id,
        String year,
        String value
) {}
