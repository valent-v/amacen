package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Set;
@Entity
public class ShipLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    @OneToMany(mappedBy="shipLocation", fetch= FetchType.EAGER)
    Set<Ship> ship;

    public ShipLocation(){}
}