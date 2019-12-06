package com.codeoftheweb.salvo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class SalvoController  {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    GameRepository gameRepository;
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    GamePlayerRepository gamePlayerRepository;

    @RequestMapping("/games")
    public Map<String, Object> getAllGames(Authentication authentication) {
        Map<String, Object> dto = new LinkedHashMap<>();
        if(isGuest(authentication)){
            dto.put("player", "guest");
        } else{
            dto.put("player", playerRepository.findByUserName(authentication.getName()).makePlayerDTO());
        }
        dto.put("games",  gameRepository.findAll().stream().map(Game::makeGameDTO).collect(Collectors.toList()));
        return dto;
    }

    @RequestMapping ("/players")
    public Map<String,Object> getAllPlayers (Authentication authentication){
        Map <String, Object> dto= new LinkedHashMap<>();
        dto.put("players", playerRepository.findAll().stream().map(Player::makePlayerDTO).collect(Collectors.toList()));
        return  dto;
    }

    private boolean isGuest(Authentication authentication) {
        return authentication == null || authentication instanceof AnonymousAuthenticationToken;
    }
    @RequestMapping(path = "/players", method = RequestMethod.POST)
    public ResponseEntity<String> register(@RequestParam String userName,@RequestParam String email,@RequestParam String password) {
        if (userName.isEmpty()||password.isEmpty()) {
            return new ResponseEntity<>("No name given", HttpStatus.FORBIDDEN);
        }

        Player player = playerRepository.findByUserName(userName);
        if (player != null) {
            return new ResponseEntity<>("Name already used", HttpStatus.CONFLICT);
        }

        if (!email.isEmpty()){
            playerRepository.save(new Player(userName, email, passwordEncoder.encode(password)));
            return new ResponseEntity<>("name with email added", HttpStatus.CREATED);
        }
        else {
            playerRepository.save(new Player(userName,  passwordEncoder.encode(password)));
            return new ResponseEntity<>("Named added", HttpStatus.CREATED);
        }
    }

    @RequestMapping ("/gamePlayers")
    public List<Map<String, Object>> getAllGamePlayers(){
        return gamePlayerRepository.findAll().stream().map(GamePlayer::makeGamePlayerDTO).collect(Collectors.toList());
    }

    @RequestMapping("/game_view/{gamePlayerId}")
    public Map<String,Object> getGamePlayer(@PathVariable Long gamePlayerId) {
        GamePlayer gamePlayer = gamePlayerRepository.findById(gamePlayerId).orElse(null);
        Map<String,Object> dto = new LinkedHashMap<>();

        if(gamePlayer != null){
            dto.put("id", gamePlayer.getGame().getId());
            dto.put("date",gamePlayer.getGame().getDate());
            dto.put("gamePlayers", gamePlayer.getGame().getGamePlayers().stream().map(GamePlayer::makeGamePlayerDTO));
            dto.put("ships", gamePlayer.getShips().stream().map(Ship::makeShipDTO));
            dto.put("salvoes", gamePlayer.getGame().getGamePlayers().stream()
                    .flatMap(gp -> gp.getSalvoes().stream().map(Salvo::makeSalvoesDTO)));

        } else{
            dto.put("error", "no game");
        }
        return dto;

    }


}


