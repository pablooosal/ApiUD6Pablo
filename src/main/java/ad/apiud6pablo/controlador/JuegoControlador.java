package ad.apiud6pablo.controlador;

import ad.apiud6pablo.modelo.Juego;
import ad.apiud6pablo.repositorio.JuegoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/juego")
public class JuegoControlador {
    @Autowired
    private JuegoRepositorio juegoRepositorio;

    //añadir juego
    @PostMapping
    public Juego crearJuego(@RequestBody Juego juego){
        return juegoRepositorio.save(juego);
    }

    //mostrar todos los juegos
    @GetMapping
    public List<Juego> mostrarTodosJuegos(){
        return juegoRepositorio.findAll();
    }

    //mostrar Juego por id
    @GetMapping ("/{id}")
    public Juego mostrarJuegoPorId(@PathVariable Long id){
        Optional<Juego> resultado = juegoRepositorio.findById(id);
        return resultado.orElseThrow(()->
                new RuntimeException("Juego no encontrado"));
    }

    //eliminar juego
    @DeleteMapping ("/{id}")
    public void eliminarJuego(@PathVariable Long id){
        juegoRepositorio.deleteById(id);
    }

    //modificar juego
    @PutMapping ("/{id}")
    public Juego modificarJuego(@PathVariable Long id, @RequestBody Juego juego){
        return juegoRepositorio.findById(id)
                .map(juegoTemp -> {
                    juegoTemp.setNombre((juego.getNombre()!=null)? juego.getNombre(): juegoTemp.getNombre());
                    return juegoRepositorio.save(juegoTemp);
                }).orElseThrow(()-> new RuntimeException("Juego no encontrado."));
    }



}
