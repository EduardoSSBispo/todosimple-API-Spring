package com.eduardosantos.todosimple.models;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table (name = Task.TABLE_NAME)
public class Task {
        private static final String TABLE_NAME = "tasks";

        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE)
        @Column(name = "id", unique = true, nullable = false)
        private Long id;

        @ManyToOne //Muitas tarefas para um usuário, para o banco
        @JoinColumn(name = "user_id", nullable = false, updatable = false) //Realizar join entre usuário e tarefa
        private User user;

        @Column(name = "description", nullable = false, length = 255)
        @NotNull
        @NotEmpty
        @Size(min = 1, max = 255)
        private String description;

        
        public Task(Long id, User user, String description) {
            this.id = id;
            this.user = user;
            this.description = description;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj == null){
            return true;
        }

        if(!(obj instanceof Task)){
            return false;
        }

        Task other = (Task) obj;
        if(this.id ==null){
            if(other.id != null){
                return false;
            }
        } else if (!this.id.equals(other.id)){
            return false;
        }

        return Objects.equals(this.id, other.id) && 
               Objects.equals(this.user, other.user) && 
               Objects.equals(this.description, other.description);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : id.hashCode());
        return result;
    }
}
