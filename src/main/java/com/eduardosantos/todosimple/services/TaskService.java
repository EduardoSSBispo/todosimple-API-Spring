package com.eduardosantos.todosimple.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.eduardosantos.todosimple.models.Task;
import com.eduardosantos.todosimple.models.User;
import com.eduardosantos.todosimple.repositories.TaskRepositories;

@Service
public class TaskService {
    
    @Autowired
    private TaskRepositories taskRepository;

    @Autowired
    private UserService userService;

    public Task findTaskById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        
        return task.orElseThrow(() -> new RuntimeException(
            "Tarefa com o ID:" + id + " não encontrada! Tipo: " + Task.class.getName()
        ));
    }

    @Transactional
    public Task create(Task task) {
        User user = this.userService.findUserById(task.getUser().getId());

        task.setId(null);
        task.setUser(user);
        task = this.taskRepository.save(task);

        return task;
    }

    @Transactional
    public Task update(Task task) {
        Task newTask = findTaskById(task.getId());
        newTask.setDescription(task.getDescription());
        return this.taskRepository.save(newTask);
    }

    public void delete(Long id) {
        findTaskById(id);

        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível deletar a tarefa com o ID: " + id);
        }
    }
}
