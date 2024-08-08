package shvetsov.task.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import shvetsov.task.errors.UserNotFoundException;
import shvetsov.task.jwt.JwtResponse;
import shvetsov.task.jwt.JwtService;
import shvetsov.task.models.Roles;
import shvetsov.task.models.Users;
import shvetsov.task.repository.RolesRepository;
import shvetsov.task.repository.UsersRepository;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private UsersRepository userRepository;

    public ResponseEntity<?> registrationUser(String email, String password){
        if ((email.contains("@")) && (!userRepository.findByEmail(email).isPresent())) {
            if (password.length() > 5){
                Set<Roles> rolesSet = new HashSet<>();
                Roles roles = rolesRepository.findByTitle("user");
                rolesSet.add(roles);
                String passEncode = passwordEncoder.encode(password);
                Users user = new Users(email, passEncode);
                user.setUserRoles(rolesSet);
                user.setCreated_at(ZonedDateTime.now(ZoneOffset.UTC).plusHours(3));
                userRepository.save(user);
                return new ResponseEntity<>(user, HttpStatus.CREATED);
            }
            else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<?> delUser(String email){
        Users user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found with email:"));
        userRepository.delete(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByEmail(username).orElseThrow(() -> new UserNotFoundException("User not found"));


        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getUserRoles())
                .build();

        return userDetails;
    }

    public ResponseEntity<?> authenticateUser(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()){
            if (email != null && password != null){
                Authentication authentication = authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(email, password));

                SecurityContextHolder.getContext().setAuthentication(authentication);
                String jwt = jwtService.generateToken(authentication.getName());
                return ResponseEntity.ok(new JwtResponse(jwt));
            }else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
