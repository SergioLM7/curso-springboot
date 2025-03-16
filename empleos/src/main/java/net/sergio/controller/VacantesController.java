package net.sergio.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.sergio.model.Vacante;
import net.sergio.service.ICategoriasService;
import net.sergio.service.IVacantesService;
import net.sergio.utils.Utils;

@Controller
@RequestMapping("/vacantes")
public class VacantesController {
	
	@Autowired
	private IVacantesService serviceVacantes;
	
	@Autowired
	private ICategoriasService serviceCategorias;
	
	@GetMapping("/index")
	public String mostrarIndex(Model model) {
		List<Vacante> vacantes = serviceVacantes.buscarTodas();
		model.addAttribute("vacantes", vacantes);
		
		return "vacantes/listVacantes";
	}
	
	@GetMapping("/create")
	public String crear(Vacante vacante, Model model) {
		
		model.addAttribute("categorias", serviceCategorias.buscarTodas());
		
		return "vacantes/formVacante";
	}
	
	@PostMapping("/save")
	public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes, @RequestParam("archivoImagen") MultipartFile multiPart) {
		
		if(result.hasErrors()) {
			for (ObjectError error: result.getAllErrors()){
					System.out.println("Ocurrió un error: " + error.getDefaultMessage());
				}
			
			return "vacantes/formVacante";
		}
		
		if (!multiPart.isEmpty()) {
				String ruta = "/empleos/img-vacantes/"; // Linux/MAC
				String nombreImagen = Utils.guardarArchivo(multiPart, ruta);
				
				if (nombreImagen != null){ // La imagen si se subió
					// Procesamos la variable nombreImagen
					vacante.setImagen(nombreImagen);
				}
		}
		
		serviceVacantes.guardar(vacante);
		attributes.addFlashAttribute("msg", "Registro guardado");
		System.out.println("Vacante: " + vacante);

		return "redirect:/vacantes/index";
	}
	
	/*@PostMapping("/save")
	public String guardar(@RequestParam("nombre") String nombre, @RequestParam("descripcion") String descripcion, @RequestParam("estatus") String estatus, 
			@RequestParam("fecha") String fecha, @RequestParam("destacado") int destacado, @RequestParam("salario") double salario, @RequestParam("detalles") String detalles) {
		
		System.out.println("Nombre: " + nombre);
		System.out.println("Descripción: " + descripcion);
		System.out.println("Estatus: " + estatus);
		System.out.println("Fecha: " + fecha);
		System.out.println("Destacado: " + destacado);
		System.out.println("Salario: " + salario);
		System.out.println("Detalles: " + detalles);
		
		return "vacantes/listVacantes";
	}*/
	
	@GetMapping("/delete")
	public String eliminar(@RequestParam("id") int idVacante, Model model) {

		System.out.println("Borrando vacante con id " + idVacante);
			
		model.addAttribute("id", idVacante);
		return "mensaje";
		
		
	}
	
	@GetMapping("/view/{id}")
	public String verDetalle(@PathVariable("id") int idVacante, Model model) {
		
		Vacante vacante = serviceVacantes.buscarPorId(idVacante);
		
		System.out.println("Vacante: " + vacante);
		model.addAttribute("vacante", vacante);
		
		
		//Buscar los detalles de la vacante en la BBDD...
		
		return "detalle";
	}
	
	@InitBinder
	public void initBinder(WebDataBinder webDataBinder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}

}
