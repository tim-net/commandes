package com.netisov.tim.commandes.api.rest.representation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * REST Response class
 * for a country entry
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ApiModel(value = "CountryRepresentation", description = "Country representation")

public class CountryRepresentation {
    @ApiModelProperty(value = "Code", position = 10)
    private String code;

    @ApiModelProperty(value = "Label", position = 20)
    private String label;
}
