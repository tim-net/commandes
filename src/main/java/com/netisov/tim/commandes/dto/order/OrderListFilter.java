package com.netisov.tim.commandes.dto.order;

import com.netisov.tim.commandes.domain.Order;
import com.netisov.tim.commandes.dto.PagingAndSortingRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

import static com.netisov.tim.commandes.domain.QOrder.order;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ApiModel(value = "Orders list filter", description = "A filter for a list of orders")
@Getter
@Setter
public class OrderListFilter extends PagingAndSortingRequest<Order> {
    @ApiModelProperty(value = "Search string for id(number)", position = 10)
    public String id;

    @ApiModelProperty(value = "Search by state code", position = 20)
    public String stateCode;

    @ApiModelProperty(value = "Search by client name", position = 30)
    public String clientName;

    @ApiModelProperty(value = "Search by creation date(from)", example = "2019-09-11 22-00-00", position = 40)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    public LocalDateTime fromCreatedAt;

    @ApiModelProperty(value = "Search by creation date(to)", example = "2019-09-11 22-00-00", position = 50)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    public LocalDateTime toCreatedAt;


    @Override
    public Predicate predicate() {
        BooleanBuilder builder = new BooleanBuilder();
        if (NumberUtils.isParsable(id)) {
            builder.and(order.id.eq(Long.parseLong(id)));
        }
        if (!StringUtils.isEmpty(stateCode)) {
            builder.and(order.state.code.eq(stateCode));
        }
        if (!StringUtils.isEmpty(clientName)) {
            builder.and(order.client.name.likeIgnoreCase("%" + clientName + "%"));
        }
        if (fromCreatedAt != null) {
            builder.and(order.createdAt.goe(fromCreatedAt));
        }
        if (toCreatedAt != null) {
            builder.and(order.createdAt.loe(toCreatedAt));
        }
        return builder;
    }

    @Override
    protected OrderSpecifier<?> orderBy(com.querydsl.core.types.Order orderBy, String field) {
        switch (field) {
            case "created":
                return new OrderSpecifier<>(orderBy, order.createdAt);
            case "state":
                return new OrderSpecifier<>(orderBy, order.state.label);
            case "client":
                return new OrderSpecifier<>(orderBy, order.client.name);
            case "price":
                return new OrderSpecifier<>(orderBy, order.price);
            case "shippingCountry":
                return new OrderSpecifier<>(orderBy, order.shippingCountry.label);
            default:
                return new OrderSpecifier<>(orderBy, order.id);
        }
    }
}
