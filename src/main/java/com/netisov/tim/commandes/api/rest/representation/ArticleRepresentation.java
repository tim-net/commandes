package com.netisov.tim.commandes.api.rest.representation;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * REST Response class
 * for an article entry
 */
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@ApiModel(value = "ArticleRepresentation", description = "Article representation")
public class ArticleRepresentation {
    @ApiModelProperty(value = "Code", position = 10)
    private String code;

    @ApiModelProperty(value = "Label", position = 20)
    private String label;

    @ApiModelProperty(value = "Family", position = 30)
    private ArticleFamilyRepresentation family;

    @ApiModelProperty(value = "Price", example = "1.0", position = 40)
    private Double price;
}
