package cl.duoc.biblioteca;

public record ResumenPrestamo(
        Long idPrestamo,
        String estudiante,
        String libro,
        long diasAtraso,
        int valorMultaDiaria,
        long totalMulta,
        String estado
) {}
