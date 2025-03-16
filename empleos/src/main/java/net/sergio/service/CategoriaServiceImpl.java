package net.sergio.service;

import java.util.LinkedList;

import org.springframework.stereotype.Service;

import net.sergio.model.Categoria;

@Service
public class CategoriaServiceImpl implements ICategoriasService {
	
	private LinkedList<Categoria> lista = null;
	
	public CategoriaServiceImpl() {
		
		lista = new LinkedList<Categoria>();
		
		Categoria categoria1 = new Categoria();
		categoria1.setId(1);
		categoria1.setNombre("Contabilidad");
		categoria1.setDescripcion("Área de contabilidad de la empresa");
		
		Categoria categoria2 = new Categoria();
		categoria2.setId(2);
		categoria2.setNombre("Ingeniería");
		categoria2.setDescripcion("Área de Ingeniería de la empresa");
		
		Categoria categoria3 = new Categoria();
		categoria3.setId(3);
		categoria3.setNombre("Marketing");
		categoria3.setDescripcion("Área de marketing de la empresa");
		
		Categoria categoria4 = new Categoria();
		categoria4.setId(4);
		categoria4.setNombre("RRHH");
		categoria4.setDescripcion("Área de recursos humanos de la empresa");
		
		Categoria categoria5 = new Categoria();
		categoria5.setId(5);
		categoria5.setNombre("Arquitectura");
		categoria5.setDescripcion("Área de arquitectura software de la empresa");
		
		lista.add(categoria1);
		lista.add(categoria2);
		lista.add(categoria3);
		lista.add(categoria4);
		lista.add(categoria5);

		
	}


	@Override
	public void guardar(Categoria categoria) {
		lista.add(categoria);
	}

	@Override
	public LinkedList<Categoria> buscarTodas() {
		return lista;
	}

	@Override
	public Categoria buscarPorId(Integer idCategoria) {
		
		for(Categoria categoria:lista) {
			if(categoria.getId() == idCategoria) {
				return categoria;
			}
		}
		
		return null;
	}
	
	

}
