package edu.AnastasiiaTkachuk.service;

import edu.AnastasiiaTkachuk.dao.UserRepository;
import edu.AnastasiiaTkachuk.dto.UserCreateDto;
import edu.AnastasiiaTkachuk.dto.UserReadDto;
import edu.AnastasiiaTkachuk.entity.User;
import edu.AnastasiiaTkachuk.mapper.UserCreateMapper;
import edu.AnastasiiaTkachuk.mapper.UserReadMapper;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserReadMapper userReadMapper;
    private final UserCreateMapper userCreateMapper;

    public Long create(UserCreateDto userDto){
        //validation
        // map
        User user = userCreateMapper.mapFrom(userDto);
        return userRepository.save(user).getId();
    }
    public Optional<UserReadDto> findById(Long id){
        return userRepository.findById(id)
                .map(userReadMapper::mapFrom);
    }

    public boolean delete(Long id){
        Optional<User> maybeUser = userRepository.findById(id);
        maybeUser.ifPresent(user -> userRepository.delete(user.getId()));
        return maybeUser.isPresent();
    }
}
