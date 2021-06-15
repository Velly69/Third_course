package alex.totsky.second.service.mapper;

import alex.totsky.second.dto.dish.DishReadDto;
import alex.totsky.second.dto.dish.DishWriteDto;
import alex.totsky.second.persistence.entity.Dish;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", config = CommonMapper.class)
public interface DishMapper extends CommonMapper<DishReadDto, DishWriteDto, Dish> {
}
