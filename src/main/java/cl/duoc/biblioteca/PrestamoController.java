package cl.duoc.biblioteca;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoRepository repository;
    private final CalculoMultaService calculoMultaService;

    public PrestamoController(PrestamoRepository repository, CalculoMultaService calculoMultaService) {
        this.repository = repository;
        this.calculoMultaService = calculoMultaService;
    }

    @GetMapping
    public List<Prestamo> listar() {
        return repository.findAll();
    }

    @GetMapping("/{id}/resumen")
    public ResponseEntity<ResumenPrestamo> resumen(@PathVariable Long id) {
        return repository.findById(id)
                .map(calculoMultaService::generarResumen)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
