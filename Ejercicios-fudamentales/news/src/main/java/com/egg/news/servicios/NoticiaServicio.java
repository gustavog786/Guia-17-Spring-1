
package com.egg.news.servicios;

import com.egg.news.entidades.Noticia;
import com.egg.news.excepciones.MiException;
import com.egg.news.repositorios.NoticiaRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoticiaServicio {
    
    @Autowired  //
    private NoticiaRepositorio noticiaRepositorio;
    
    @Transactional // para metodos que generan cambios en la BD
    public void crearNoticia(String titulo, String cuerpo) throws MiException{
        
        validar(titulo,cuerpo);
        Noticia noticia = new Noticia();
        
        noticia.setTitulo(titulo);
        noticia.setCuerpo(cuerpo);
        noticia.setAlta(new Date());
        
        noticiaRepositorio.save(noticia);
    }
    
    public List<Noticia> listarNoticias(){
        List<Noticia> noticias = new ArrayList();
        noticias = noticiaRepositorio.todosDescendente();
        
        return  noticias;
    }
    
    public void modificarNoticia(String id, String titulo, String cuerpo) throws MiException{
        validar(titulo,cuerpo);
        validarIdNoticia(id);
        Optional <Noticia> respuesta = noticiaRepositorio.findById(id);
        
        if (respuesta.isPresent()) {
            Noticia noticia = respuesta.get();
            noticia.setTitulo(titulo);
            noticia.setCuerpo(cuerpo);
            noticia.setAlta(new Date());
            noticiaRepositorio.save(noticia);
        }
    }
    
    @Transactional
    public void eliminarNoticia(String id) throws MiException {
        validarIdNoticia(id);
    Noticia noticia = noticiaRepositorio.getById(id);
    noticiaRepositorio.delete(noticia);
    }

    
    public Noticia getOne(String id){
    
        return noticiaRepositorio.getOne(id);
    }
    
    private void validar(String titulo, String cuerpo) throws MiException{
    
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("El titulo no pueder estar vacio o ser nulo");
        }
        
        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("El cuerpo no pueder estar vacio o ser nulo");
        }   
    }
    
    public void validarTitulo(String titulo) throws MiException {
        if (titulo.isEmpty() || titulo == null) {
            throw new MiException("Titulo no puede ser vacio ni nulo");
        }
    }

    public void validarCuerpo(String cuerpo) throws MiException {
        if (cuerpo.isEmpty() || cuerpo == null) {
            throw new MiException("Cuerpo no puede ser vacio ni nulo");
        }
    }

    public void validarIdNoticia(String idNoticia) throws MiException {
        if (idNoticia.isEmpty() || idNoticia == null) {
            throw new MiException("ID Noticia no puede ser vacio ni nulo");
        }
    }

}
