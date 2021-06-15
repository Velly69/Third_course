package alex.totsky.second.dto.user;

import lombok.Data;
import lombok.experimental.Accessors;
import alex.totsky.second.dto.order.OrderReadDto;

import java.util.List;

@Data
@Accessors(chain = true)
public class UserReadDto {
    Integer id;
    String username;
    Integer balance;
    List<OrderReadDto> orders;
}
