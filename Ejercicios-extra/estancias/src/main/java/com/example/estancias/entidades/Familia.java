
package com.example.estancias.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


@Data
@NoArgsConstructor
@Entity
public class Familia extends Usuario{
    
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private int edadMin;
    private int edadMax;
    private int numHijos;
    
    
    
}
