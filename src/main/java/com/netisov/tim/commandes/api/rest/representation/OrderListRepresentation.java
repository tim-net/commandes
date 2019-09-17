package com.netisov.tim.commandes.api.rest.representation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST Response class containing
 * total number of items and list of orders
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ApiModel(value = "OrderListRepresentation", description = "Orders list")
public class OrderListRepresentation {

    @ApiModelProperty(value = "Total number of orders", example = "10", position = 10)
    private Long count;


    @ApiModelProperty(value = "Orders list", position = 20)
    private List<OrderRepresentation> orders;

    @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    @ApiModel(value = "OrderRepresentation", description = "OrderRepresentation in the list")
    public static class OrderRepresentation {
        @ApiModelProperty(value = "Identity", example = "1", position = 10)
        private Long id;

        @ApiModelProperty(value = "Creation date and time", example = "2018-10-01T00:00:00", position = 30)
        private LocalDateTime createdAt;

        @ApiModelProperty(value = "Name of a client", position = 20)
        private String client;

        @ApiModelProperty(value = "State", position = 30)
        private String state;

        @ApiModelProperty(value = "Shipping country name", position = 40)
        private String shippingCountry;
    }
}
