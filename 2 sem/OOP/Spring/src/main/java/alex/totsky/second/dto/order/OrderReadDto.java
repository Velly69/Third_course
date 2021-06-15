package alex.totsky.second.dto.order;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class OrderReadDto {
    Integer id;
    String status;
    Double totalSum;
    List<OrderDishDto> dishes;
}
