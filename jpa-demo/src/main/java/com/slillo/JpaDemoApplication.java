package com.slillo;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import com.slillo.model.Categoria;
import com.slillo.model.Perfil;
import com.slillo.model.Usuario;
import com.slillo.model.Vacante;
import com.slillo.repository.CategoriaRepository;
import com.slillo.repository.PerfilRepository;
import com.slillo.repository.UsuarioRepository;
import com.slillo.repository.VacanteRepository;

@SpringBootApplication
public class JpaDemoApplication implements CommandLineRunner {
	
	@Autowired
	private CategoriaRepository repoCategorias;
	
	@Autowired
	private VacanteRepository repoVacante;
	
	@Autowired
	private UsuarioRepository repoUsuario;
	
	@Autowired
	private PerfilRepository repoPerfil;
	

	public static void main(String[] args) {
		SpringApplication.run(JpaDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		buscarVacantesPorVariosEstatus();
	}
	
	private void buscarVacantesPorVariosEstatus() {
		String[] estatus = new String[] {"Eliminada", "Creada"};
		List<Vacante> lista = repoVacante.findByEstatusIn(estatus);
		System.out.println("Registros encontrados: " + lista.size());
		
		for(Vacante v:lista) {
			System.out.println(v.getId() + ": " + v.getNombre() + ": " + v.getEstatus());
		}
	}
	
	private void buscarVacantesPorSalario() {
		List<Vacante> lista = repoVacante.findBySalarioBetweenOrderBySalarioDesc(7000, 14000);
		System.out.println("Registros encontrados: " + lista.size());
		
		for(Vacante v:lista) {
			System.out.println(v.getId() + ": " + v.getNombre() + ": " + v.getSalario() + "$");
		}
	}
	
	private void buscarVacantesPorDestacadoEstatus() {
		List<Vacante> lista = repoVacante.findByDestacadoAndEstatusOrderByIdDesc(1, "Aprobada");
		System.out.println("Registros encontrados: " + lista.size());
		
		for(Vacante v:lista) {
			System.out.println(v.getId() + ": " + v.getNombre() + ": " + v.getEstatus() + ": " + v.getDestacado());
		}
	}
	
	private void buscarVacantesPorEstatus() {
		List<Vacante> lista = repoVacante.findByEstatus("Aprobada");
		System.out.println("Registros encontrados: " + lista.size());
		
		for(Vacante v:lista) {
			System.out.println(v.getId() + ": " + v.getNombre() + ": " + v.getEstatus());
		}
	}
	
	public void crearUsuarioCon2Perfiles() {
		Usuario user = new Usuario();
		user.setNombre("Iván");
		user.setEmail("ivantinaj@gmail.com");
		user.setFechaRegistro(new Date());
		user.setUsername("itinajero");
		user.setPassword("12345");
		user.setEstatus(1);
		
		Perfil perf1 = new Perfil();
		perf1.setId(2);
		
		Perfil perf2 = new Perfil();
		perf2.setId(3);
		
		user.agregar(perf1);
		user.agregar(perf2);
		
		repoUsuario.save(user);
	}
	
	public void buscarUsuario() {
		Optional<Usuario> optional = repoUsuario.findById(1);
		
		if(optional.isPresent()) {
			Usuario user = optional.get();
			System.out.println("Usuario: " + user.getNombre());
			System.out.print("Perfiles asignados: ");
			for(Perfil perf: user.getPerfiles()) {
				System.out.print(" " + perf.getPerfil());
			}
			System.out.println();

		} else {
			System.out.println("Usuario no encontrado.");
		}
	}
	
	public void crearPerfilesAplicacion() {
		repoPerfil.saveAll(getPerfilesAplicacion());	
	}
	
	
	private void buscarVacantes() {
		List<Vacante> lista = repoVacante.findAll();
		
		for(Vacante vacante: lista) {
			System.out.println(vacante.getId() + " " + vacante.getNombre() + " - " + vacante.getCategoria().getNombre());
		}
		
	}
	
	private void guardarVacante() {
		Vacante vacante = new Vacante();
		vacante.setNombre("Profesor de matemáticas");
		vacante.setDescripcion("Escuela primaria solicita profesor para curso de Matemáticas.");
		vacante.setFecha(new Date());
		vacante.setSalario(8500.00);
		vacante.setEstatus("Aprobada");
		vacante.setDestacado(0);
		vacante.setImagen("escuela.png");
		vacante.setDetalles("<h1>Los requisitos para el profesor de Matemáticas.</h1>");
		
		Categoria cat = new Categoria();
		cat.setId(15);
		
		vacante.setCategoria(cat);
		
		repoVacante.save(vacante);
		
		System.out.println(vacante);
	}
	
	private void guardar() {
		Categoria categoria = new Categoria();
		categoria.setNombre("Sanidad");
		categoria.setDescripcion("Trabajos relacionados con sanidad");
		
		repoCategorias.save(categoria);
		
		System.out.println(categoria);
	}
	
	private void guardarTodos() {
		List<Categoria> categorias = getListaCategorias();
		
		repoCategorias.saveAll(categorias);
		
	}
	
	private void eliminar() {
		int idCategoria = 1;
		repoCategorias.deleteById(idCategoria);
		
	}
	
	private void buscarPorId() {
		Optional<Categoria> opt = repoCategorias.findById(3);
		if(opt.isPresent()) {
			System.out.println(opt);
		} else {
			System.err.println("No se encontró la categoría indicada");
		}
	}
	
	private void modificar() {
		Optional<Categoria> opt = repoCategorias.findById(2);
		if(opt.isPresent()) {
			Categoria catTmp = opt.get();
			catTmp.setNombre("Ingeniería de Software");
			catTmp.setDescripcion("Desarrollo de sistemas");
			repoCategorias.save(catTmp);
			System.out.println(opt.get());
		} else {
			System.err.println("La categoría indicada no existe.");
		}
	}
	
	private void conteo() {
		Long cont = repoCategorias.count();
		System.err.println("Total de Categorías: " + cont);
	}
	
	// Usar con precaución
	private void eliminarTodos() {
		repoCategorias.deleteAll();
	}
	
	// Usar con precaución
	private void borrarTododsEnBloqueJPA() {
		repoCategorias.deleteAllInBatch();
	}
	
	private void encontrarTodosPorId() {
		List<Integer> ids = new LinkedList<Integer>();
		ids.add(1);
		ids.add(4);		
		ids.add(10);

		Iterable<Categoria> cat = repoCategorias.findAllById(ids);
		for(Categoria cat2: cat) {
			System.out.println(cat2);
		}
		
	}
	
	private void buscarTodos() {
		Iterable<Categoria> cat = repoCategorias.findAll();
		for(Categoria cat2: cat) {
			System.out.println(cat2);
		}
	}
	
	private void buscarTodosJPA() {
		List<Categoria> cat = repoCategorias.findAll();
		for(Categoria cat2: cat) {
			System.out.println(cat2.getId() + " " + cat2.getNombre());
		}
	}
	
	// PAGINACION y ORDEN
	
	private void buscarTodosOrdenadosJPA() {
		List<Categoria> cat = repoCategorias.findAll(Sort.by("nombre").descending());
		for(Categoria cat2: cat) {
			System.out.println(cat2.getId() + " " + cat2.getNombre());
		}
	}
	
	private void buscarTodosConPaginacionJPA() {
		Page<Categoria> page = repoCategorias.findAll(PageRequest.of(1, 5));
		System.out.println("Total registros: " + page.getTotalElements());
		System.out.println("Total páginas: " + page.getTotalPages());

		for(Categoria cat2: page.getContent()) {
			System.out.println(cat2.getId() + " " + cat2.getNombre());
		}
	}
	
	private void buscarTodosConPaginacionYOrdenJPA() {
		Page<Categoria> page = repoCategorias.findAll(PageRequest.of(1, 5, Sort.by("nombre")));
		System.out.println("Total registros: " + page.getTotalElements());
		System.out.println("Total páginas: " + page.getTotalPages());

		for(Categoria cat2: page.getContent()) {
			System.out.println(cat2.getId() + " " + cat2.getNombre());
		}
	}
	
	
	
	private void existeId() {
		Boolean bool = repoCategorias.existsById(44);
		System.out.println("¿La categoría existe? " + bool);
	}
	
	private List<Categoria> getListaCategorias() {
		
		Categoria categoria = new Categoria();
		categoria.setNombre("Sanidad2");
		categoria.setDescripcion("Trabajos relacionados con sanidad");
		
		Categoria categoria2 = new Categoria();
		categoria2.setNombre("Sanidad3");
		categoria2.setDescripcion("Trabajos relacionados con sanidad");
		
		Categoria categoria3 = new Categoria();
		categoria3.setNombre("Sanidad4");
		categoria3.setDescripcion("Trabajos relacionados con sanidad");
		
		List<Categoria> categorias = new LinkedList<Categoria>();
		categorias.add(categoria);
		categorias.add(categoria2);		
		categorias.add(categoria3);

		return categorias;
	}
	
	private List<Perfil> getPerfilesAplicacion() {
		
		List<Perfil> lista = new LinkedList<Perfil>();
		
	
		Perfil perfil1 = new Perfil();
		perfil1.setPerfil("SUPERVISOR");
		
		Perfil perfil2 = new Perfil();
		perfil2.setPerfil("ADMINISTRADOR");
		
		Perfil perfil3 = new Perfil();
		perfil3.setPerfil("USUARIO");
		
		lista.add(perfil1);
		lista.add(perfil2);
		lista.add(perfil3);
		
		return lista;
	}

}
