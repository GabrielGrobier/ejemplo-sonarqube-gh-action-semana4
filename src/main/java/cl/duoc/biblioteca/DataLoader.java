package cl.duoc.biblioteca;

import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final PrestamoRepository repository;

    public DataLoader(PrestamoRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        if (repository.count() == 0) {
            repository.save(new Prestamo("Camila Torres", "Clean Code", LocalDate.now().minusDays(20), LocalDate.now().minusDays(5), LocalDate.now().minusDays(1), 500));
            repository.save(new Prestamo("Diego Pérez", "Spring Boot in Action", LocalDate.now().minusDays(10), LocalDate.now().plusDays(5), LocalDate.now(), 500));
        }
    }
}
