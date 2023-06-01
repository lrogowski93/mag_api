package mag.service;

import mag.model.OrderHeader;

import mag.model.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

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

    @Test
    void shouldAddOrderItem() {
        //given
        OrderItem orderItem = OrderItem.builder()
                .id(0)
                .orderId(69939)
                .itemId(1056533)
                .vat("23")
                .ordered(50)
                .fulfilled(0)
                .reserved(50)
                .toReserve(50)
                .netValue(BigDecimal.ZERO)
                .grossValue(BigDecimal.ZERO)
                .currencyNetVal(BigDecimal.ZERO)
                .currencyGrossVal(BigDecimal.ZERO)
                .conversion(1)
                .unit("szt.")
                .markup(BigDecimal.ZERO)
                .markupSign(0)
                .registrationMode(0)
                .deliveryId(0)
                .productVariantId(0)
                //.priceType('i')
                .crossBorderTransType("Nie dotyczy")
                .build();

        //when
        int id = orderService.addOrderItem(orderItem);
        //then
        assertThat(id).isEqualTo(0);

    }


}