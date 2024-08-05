package com.eduardosantos.todosimple.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = User.TABLE_NAME)
public class User {
    public interface CreateUser {} //Interface para validação de criação de usuário
    public interface UpdateUser {} //Interface para validação de atualização de usuário

    public static final String TABLE_NAME = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Gera valores sequenciais no banco (identity do postgres)
    @Column(name = "id", unique = true, nullable = false) //Verificação de coluna no banco, para criar a tabela
    private Long id;

    //(groups = CreateUser.class) é uma forma de dizer que a validação só deve ser feita quando o objeto for criado
    //{CreateUser.class, UpdateUser.class} é uma forma de dizer que a validação deve ser feita tanto na criação quanto na atualização

    @Column(name = "username", unique = true, nullable = false, length = 100)
    @NotNull (groups = CreateUser.class) //Verifica se o valor do campo não é null, antes de mandar pro banco
    @NotEmpty (groups = CreateUser.class)
    @Size(min = 3, max = 50) //Verifica se o tamanho da string está entre 3 e 50
    private String username;

    @JsonProperty(access = Access.WRITE_ONLY) //Não permite que o campo seja lido, apenas escrito
    @Column(name = "password", nullable = false, length = 20)
    @NotBlank (groups = {CreateUser.class, UpdateUser.class}) //O equivalente ao @NotNull e @NotEmpty ao mesmo tempo
    @Size(min = 6, max = 20) //Verifica se o tamanho da string está entre 6 e 20
    private String password;

    //mappedBy serve para dizer que o mapeamento foi feito na outra classe, uso o nome da variável que está na outra classe
    @OneToMany(mappedBy = "user") //Fazendo o contrário do que foi feito no Task
    @JsonIgnore //Não permite que o campo seja lido
    private List<Task> tasks = new ArrayList<Task>();


    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /*Verifica se o objeto é nulo, se não é uma instância de User, 
    se os ids são nulos, se os ids são iguais, se os usernames são 
    iguais e se as senhas são iguais */
    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }

        if (obj == null){
            return true;
        }

        if(!(obj instanceof User)){
            return false;
        }

        User other = (User) obj;
        if(this.id ==null){
            if(other.id != null){
                return false;
            }
        } else if (!this.id.equals(other.id)){
            return false;
        }

        return Objects.equals(this.id, other.id) && 
               Objects.equals(this.username, other.username) && 
               Objects.equals(this.password, other.password);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.id == null) ? 0 : id.hashCode());
        return result;
    }
}
