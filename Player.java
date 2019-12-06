package com.codeoftheweb.salvo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;
    private String userName;
    private String email;
    private String password;
    private boolean admin;
    @OneToMany(mappedBy="player", fetch=FetchType.EAGER)
    Set<GamePlayer> gamePlayers;
    @OneToMany(mappedBy="player", fetch = FetchType.EAGER)
    Set<Score> scores;

    public Player(){};

    public Player(String userName, String email, String password, boolean isAdmin){
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.admin=admin;
    }

    public Player (String userName, String email, String password){
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.admin=false;
    }

    public Player (String userName, String password){
        this.userName=userName;
        this.password=password;
    }

        public String getUserName() {
        return userName;
    }
        public void setUserName(String userName){
        this.userName= userName;
    }

        public String getEmail(){return email; }
        public void setEmail (String email) {this.email=email; }

        public Long getId(){return id;}
        public void setId(Long id) {this.id=id;}

        public String getPassword(){return password; }
        public void setPassword (String password) {this.password=password; }

        public void setAdmin(boolean admin) {
        this.admin = admin;
        }

        public boolean isAdmin() {
        return this.admin;
        }

        public void addGamePlayer (GamePlayer gamePlayer){
            gamePlayer.setPlayer(this);
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
        public List<Game> getGame(){
        return gamePlayers.stream().map(sub -> sub.getGame()).collect(toList());
        }


        public Score getScoreByGame(Game game){
            return this.scores.stream()
             .filter(score -> score.getGame().getId() == game.getId())
             .findFirst()
             .orElse(null);
        }
        @JsonIgnore
        public Map<String, Object> makePlayerDTO() {
          Map<String, Object> dto = new LinkedHashMap<String, Object>();
          dto.put("id", getId());
          dto.put("name", getUserName());



        return dto;
    }

}
