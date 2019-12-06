package com.codeoftheweb.salvo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.GlobalAuthenticationConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

    @Configuration
    class WebSecurityConfiguration extends GlobalAuthenticationConfigurerAdapter {

        @Autowired
        PlayerRepository playerRepository;

        @Autowired
        PasswordEncoder passwordEncoder;

        @Override
        public void init(AuthenticationManagerBuilder auth) throws Exception {
            auth.userDetailsService(inputName-> {
                Player player = playerRepository.findByUserName(inputName);
                if (player != null) {
                    if (player.isAdmin()){
                        return new User(player.getUserName(),player.getPassword(),
                        AuthorityUtils.createAuthorityList("ADMIN"));
                    } else {
                        return new User(player.getUserName(), player.getPassword(),
                        AuthorityUtils.createAuthorityList("USER"));

                    }
                }else {
                    throw new UsernameNotFoundException("Unknown user: " + inputName);
                }
            }).passwordEncoder(passwordEncoder);
        }
    }

