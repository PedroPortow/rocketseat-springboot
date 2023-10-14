package br.com.pedroporto.todolistapi.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data; // isso já define por baixo dos panos os getters e setter da aplicação
// tme opç]ão de ser só setter ou só getter tbm

@Data
@Entity(name = "tb_users")
public class UserModel {
  
  @Id
  @GeneratedValue(generator = "UUID") // jakarta vai tomar conta da gerração de ids
  private UUID id;
  
  @Column(unique = true) // constraint pro username ser unico VALIDAÇÃO
  private String username;
  private String name;
  private String password;
  
  @CreationTimestamp
  private LocalDateTime createdAt;
  
  
}
