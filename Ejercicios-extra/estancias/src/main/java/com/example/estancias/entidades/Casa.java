/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.estancias.entidades;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Data
@NoArgsConstructor
@Entity
public class Casa {
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String calle;
    private Integer numero;
    private String codPostal;
    private String ciudad;
    private String pais;
    
    @Temporal(TemporalType.DATE)
    private Date fechaDesde;
    @Temporal(TemporalType.DATE)
    private Date fechaHasta;
    
    private Integer minDias;
    private Integer maxDias;
    private Double precio;
    private String tipoVivienda;
    
    
    
}
