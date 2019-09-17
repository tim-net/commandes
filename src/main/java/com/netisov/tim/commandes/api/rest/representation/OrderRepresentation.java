package com.netisov.tim.commandes.api.rest.representation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Response class containing
 * an order with order lines
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ApiModel(value = "OrderRepresentation", description = "Order representation")
public class OrderRepresentation {
    @ApiModelProperty(value = "Identity", example = "1", position = 10)
    private Long id;

    @ApiModelProperty(value = "Created", example = "2018-10-01T00:00:00", position = 20)
    private LocalDateTime createdAt;

    @ApiModelProperty(value = "Client", position = 30)
    private ClientRepresentation client;

    @ApiModelProperty(value = "Shipping country", position = 40)
    private CountryRepresentation shippingCountry;

    @ApiModelProperty(value = "Order state", position = 50)
    private OrderStateRepresentation state;

    @ApiModelProperty(value = "Total price", example = "1.0", position = 60)
    private Double price;

    @ApiModelProperty(value = "Order lines", position = 70)
    private List<OrderLineRepresentation> lines;

}
