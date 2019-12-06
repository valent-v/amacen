package com.codeoftheweb.salvo;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Entity
public class GamePlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="player_id")
    private Player player;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="game_id")
    private Game game;

    @OneToMany(mappedBy="gamePlayer", fetch= FetchType.EAGER,cascade = CascadeType.ALL)
    private Set<Ship> ships = new HashSet<>();

    @OneToMany(mappedBy="gamePlayer", fetch=FetchType.EAGER,cascade=CascadeType.ALL)
    private Set<Salvo> salvoes= new HashSet<>();

    public GamePlayer (){}

    public GamePlayer (Game game, Player player){
        this.game=game;
        this.player=player;
    }
    public long getId(){
           return id;}
    public void setId(Long id){
        this.id=id;}

     public Set<Ship> getShips() {
        return ships;
    }

    public Set<Salvo> getSalvoes() {return salvoes;}

     public Game getGame(){
        return game;}
     public void setGame(Game game){
      this.game=game;}

     public Player getPlayer(){
       return player;}

     public void addShip(Ship ship){
           ships.add(ship);
           ship.setGamePlayer(this);
     }
     public void addSalvo(Salvo salvo){
        salvoes.add(salvo);
        salvo.setGamePlayer(this);

     }


   public void setPlayer(Player player){
      this.player=player;
   }

    public Map <String, Object> makeGamePlayerDTO() {
        Map<String, Object> dto = new LinkedHashMap<String, Object>();
        dto.put("id", id);
        dto.put("player", player.makePlayerDTO());
        Score score = player.getScoreByGame(game);
        if(score != null){
            dto.put("score", score.getScore());
        } else{
            dto.put("score", null);
        }

        return dto;
}
}


