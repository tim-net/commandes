package com.netisov.tim.commandes.api.rest.representation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * REST Response class
 * for a order state entry
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ApiModel(value = "OrderStateRepresentation", description = "Order state representation")

public class OrderStateRepresentation {
    @ApiModelProperty(value = "Code", position = 10)
    private String code;

    @ApiModelProperty(value = "Label", position = 20)
    private String label;

    @ApiModelProperty(value = "Outcome", position = 30)
    private String outcome;

}
