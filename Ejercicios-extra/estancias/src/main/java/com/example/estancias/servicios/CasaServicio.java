/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.estancias.servicios;

import com.example.estancias.repositorios.CasaRepositorio;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CasaServicio {
    
    @Autowired
    CasaRepositorio casaRepositorio;
    
    @Transactional
    private void crearCasa(String nombre ){
        
    }
}
