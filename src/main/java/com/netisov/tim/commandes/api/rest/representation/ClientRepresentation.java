package com.netisov.tim.commandes.api.rest.representation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * REST Response class
 * for a client entry
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ApiModel(value = "ClientRepresentation", description = "Client representation")
public class ClientRepresentation {
    @ApiModelProperty(value = "Identity", example = "1", position = 10)
    private Long id;

    @ApiModelProperty(value = "Code", position = 20)
    private String code;

    @ApiModelProperty(value = "Name", position = 30)
    private String name;
}
