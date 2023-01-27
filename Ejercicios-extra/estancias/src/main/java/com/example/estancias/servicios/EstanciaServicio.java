/*
        * Esta clase tiene la responsabilidad de llevar adelante las funcionalidades
        * necesarias para realizar las reservas de viviendas (reservar, consultar
        * reservas realizadas, modificación y eliminación).
 */
package com.example.estancias.servicios;

import com.example.estancias.entidades.Casa;
import com.example.estancias.entidades.Cliente;
import com.example.estancias.entidades.Estancia;
import com.example.estancias.excepciones.MiException;
import com.example.estancias.repositorios.CasaRepositorio;
import com.example.estancias.repositorios.ClienteRepositorio;
import com.example.estancias.repositorios.EstanciaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstanciaServicio {

    @Autowired
    EstanciaRepositorio estanciaRepositorio;

    @Autowired
    ValidacionServicio validacionServicio;

    @Autowired
    ClienteRepositorio clienteRepositorio;

    @Autowired
    CasaRepositorio casaRepositorio;

    @Transactional
    public void crearEstancia(String huesped, Date fechaDesde, Date fechaHasta, String idCliente, String idCasa) throws MiException {

        validacionServicio.validarHuesped(huesped);
        validacionServicio.validarFecha(fechaDesde);
        validacionServicio.validarFecha(fechaHasta);

        //Encuentra el id por cliente 
        Optional<Cliente> respuestaCliente = clienteRepositorio.findById(idCliente);
        Optional<Casa> respuestaCasa = casaRepositorio.findById(idCasa);

        //optional por si el id existe o no y si contiene algun error
        //objetos cliente y casa
        Cliente cliente = new Cliente();
        Casa casa = new Casa();

        if (respuestaCliente.isPresent()) {
            cliente = respuestaCliente.get();
        }

        if (respuestaCasa.isPresent()) {
            casa = respuestaCasa.get();
        }

        //creo el objeto Estancia
        Estancia estancia = new Estancia();

        //seteo los parametros
        estancia.setHuesped(huesped);
        estancia.setFechaDesde(fechaDesde);
        estancia.setFechaHasta(fechaHasta);
        estancia.setCliente(cliente);
        estancia.setCasa(casa);

        //persisto - guardo en BD
        estanciaRepositorio.save(estancia);
    }

    //Metodo listar reservas
    public List<Estancia> listarEstancia() {

        List<Estancia> listaEstancias = new ArrayList<>();

        // Encuentra reservas dentro de repositorio, los mete en arraylist listaReservas
        listaEstancias = estanciaRepositorio.findAll();

        return listaEstancias;
    }

    //Metodo modificar Estancias
    @Transactional
    public void modificarEstancias(String huesped, Date fechaDesde, Date fechaHasta, String idCliente, String idCasa) throws MiException {
        validacionServicio.validarHuesped(huesped);
        validacionServicio.validarFecha(fechaDesde);
        validacionServicio.validarFecha(fechaHasta);

        // Encuentra por id Cliente y Familia, los trae y guarda en objetos
        Optional<Cliente> respuestaCliente = clienteRepositorio.findById(idCliente);
        Optional<Casa> respuestaCasa = casaRepositorio.findById(idCasa);
        // Optional = Por si id existe o no y si contiene algún error

        // Objetos Cliente y Casa
        Cliente cliente = new Cliente();
        Casa casa = new Casa();
    }
    
    @Transactional
    public void eliminarEstancias( String id) throws MiException{
        Estancia estancia = estanciaRepositorio.getById(id);
        estanciaRepositorio.delete(estancia);
    }
    
}
