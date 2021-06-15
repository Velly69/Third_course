package service;

import alex.totsky.second.persistence.entity.Dish;
import alex.totsky.second.persistence.repository.DishRepository;
import alex.totsky.second.service.DishService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DishServiceTest {
    @InjectMocks
    private DishService service;

    @Mock
    private DishRepository dishRepository;

    @Mock
    private List<Dish> list;

    @Test
    public void testFindAll() {
        when(dishRepository.findAll()).thenReturn(list);
        assertEquals(service.findAll(), list);
    }
}

