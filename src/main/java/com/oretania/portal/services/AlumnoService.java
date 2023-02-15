package com.oretania.portal.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.oretania.portal.models.Alumno;
import com.oretania.portal.models.Asignatura;
import com.oretania.portal.repositories.AlumnoRepository;

@Service
public class AlumnoService implements UserDetailsService{
    
    @Autowired
    AlumnoRepository repository;

    public List<Alumno> findAll(){
        return repository.findAll();
    }

    public Optional<Alumno> findByCodigo(int codigo){
        return repository.findById(codigo);
    }

    public Alumno save(Alumno alumno){
        return repository.save(alumno);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        Alumno alumno = repository.findByUserName(username);
        List<Asignatura> asignaturas = new ArrayList<Asignatura>();

        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for (Asignatura asignatura : asignaturas){
            authorities.add(new SimpleGrantedAuthority(asignatura.getCodigo()));
        }

        UserDetails user = org.springframework.security.core.userdetails.User.builder()
            .username(alumno.getUserName())
            .password(alumno.getPassword())
            .authorities(authorities)
            .build();

        return user;
    }


}
