package com.example.demo2.services;

import com.example.demo2.auth.Roles;
import com.example.demo2.auth.RolesRepository;
import com.example.demo2.dto.UserDto;
import com.example.demo2.entitites.User;
import com.example.demo2.logic.UserLogic;
import com.example.demo2.mapper.UserMapper;
import com.example.demo2.repostories.UserRepository;
import com.example.demo2.token.ConfirmationToken;
import com.example.demo2.token.ConfirmationTokenService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {
    @PersistenceContext
    private EntityManager entityManager;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserLogic userLogic;
    @Autowired
    private ConfirmationTokenService confirmationTokenService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RolesRepository rolesRepository;

    @PreAuthorize("hasAuthority('CREATE_USER')")
    public UserDto saveUser(UserDto userDto) {
        if (userLogic.validateUser(userDto)) {
            if (!userLogic.validateUserID(userDto)) {
                throw new IllegalArgumentException("User id must be 11 characters!");
            }
            if (!userLogic.validateUserSalary(userDto)) {
                throw new IllegalArgumentException("User salary must be bigger than or equal to 0!");
            }
            User user = userMapper.toEntity(userDto);
            user = userRepository.save(user);
            return userMapper.toDTO(user);
        } else {
            throw new IllegalArgumentException("User data is not valid!");
        }
    }

    @PreAuthorize("hasAuthority('READ_USER')")
    public Optional<UserDto> getUserById(UUID id) {
        return userRepository.findById(id).map(userMapper::toDTO);
    }

    @PreAuthorize("hasAuthority('READ_USER')")
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toDTO).collect(Collectors.toList());
    }

    @PreAuthorize("hasAuthority('DELETE_USER')")
    public void deleteUser(UUID id) {
        userRepository.deleteById(id);
    }

    @PreAuthorize("hasAuthority('UPDATE_USER')")
    public UserDto updateUser(UUID id, UserDto useratt) {
        if (userLogic.validateUser(useratt)) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("User not found with id " + id));
            if (!userLogic.validateUserID(useratt)) {
                throw new IllegalArgumentException("User id must be 11 characters!");
            }
            if (!userLogic.validateUserSalary(useratt)) {
                throw new IllegalArgumentException("User salary must be bigger than or equal to 0!");
            }
            user.setName(useratt.getName());
            user.setSurname(useratt.getSurname());
            user.setIdentity_number(useratt.getIdentity_number());
            user.setBirth_date(useratt.getBirth_date());
            user.setSalary(useratt.getSalary());
            user.setRoles(user.getRoles());
            user = userRepository.save(user);
            return userMapper.toDTO(user);
        } else {
            throw new IllegalArgumentException("User data is not valid!");
        }
    }

    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findById(UUID.fromString(id));
        return optionalUser.orElseThrow(() -> new UsernameNotFoundException("User not found with id " + id));
    }

    public void signUpUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        ConfirmationToken confirmationToken = new ConfirmationToken(user);
        confirmationTokenService.saveConfirmationToken(confirmationToken);
    }

    public void saveAsUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName() + " " + userDto.getSurname());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Roles role = rolesRepository.findByRolename("ROLE_ADMIN");
        if (role == null) {
            role = checkRoleExist();
        }
        user.setRoles(Set.of(role));
        userRepository.save(user);
    }
    @PreAuthorize("hasAuthority('READ_USER')")
    public Page<User> findAllUsersWithFilters(Pageable pageable, String name, String surname, String email, String identity_number, Date birth_date, Float salary, String sortBy, String sortDirection) {
        Locale turkishLocale = new Locale("tr", "TR");

        logger.info("Filtering users with: name={}, surname={}, email={}, identity_number={}, birth_date={}, salary={}",
                name, surname, email, identity_number, birth_date, salary);
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> user = cq.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (name != null && !name.isEmpty()) {
            predicates.add(cb.like(cb.lower(user.get("name")), "%" + name.toLowerCase(turkishLocale) + "%"));
        }
        if (surname != null && !surname.isEmpty()) {
            predicates.add(cb.like(cb.lower(user.get("surname")), "%" + surname.toLowerCase(turkishLocale) + "%"));
        }
        if (email != null && !email.isEmpty()) {
            predicates.add(cb.like(cb.lower(user.get("email")), "%" + email + "%"));
        }
        if (identity_number != null && !identity_number.isEmpty()) {
            predicates.add(cb.like(cb.lower(user.get("identity_number")), "%" + identity_number + "%"));
        }
        if (birth_date != null) {
            predicates.add(cb.equal(user.get("birth_date"), birth_date));
        }
        if (salary != null) {
            predicates.add(cb.equal(user.get("salary"), salary));
        }

        cq.where(predicates.toArray(new Predicate[0]));

        if (sortDirection != null && sortBy != null) {
            if (sortDirection.equalsIgnoreCase("asc")) {
                cq.orderBy(cb.asc(cb.lower(user.get(sortBy))));
            } else if (sortDirection.equalsIgnoreCase("desc")) {
                cq.orderBy(cb.desc(cb.lower(user.get(sortBy))));
            }
        }

        TypedQuery<User> typedQuery = entityManager.createQuery(cq.select(user));
        int totalRows = typedQuery.getResultList().size();
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<User> results = typedQuery.getResultList();

        return new PageImpl<>(results, pageable, totalRows);
    }


    @PreAuthorize("hasAuthority('READ_USER')")
    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    private Roles checkRoleExist() {
        Roles role = new Roles();
        role.setRolename("ROLE_ADMIN");
        return rolesRepository.save(role);
    }
}