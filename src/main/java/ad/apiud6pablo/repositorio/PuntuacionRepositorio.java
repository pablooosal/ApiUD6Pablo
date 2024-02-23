package ad.apiud6pablo.repositorio;

import ad.apiud6pablo.modelo.Puntuacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PuntuacionRepositorio extends JpaRepository<Puntuacion, Long> {
    List<Puntuacion> findByJuegoId(Long idJuego);
}
