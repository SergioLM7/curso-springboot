package net.sergio.service.DB;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import net.sergio.model.Categoria;
import net.sergio.repository.CategoriaRepository;
import net.sergio.service.ICategoriasService;

@Service
@Primary
public class CategoriasServiceJPA implements ICategoriasService {

	
	@Autowired
	private CategoriaRepository categoriaRepo;


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
		if(optional.isPresent()) {
			return optional.get();
		}
		
		return null;
	}

}
