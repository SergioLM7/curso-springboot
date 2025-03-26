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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.sergio.model.Vacante;
import net.sergio.service.ICategoriaService;
import net.sergio.service.IVacanteService;
import net.sergio.utils.Utils;

@Controller
@RequestMapping("/vacantes")
public class VacanteController {

    @Autowired
    private IVacanteService serviceVacantes;

    @Autowired
    private ICategoriaService serviceCategorias;

    @GetMapping("/index")
    public String mostrarIndex(Model model) {
        List<Vacante> vacantes = serviceVacantes.buscarTodas();
        model.addAttribute("vacantes", vacantes);

        return "vacantes/listVacantes";
    }

    @GetMapping("/create")
    public String crear(Vacante vacante, Model model) {

        return "vacantes/formVacante";
    }

    @PostMapping("/save")
    public String guardar(Vacante vacante, BindingResult result, RedirectAttributes attributes,
            @RequestParam("archivoImagen") MultipartFile multiPart) {

        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                System.out.println("Ocurrió un error: " + error.getDefaultMessage());
            }

            return "vacantes/formVacante";
        }

        if (!multiPart.isEmpty()) {
            String ruta = "/empleos/img-vacantes/"; // Linux/MAC
            String nombreImagen = Utils.guardarArchivo(multiPart, ruta);

            if (nombreImagen != null) {
                vacante.setImagen(nombreImagen);
            }
        }

        serviceVacantes.guardar(vacante);
        attributes.addFlashAttribute("msg", "Registro guardado");
        System.out.println("Vacante: " + vacante);

        return "redirect:/vacantes/index";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") int idVacante, RedirectAttributes attributes) {

        System.out.println("Borrando vacante con id " + idVacante);

        serviceVacantes.eliminar(idVacante);
        attributes.addFlashAttribute("msg", "La vacante " + idVacante + " fue eliminada");

        return "redirect:/vacantes/index";

    }

    @GetMapping("/edit/{id}")
    public String editar(@PathVariable("id") int idVacante, Model model) {

        Vacante vacante = serviceVacantes.buscarPorId(idVacante);
        model.addAttribute("vacante", vacante);

        return "vacantes/formVacante";

    }

    @GetMapping("/view/{id}")
    public String verDetalle(@PathVariable("id") int idVacante, Model model) {

        Vacante vacante = serviceVacantes.buscarPorId(idVacante);

        System.out.println("Vacante: " + vacante);
        model.addAttribute("vacante", vacante);

        // Buscar los detalles de la vacante en la BBDD...

        return "detalle";
    }

    @ModelAttribute
    public void setGenericos(Model model) {
        model.addAttribute("categorias", serviceCategorias.buscarTodas());

    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

}
