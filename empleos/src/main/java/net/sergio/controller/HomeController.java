package net.sergio.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import net.sergio.model.Vacante;
import net.sergio.service.IVacantesService;

@Controller
public class HomeController {
	
	@Autowired
	private IVacantesService serviceVacantes;
	
	@GetMapping("/tabla")
	public String mostrarTabla(Model model) {
		List<Vacante> lista = serviceVacantes.buscarTodas();
		
		model.addAttribute("vacantes", lista);
		
		return "tabla";
	}
	
	@GetMapping("/detalle")
	public String mostrarDetalle(Model model) {
		
		Vacante vacante = new Vacante();
		vacante.setNombre("Ingeniero de Telecomunicaciones");
		vacante.setDescripcion("Se solicita ingeniero para dar soporte a Internet");
		vacante.setFecha(new Date());
		vacante.setSalario(9700.00);
		
		model.addAttribute("vacante", vacante);
		
		return "detalle";
		
	}
	
	@GetMapping("/listado")
	public String mostrarListado(Model model) {
		
		List<String> lista = new LinkedList<String>();
		lista.add("Ingeniero de Sistemas");
		lista.add("Auxiliar de Contabilidad");
		lista.add("Vendedor");
		lista.add("Arquitecto");
		
		model.addAttribute("empleos", lista);
		
		return "listado";

	}
	
	@GetMapping("/")
	public String mostrarHome(Model model) {
		
		/*
		model.addAttribute("mensaje", "¡Bienvenidos a Empleos App!");
		model.addAttribute("fecha", new Date());
		*/

		String nombre = "Auxiliar de Contabilidad";
		Date fechaPub = new Date();
		double salario = 9000.00;
		boolean vigente = true;
		
		model.addAttribute("nombre", nombre);
		model.addAttribute("fecha", fechaPub);
		model.addAttribute("salario", salario);
		model.addAttribute("vigente", vigente);


		
		return "home";	
	}
	

}
