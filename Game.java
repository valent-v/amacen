package com.codeoftheweb.salvo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;


@Entity
//clase
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private LocalDateTime creationDate;

    @OneToMany(mappedBy="game", fetch=FetchType.EAGER)
    private Set<GamePlayer> gamePlayers;
    @OneToMany(mappedBy="game", fetch = FetchType.EAGER)
    Set<Score> scores;


    //private ;


    //constructor
    public Game () {};

    //objeto de la clase
    public Game (LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDate() {return creationDate;}
    public void setDate(LocalDateTime creationDate){this.creationDate=creationDate;}

    public Long getId(){return id;}
    public void setId(Long id){this.id=id;}

    public void addGamePlayer(GamePlayer gamePlayer){
        gamePlayer.setGame(this);
        gamePlayers.add(gamePlayer);
    }

    public Set<Score> getScores() {
        return scores;
    }

    public void setScores(Set<Score> scores) {
        this.scores = scores;
    }

    public Set<GamePlayer> getGamePlayers() {
        return gamePlayers;
    }

    @JsonIgnore
    public List<Player> getPlayers(){
        return gamePlayers.stream().map(sub->sub.getPlayer()).collect(toList());
    }

    @JsonIgnore
    public Map<String, Object> makeGameDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", id);
        dto.put("date", creationDate);
        dto.put("gamePlayers", gamePlayers.stream().map(GamePlayer::makeGamePlayerDTO));
        return dto;
    }
}