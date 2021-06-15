package controller;


import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import alex.totsky.second.dto.user.UserReadDto;
import alex.totsky.second.persistence.entity.User;
import alex.totsky.second.rest.controller.UserController;
import alex.totsky.second.service.UserService;
import alex.totsky.second.service.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {
    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @Mock
    private UserMapper userMapper;

    @Mock
    private List<UserReadDto> listDTO;

    @Mock
    private List<User> listE;

    @Mock
    private UserReadDto userDto;

    @Mock
    private User user;

    @Test
    public void testGetAllUsers() {
        when(userService.findAll()).thenReturn(listE);
        listDTO = listE.stream().
                map(userMapper::entityToDto).
                collect(Collectors.toList());
        assertEquals(controller.findAll().getBody(), listDTO);
    }

    @Test
    public void testFindByUserName() {
        when(userService.findByUsername("user")).thenReturn(Optional.of(user));
        userDto = userMapper.entityToDto(user);
        assertEquals(controller.findByUsername("user").getBody(), userDto);
    }
}