package alex.totsky.second.service;

import alex.totsky.second.dto.order.OrderReadDto;
import alex.totsky.second.persistence.entity.Order;
import alex.totsky.second.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import alex.totsky.second.dto.order.OrderWriteDto;
import alex.totsky.second.service.converter.OrderConverter;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true)
public class OrderService {

    private OrderRepository orderRepository;
    private OrderConverter orderConverter;

    public void checkout(OrderWriteDto orderWriteDto) {
        log.info("Saving order in the order repository");
        orderRepository.save(orderConverter.dtoToEntity(orderWriteDto));
    }

    public List<OrderReadDto> getAll() {
        log.info("Retrieving all orders from the order repository");
        return orderRepository.findAll().stream()
                .map(orderConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public List<OrderReadDto> getAllByUserId(Integer userId) {
        log.info("Retrieving all orders from the order repository for the user with id '{}'", userId);
        return orderRepository.findAllByUserId(userId).stream()
                .map(orderConverter::entityToDto)
                .collect(Collectors.toList());
    }

    public Optional<Order> getByOrderId(Integer orderId) {
        return orderRepository.findById(orderId);
    }

    public Order updateOrder(Order order) {
        return orderRepository.save(order);
    }
}
