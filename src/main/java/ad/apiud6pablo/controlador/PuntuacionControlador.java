package ad.apiud6pablo.controlador;

import ad.apiud6pablo.modelo.Puntuacion;
import ad.apiud6pablo.repositorio.JuegoRepositorio;
import ad.apiud6pablo.repositorio.PuntuacionRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/puntuaciones")
public class PuntuacionControlador {

    @Autowired
    private PuntuacionRepositorio puntuacionRepositorio;
    @Autowired
    private JuegoRepositorio juegoRepositorio;

    //Listar las puntuaciones y sus juegos.
    @GetMapping
    public List<Puntuacion> listarPuntuaciones() {
        return puntuacionRepositorio.findAll();
    }

    //Listar las puntuaciones de un juego
    @GetMapping("/juego/{idJuego}")
    public List<Puntuacion> listarPuntuacionesJuego(@PathVariable Long idJuego) {
        return puntuacionRepositorio.findByJuegoId(idJuego);
    }

    //Añadir una puntuación
    @PostMapping("/juego/{id}")
    public Puntuacion crearPuntuacion(@PathVariable Long id, @RequestBody Puntuacion puntuacion) {
        Puntuacion put = juegoRepositorio.findById(id).map(juego -> {
            puntuacion.setJuego(juego);
            return puntuacionRepositorio.save(puntuacion);
        }).orElseThrow(() -> new RuntimeException("Juego no encontrado"));
        return put;
    }

    //Editar una puntuacion
    @PutMapping("/{id}")
    public Puntuacion editarPuntuacion(@PathVariable Long id, @RequestBody Puntuacion puntuacion) {
        return puntuacionRepositorio.findById(id).map(puntuacionTemp -> {
            puntuacionTemp.setNombre(!puntuacion.getNombre().isEmpty()? puntuacion.getNombre(): puntuacionTemp.getNombre());
            puntuacionTemp.setPuntuacion((puntuacion.getPuntuacion() > 0)? puntuacion.getPuntuacion(): puntuacionTemp.getPuntuacion());
            return puntuacionRepositorio.save(puntuacionTemp);
        }).orElseThrow(() -> new RuntimeException("Jugador no encontrado"));
    }

    //Eliminar una puntuacion
    @DeleteMapping("/{id}")
    public void eliminarPuntuacion(@PathVariable Long id) {
        puntuacionRepositorio.deleteById(id);
    }

}
