package com.eduardosantos.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.eduardosantos.todosimple.models.User;

@Repository
public interface UserRepositories extends JpaRepository<User, Long> {
    /* Esse Repositories servem para buscar os dados da classe no banco de dados
     * O JpaRepository é uma interface que já tem métodos prontos para fazer operações no banco de dados
     * Um exemplo de método que já vem pronto é o "User findByUsername(String username);" Faz um SELECT no banco pelo nome dele
    */
}
