package com.eduardosantos.todosimple.services;

import java.util.List;
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

    public Task findById(Long id) {
        Optional<Task> task = this.taskRepository.findById(id);
        
        return task.orElseThrow(() -> new RuntimeException(
            "Tarefa com o ID:" + id + " não encontrada! Tipo: " + Task.class.getName()
        ));
    }

    public List<Task> findAllByUserId(Long userId) {
        List<Task> tasks = this.taskRepository.findByUser_Id(userId);
        return tasks;
    }

    @Transactional
    public Task create(Task task) {
        User user = this.userService.findById(task.getUser().getId());

        task.setId(null);
        task.setUser(user);
        task = this.taskRepository.save(task);

        return task;
    }

    @Transactional
    public Task update(Task task) {
        Task newTask = findById(task.getId());
        newTask.setDescription(task.getDescription());
        return this.taskRepository.save(newTask);
    }

    public void delete(Long id) {
        findById(id);

        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Não foi possível deletar a tarefa com o ID: " + id);
        }
    }
}
