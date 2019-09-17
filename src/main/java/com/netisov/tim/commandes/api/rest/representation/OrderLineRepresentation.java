package com.netisov.tim.commandes.api.rest.representation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * REST Response class
 * for an order line entry
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ApiModel(value = "OrderLineRepresentation", description = "Order line representation")
public class OrderLineRepresentation {
    @ApiModelProperty(value = "Identity", example = "1", position = 10)
    private Long id;

    @ApiModelProperty(value = "Article", position = 20)
    private ArticleRepresentation article;

    @ApiModelProperty(value = "Price", example = "1.0", position = 30)
    private Double price;

    @ApiModelProperty(value = "Amount", example = "1", position = 40)
    private Integer amount;

}
