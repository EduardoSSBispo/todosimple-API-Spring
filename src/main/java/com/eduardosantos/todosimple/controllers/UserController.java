package com.eduardosantos.todosimple.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.eduardosantos.todosimple.models.User;
import com.eduardosantos.todosimple.models.User.CreateUser;
import com.eduardosantos.todosimple.models.User.UpdateUser;
import com.eduardosantos.todosimple.services.UserService;

@RestController
@RequestMapping("/user") // Delimita o caminho para acessar os métodos
@Validated // Valida os métodos com base nas anotações que foram feitas nos models
           // (@NotNull, @Size, etc)
public class UserController {

    @Autowired
    private UserService userService;

    // Métodos para manipulação de usuários
    // Cada método é responsável por uma ação específica

    // ResponseEntity é um objeto que encapsula a resposta HTTP, trata o status
    // code, o corpo da resposta e os headers
    @GetMapping("/{id}") // Mapeia o método para acessar o ID do usuário
    public ResponseEntity<User> findById(@PathVariable Long id) {
        User user = this.userService.findById(id);
        return ResponseEntity.ok().body(user); // Retorna o usuário encontrado
    }

    @PostMapping // Mapeia o método para criar um usuário
    @Validated(CreateUser.class) // Valida o método com base na interface CreateUser
    public ResponseEntity<Void> create(@Valid @RequestBody User user) {
        this.userService.create(user);
        // Cria a URI para o novo usuário
        // Uri é uma classe que representa um identificador de recurso
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).build(); // Retorna o status code 200
    }

    @PutMapping("/{id}") // Mapeia o método para atualizar um usuário
    @Validated(UpdateUser.class) // Valida o método com base na interface CreateUser
    public ResponseEntity<Void> update(@Valid @RequestBody User user, @PathVariable Long id) {
        user.setId(id);
        this.userService.update(user);
        return ResponseEntity.noContent().build(); // Retorna o status code 204
    }

    @DeleteMapping("/{id}") // Mapeia o método para deletar um usuário
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.userService.delete(id);
        return ResponseEntity.noContent().build(); // Retorna o status code 204
    }

}
