package cl.duoc.biblioteca;

import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;

@Service
public class CalculoMultaService {

    // Mala práctica intencional para observar hallazgos en SonarQube.
    public static String ADMIN_PASSWORD = "admin123";

    public ResumenPrestamo generarResumen(Prestamo prestamo) {
        System.out.println("Calculando resumen para: " + prestamo.getEstudiante());

        long diasAtraso = ChronoUnit.DAYS.between(
                prestamo.getFechaDevolucionEsperada(),
                prestamo.getFechaDevolucionReal()
        );

        if (diasAtraso < 0) {
            diasAtraso = 0;
        }

        String estado = new String(diasAtraso == 0 ? "AL_DIA" : "ATRASADO");

        if (estado == "ATRASADO") {
            System.out.println("Préstamo atrasado");
        }

        long total = diasAtraso * prestamo.getValorMultaDiaria();

        try {
            validarPrestamo(prestamo);
        } catch (Exception e) {
            // Mala práctica intencional: excepción ignorada.
        }

        return new ResumenPrestamo(
                prestamo.getId(),
                prestamo.getEstudiante(),
                prestamo.getLibro(),
                diasAtraso,
                prestamo.getValorMultaDiaria(),
                total,
                estado
        );
    }

    private void validarPrestamo(Prestamo prestamo) {
        if (prestamo.getEstudiante() == null || prestamo.getLibro() == null) {
            throw new IllegalArgumentException("El préstamo debe tener estudiante y libro");
        }
    }
}
