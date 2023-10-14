package br.com.pedroporto.todolistapi.user;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

// <> => recebe atributos dinamicos

public interface IUserRepository extends JpaRepository<UserModel, UUID> {
  UserModel findByUsername(String username);
}
