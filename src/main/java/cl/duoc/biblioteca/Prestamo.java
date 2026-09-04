package cl.duoc.biblioteca;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;

@Entity
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String estudiante;
    private String libro;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;
    private Integer valorMultaDiaria;

    public Prestamo() {}

    public Prestamo(String estudiante, String libro, LocalDate fechaPrestamo, LocalDate fechaDevolucionEsperada, LocalDate fechaDevolucionReal, Integer valorMultaDiaria) {
        this.estudiante = estudiante;
        this.libro = libro;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
        this.valorMultaDiaria = valorMultaDiaria;
    }

    public Long getId() { return id; }
    public String getEstudiante() { return estudiante; }
    public String getLibro() { return libro; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public LocalDate getFechaDevolucionEsperada() { return fechaDevolucionEsperada; }
    public LocalDate getFechaDevolucionReal() { return fechaDevolucionReal; }
    public Integer getValorMultaDiaria() { return valorMultaDiaria; }
}
