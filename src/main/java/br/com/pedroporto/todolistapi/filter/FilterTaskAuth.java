package br.com.pedroporto.todolistapi.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.pedroporto.todolistapi.user.IUserRepository;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Component //Pro spring gerenciar 
public class FilterTaskAuth extends OncePerRequestFilter {
  
  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
    throws ServletException, IOException {
      
      var servletPath = request.getServletPath();
      
      if(servletPath.startsWith("/tasks/")){
        var authorization = request.getHeader("Authorization");  
      
        var authEnconded = authorization.substring("Basic".length()).trim();
        byte[] authDecoded = Base64.getDecoder().decode(authEnconded);
        var authString = new String(authDecoded);
          
          
        String[] credentials = authString.split(":");
        String username = credentials[0];
        String password = credentials[1];
        
        var user = this.userRepository.findByUsername(username);
        
        if(user == null){
          response.sendError(401);
        }
        
        var passwordVerification = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());

        if(passwordVerification.verified){
          request.setAttribute("idUser", user.getId());
          filterChain.doFilter(request, response); // request é o que está vindo na requisição, response é o que vamos enviar pro usuário
        } else {
          response.sendError(401);
        }
      } else {
        filterChain.doFilter(request, response);
      }
    
        
  }
  
}
