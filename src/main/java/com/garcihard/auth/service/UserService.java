package com.garcihard.auth.service;

import com.garcihard.auth.exception.custom.UsernameAlreadyExistException;
import com.garcihard.auth.mapper.UserMapper;
import com.garcihard.auth.model.dto.NewUserDTO;
import com.garcihard.auth.model.entity.User;
import com.garcihard.auth.repository.UserRepository;
import com.garcihard.auth.utils.ApiConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /*
     * Creates a new user.
     *
     * @param newUserDTO The request containing username and password.
     * @throws UsernameAlreadyExistException If the username already exist.
     * */
    @Transactional
    public void createUser(NewUserDTO newUserDTO) {
        User entity = userMapper.toEntity(newUserDTO);
        entity.setPassword(passwordEncoder.encode(newUserDTO.password()));

        try { // If 2+ users tried to create the same username at the same time.
            userRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw buildUsernameExistException(newUserDTO.username());
        }
    }

    private UsernameAlreadyExistException buildUsernameExistException(String username) {
        return new UsernameAlreadyExistException(ApiConstants.USER_CONFLICT_CODE,
                ApiConstants.USER_ALREADY_TAKEN.formatted(username));
    }
}