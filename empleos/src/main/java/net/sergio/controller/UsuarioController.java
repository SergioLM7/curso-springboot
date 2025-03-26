package net.sergio.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import net.sergio.model.Usuario;
import net.sergio.service.DB.UsuarioServiceJPA;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioServiceJPA usuarioService;

    @GetMapping("/index")
    public String mostrarIndex(Model model) {

        List<Usuario> listaUsuarios = usuarioService.buscarTodos();

        model.addAttribute("usuarios", listaUsuarios);

        return "usuarios/listUsuarios";
    }

    @GetMapping("/delete/{id}")
    public String eliminar(@PathVariable("id") int idUsuario, RedirectAttributes attribues) {

        usuarioService.eliminar(idUsuario);

        return "redirect:/usuarios/index";
    }

}
