package alex.totsky.second.service.mapper;

import alex.totsky.second.dto.order.OrderReadDto;
import alex.totsky.second.persistence.entity.Order;
import org.mapstruct.Mapper;
import alex.totsky.second.dto.order.OrderWriteDto;

@Mapper(componentModel = "spring", config = CommonMapper.class)
public interface OrderMapper extends CommonMapper<OrderReadDto, OrderWriteDto, Order> {
}
