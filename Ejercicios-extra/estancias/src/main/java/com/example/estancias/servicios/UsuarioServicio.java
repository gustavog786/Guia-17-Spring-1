
package com.example.estancias.servicios;

import com.example.estancias.entidades.Familia;
import com.example.estancias.entidades.Usuario;
import com.example.estancias.enumeraciones.Rol;
import com.example.estancias.excepciones.MiException;
import com.example.estancias.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Service
public class UsuarioServicio implements UserDetailsService {
    
    @Autowired(required = false)
    private UsuarioRepositorio usuarioRepositorio;
    
    @Autowired
    ValidacionServicio validacionServicio;
    
    public void crearUsuario(String alias, String email, String clave1, String clave2) throws MiException{
        
        validacionServicio.validarAlias(alias);
        validacionServicio.validarEmail(email);
        validacionServicio.validarPassword(clave1);
        validacionServicio.validarPassword2(clave1,clave2);
        
        Usuario usuario = new Usuario();
        
        usuario.setAlias(alias);
        usuario.setEmail(email);
        usuario.setClave(new BCryptPasswordEncoder().encode(clave1));
        usuario.setRol(Rol.USER);
        
        //persisto - guardo en BD
        usuarioRepositorio.save(usuario);
    }
    
     //Metodo listar familias
    public List<Usuario> listarUsuarios() {

        List<Usuario> listaUsuarios = new ArrayList<>();

        // Encuentra reservas dentro de repositorio, los mete en arraylist listaReservas
        listaUsuarios = usuarioRepositorio.findAll();

        return listaUsuarios;
    }
    
    
    
    
    
    
    
    
    
    

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        
        Usuario usuario = usuarioRepositorio.buscarPorEmail(email);

        if (usuario != null) {

            List<GrantedAuthority> permisos = new ArrayList();

            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" +
                    usuario.getRol().toString());

            permisos.add(p);

            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("usuariosession", usuario);

            return new User(usuario.getEmail(), usuario.getClave(), permisos);
        } else {
            return null;
        }

    }
}
