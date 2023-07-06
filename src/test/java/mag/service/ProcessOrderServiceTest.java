package mag.service;

import mag.model.OrderItem;
import mag.model.procedure.AddOrderItemProcedure;
import mag.model.procedure.SumUpOrderProcedure;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProcessOrderServiceTest {

    @Mock
    private NamedParameterJdbcTemplate magJdbcTemplateNamedParameter;
    @Mock
    private HelperMethodsService helperMethodsService;
    @Mock
    private AddOrderService addOrderService;
    @InjectMocks
    private ProcessOrderService processOrderService;

    @Test
    void extractIndexes() {
        //given
        List<OrderItem> orderItemList = List.of(
                OrderItem.builder().index("A1").quantity(123).netPrice(new BigDecimal(34.5)).vat("23").build(),
                OrderItem.builder().index("B4").quantity(300).netPrice(new BigDecimal(12.3)).vat("23").build()
        );
        //when
        List<String> indexes = processOrderService.extractIndexes(orderItemList);
        //then
        assertEquals("A1", indexes.get(0));
        assertEquals("B4", indexes.get(1));
    }

    @Test
    void setItemDetails() throws Exception {
        //given
        int returnValue;
        BigDecimal expectedGrossPrice = new BigDecimal(12.3);
        List<OrderItem> orderItemList = List.of(
                OrderItem.builder().index("C1").quantity(123).netPrice(new BigDecimal(34.5)).vat("23").build(),
                OrderItem.builder().index("Z62").quantity(321).netPrice(new BigDecimal(10.2)).vat("23").build(),
                OrderItem.builder().index("A15").quantity(200).netPrice(new BigDecimal(11.33)).vat("23").build(),
                OrderItem.builder().index("F155").quantity(500).netPrice(new BigDecimal(12.81)).vat("23").build()
        );
        List<OrderItem> foundOrderItemList = List.of(
                OrderItem.builder().index("A15").build(),
                OrderItem.builder().index("Z62").build(),
                OrderItem.builder().index("F155").build()
        );
        when(helperMethodsService.calcGrossPrice(any(BigDecimal.class), anyString())).thenReturn(expectedGrossPrice);
        when(addOrderService.sumUpOrder(any(SumUpOrderProcedure.class))).thenReturn(0);
        when(addOrderService.addOrderItem(any(AddOrderItemProcedure.class))).thenReturn(0);
        //when
        returnValue = processOrderService.processOrderItems(123L, foundOrderItemList, orderItemList);
        //then
        assertEquals(expectedGrossPrice, foundOrderItemList.get(0).getGrossPrice());
        assertEquals(getItemWithIndex("A15", orderItemList).getNetPrice(), getItemWithIndex("A15", foundOrderItemList).getNetPrice());
        assertEquals(getItemWithIndex("F155", orderItemList).getQuantity(), getItemWithIndex("F155", foundOrderItemList).getQuantity());
        assertEquals(getItemWithIndex("Z62", orderItemList).getVat(), getItemWithIndex("Z62", foundOrderItemList).getVat());
        assertEquals(0, returnValue);
    }


    @Test
    void getOrderItemIds() {
        //given
        List<OrderItem> orderItemList = List.of(
                OrderItem.builder().id(111L).index("A1").build(),
                OrderItem.builder().id(222L).index("B4").build(),
                OrderItem.builder().id(333L).index("C54").build()
        );
        //when
        when(magJdbcTemplateNamedParameter.query(any(String.class), anyMap(), any(RowMapper.class))).thenReturn(orderItemList);
        List<OrderItem> result = processOrderService.getOrderItemIds(List.of("A1", "B4", "C54"));
        //then
        assertEquals(111L, result.get(0).getId());
        assertEquals("A1", result.get(0).getIndex());
        assertEquals(333L, result.get(2).getId());
        assertEquals("C54", result.get(2).getIndex());
    }

    private OrderItem getItemWithIndex(String index, List<OrderItem> orderItemList) throws Exception {
        return orderItemList.stream()
                .filter(item -> item.getIndex().equals(index))
                .findFirst()
                .orElseThrow(() -> new Exception("Item not found - " + index));
    }

}