package com.netisov.tim.commandes.api.rest.representation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * REST Response class
 * for an article family entry
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@ApiModel(value = "ArticleFamilyRepresentation", description = "Article family representation")
public class ArticleFamilyRepresentation {
    @ApiModelProperty(value = "Code", position = 10)
    private String code;

    @ApiModelProperty(value = "Label", position = 20)
    private String label;
}
