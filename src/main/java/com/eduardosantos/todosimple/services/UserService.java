package com.eduardosantos.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardosantos.todosimple.models.User;
import com.eduardosantos.todosimple.repositories.UserRepositories;

@Service
public class UserService {
    // Essa classe se comunica com os Repositories

    // AutoWired serve para injetar a dependência da classe
    @Autowired
    private UserRepositories userRepository;


    public User findUserById(Long id) {
        //Optional serve para evitar nullpointerexception
        Optional<User> user = this.userRepository.findById(id);

        //Se o user estiver nulo, lança uma exceção
        return user.orElseThrow(() -> new RuntimeException(
            "Usuário com o ID:" + id + " não encontrado! Tipo: " + User.class.getName()
        ));
    }

    //O Transactional serve para garantir que a operação seja executada com sucesso
    //É bom utilizar em Create e Update, para garantir que vai ser salvo no banco
    @Transactional
    public User createUser(User user) {
        user.setId(null); //Garante que o usuário não tenha um ID
        user = this.userRepository.save(user);
        
        return user;
    }

    @Transactional
    public User updateUser(User user) {
        //Atualizar apenas a senha, pois o username e o ID são identificadores únicos
        User newUser = findUserById(user.getId());
        newUser.setPassword(user.getPassword());

        return this.userRepository.save(newUser);
    }

    public void delete(Long id) {
        //Verifica se o usuário existe
        findUserById(id);

        //Try catch, pois pode ocorrer um erro ao deletar o usuário por conta de relacionamentos
        try {
            this.userRepository.deleteById(id);   
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível deletar o usuário com ID: " + id);
        }
    }
}
