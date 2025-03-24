package net.sergio.service.DB;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sergio.model.Perfil;
import net.sergio.model.Usuario;
import net.sergio.repository.UsuarioRepository;
import net.sergio.service.IUsuarioService;

@Service
public class UsuarioServiceJPA implements IUsuarioService {

    @Autowired
    UsuarioRepository userRepo;

    @Override
    public void guardar(Usuario usuario) {

        usuario.setFechaRegistro(Date.valueOf(LocalDate.now()));
        usuario.agregar(new Perfil());
        usuario.getPerfiles().getFirst().setId(3);

        userRepo.save(usuario);

    }

    @Override
    public void eliminar(Integer idUsuario) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<Usuario> buscarTodos() {
        return userRepo.findAll();
    }

}
