package net.sergio.service.DB;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import net.sergio.model.Categoria;
import net.sergio.model.Vacante;
import net.sergio.repository.CategoriaRepository;
import net.sergio.repository.VacanteRepository;
import net.sergio.service.ICategoriasService;

@Service
@Primary
public class CategoriasServiceJPA implements ICategoriasService {

    @Autowired
    private CategoriaRepository categoriaRepo;

    @Autowired
    private VacanteRepository vacanteRepo;

    @Override
    public void guardar(Categoria categoria) {
        categoriaRepo.save(categoria);
    }

    @Override
    public List<Categoria> buscarTodas() {
        return categoriaRepo.findAll();
    }

    @Override
    public Categoria buscarPorId(Integer idCategoria) {
        Optional<Categoria> optional = categoriaRepo.findById(idCategoria);
        if (optional.isPresent()) {
            return optional.get();
        }

        return null;
    }

    @Override
    @Transactional
    public void eliminar(Integer idCategoria) {

        if (vacanteRepo.findAll().stream().anyMatch(v -> v.getCategoria().getId() == idCategoria)) {
            List<Vacante> vacantesWithCategoria = vacanteRepo.findAll().stream()
                    .filter(v -> v.getCategoria() != null && v.getCategoria().getId() == idCategoria)
                    .collect(Collectors.toList());

            for (Vacante vacante : vacantesWithCategoria) {
                vacante.setCategoria(null);
                vacanteRepo.save(vacante);
            }

            categoriaRepo.deleteById(idCategoria);

        }

    }

}
