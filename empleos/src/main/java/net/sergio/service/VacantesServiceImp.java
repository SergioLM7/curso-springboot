package net.sergio.service;

import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import net.sergio.model.Vacante;

@Service
public class VacantesServiceImp implements IVacantesService {

    private List<Vacante> lista = null;

    public VacantesServiceImp() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        lista = new LinkedList<Vacante>();

        try {

            // Oferta de Trabajo 1
            Vacante vacante1 = new Vacante();
            vacante1.setId(1);
            vacante1.setNombre("Ingeniero Civil");
            vacante1.setDescripcion("Se solicita ingeniero civil para diseñar puente peatonal.");
            vacante1.setFecha(sdf.parse("08-02-2019"));
            vacante1.setSalario(14000.00);
            vacante1.setEstatus("Aprobada");
            vacante1.setDestacado(1);

            // Oferta de Trabajo 2
            Vacante vacante2 = new Vacante();
            vacante2.setId(2);
            vacante2.setNombre("Contador público");
            vacante2.setDescripcion("Se solicita ingeniero civil para diseñar puente peatonal.");
            vacante2.setFecha(sdf.parse("08-03-2019"));
            vacante2.setSalario(1200.00);
            vacante2.setEstatus("Creada");
            vacante2.setDestacado(0);

            // Oferta de Trabajo 3
            Vacante vacante3 = new Vacante();
            vacante3.setId(3);
            vacante3.setNombre("Ingeniero eléctrico");
            vacante3.setDescripcion("Se solicita ingeniero civil para diseñar puente peatonal.");
            vacante3.setFecha(sdf.parse("12-03-2019"));
            vacante3.setSalario(4500.00);
            vacante3.setEstatus("Eliminada");
            vacante3.setDestacado(0);

            // Oferta de Trabajo 4
            Vacante vacante4 = new Vacante();
            vacante4.setId(4);
            vacante4.setNombre("Diseñador gráfico");
            vacante4.setDescripcion("Se solicita diseñador gráfico para diseñar poster.");
            vacante4.setFecha(sdf.parse("12-03-2019"));
            vacante4.setSalario(4500.00);
            vacante4.setEstatus("Aprobada");
            vacante4.setDestacado(1);

            /*
             * Agregamos los objetos a la lista
             */
            lista.add(vacante1);
            lista.add(vacante2);
            lista.add(vacante3);
            lista.add(vacante4);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @Override
    public List<Vacante> buscarTodas() {
        return lista;
    }

    @Override
    public Vacante buscarPorId(Integer idVacante) {

        for (Vacante vacante : lista) {
            if (vacante.getId() == idVacante) {
                return vacante;
            }
        }

        return null;
    }

    @Override
    public void guardar(Vacante vacante) {
        lista.add(vacante);
    }

    @Override
    public List<Vacante> buscarDestacadas() {
        // TODO Auto-generated method stub
        return null;
    }

}
