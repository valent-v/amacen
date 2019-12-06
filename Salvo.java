package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Salvo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String turn;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="gamePlayer_id")
    private GamePlayer gamePlayer;
    @ElementCollection
    private List<String> salvoLocation;

    public Salvo (){};

    public Salvo(String turn, List<String> salvoLocation) {
        this.turn=turn;
        this.salvoLocation = salvoLocation;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public String getTurn() {
        return turn;
    }

    public void setTurn(String turn) {
        this.turn = turn;
    }

    public GamePlayer getGamePlayer() {
        return gamePlayer;
    }
    public void setGamePlayer(GamePlayer gamePlayer) {
        this.gamePlayer = gamePlayer;
    }

    public List<String> getSalvoLocation() {
        return salvoLocation;
    }

    public void setSalvoLocation(List<String> salvoLocation) {
        this.salvoLocation = salvoLocation;
    }


    public Map<String,Object>makeSalvoesDTO (){
        Map <String, Object> dto=new LinkedHashMap<>();
        dto.put("turn", turn);
        dto.put("coordinates", salvoLocation);
        dto.put("gpId", getGamePlayer().getId());
        return dto;
    }
}