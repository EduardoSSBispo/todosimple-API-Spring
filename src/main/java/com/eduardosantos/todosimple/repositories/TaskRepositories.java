package com.eduardosantos.todosimple.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import com.eduardosantos.todosimple.models.Task;

@Repository
public interface TaskRepositories extends JpaRepository<Task, Long> {
    /* Esse Repositories servem para buscar os dados da classe no banco de dados
     * O JpaRepository é uma interface que já tem métodos prontos para fazer operações no banco de dados
     * Um exemplo de método que já vem pronto é o "Task findByDescription(String description);" Faz um SELECT no banco pelo nome dele
    */
    
    /*Três formas diferentes de fazer a mesma query */

    List<Task> findByUser_Id(Long id);

    //@Query(value= "SELECT t FROM Task t WHERE t.user.id = :id")
    //List<Task> findByUserId(@Param("id") Long id);

    //@Query(value= "SELECT * FROM task WHERE user_id = :id", nativeQuery = true)
    //List<Task> findByUserId(@Param("id") Long id);
}
