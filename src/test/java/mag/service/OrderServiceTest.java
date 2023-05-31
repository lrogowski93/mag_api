package mag.service;

import mag.model.OrderHeader;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class OrderServiceTest {
    @Autowired
    private OrderService orderService;



    @Test
    void shouldAddOrderHeader() {
        //given
        OrderHeader orderHeader = OrderHeader.builder()
                .companyId(1)
                .customerId(7909)
                .warehouseId(1)
                .type(1)
                .date(81237)
                .userId(3000001)
                .registrationMode(0)
                .valueType("Netto")
                .build();

        //when
        long id = orderService.addOrderHeader(orderHeader);
        System.out.println("ID:"+id);
        //then
        assertThat(id).isGreaterThan(0L);

    }


}