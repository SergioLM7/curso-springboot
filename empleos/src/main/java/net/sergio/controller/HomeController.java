package net.sergio.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.sergio.model.Usuario;
import net.sergio.model.Vacante;
import net.sergio.service.ICategoriaService;
import net.sergio.service.IVacanteService;
import net.sergio.service.DB.UsuarioServiceJPA;

@Controller
public class HomeController {

    @Autowired
    private IVacanteService serviceVacantes;

    @Autowired
    private UsuarioServiceJPA userService;

    @Autowired
    private ICategoriaService serviceCategoria;

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
        return "home";
    }

    @GetMapping("/signup")
    public String registrarse(Usuario usuario) {
        return "formRegistro";

    }

    @PostMapping("/signup")
    public String guardarRegistro(Usuario usuario, BindingResult result, RedirectAttributes attributes) {

        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println("Ocurrió un error: " + error.getDefaultMessage());
            }

            return "formRegistro";
        }

        userService.guardar(usuario);
        attributes.addFlashAttribute("msg", "Usuario guardado");

        System.out.println("Usuario: " + usuario);

        return "redirect:/usuarios/index";
    }

    @GetMapping("/search")
    public String buscar(@ModelAttribute("search") Vacante vacante, Model model) {

        System.out.println("Buscando por " + vacante);

        Example<Vacante> example = Example.of(vacante);

        List<Vacante> lista = serviceVacantes.searchByExample(example);

        model.addAttribute("vacantes", lista);
        return "home";

    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute
    public void setGenericos(Model model) {
        Vacante vacanteSearch = new Vacante();
        vacanteSearch.reset();
        model.addAttribute("vacantes", serviceVacantes.buscarDestacadas());
        model.addAttribute("categorias", serviceCategoria.buscarTodas());
        model.addAttribute("search", vacanteSearch);
    }

}
