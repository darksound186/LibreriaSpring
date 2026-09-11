package com.example.demo.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LibroRequest(
        @NotBlank(message = "El nombre es obligatorio") String nombre,
        @NotBlank(message = "El autor es obligatorio") String autor,
        @NotNull(message = "El anio es obligatorio")
        @Min(value = 1, message = "El anio debe ser positivo")
        @Max(value = 2100, message = "El anio no puede ser mayor que 2100") Integer anio
) {
}
