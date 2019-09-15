package com.netisov.tim.commandes.dto.order;

import com.netisov.tim.commandes.domain.OrderItem;
import com.netisov.tim.commandes.dto.PagingAndSortingRequest;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
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

import java.time.LocalDateTime;

import static com.netisov.tim.commandes.domain.QOrderItem.orderItem;

@NoArgsConstructor(access = AccessLevel.PUBLIC)
@ApiModel(value = "Orders list filter", description = "A filter for a list of orders")
@Getter
@Setter
public class OrderListFilter extends PagingAndSortingRequest<OrderItem> {
    @ApiModelProperty(value = "Search string for id(number)", position = 10)
    public String id;

    @ApiModelProperty(value = "Search by state code", position = 20)
    public String stateCode;

    @ApiModelProperty(value = "Search by client name", position = 30)
    public String clientName;

    @ApiModelProperty(value = "Search by creation date(from)", position = 40)
    public LocalDateTime fromCreatedAt;

    @ApiModelProperty(value = "Search by creation date(to)", position = 40)
    public LocalDateTime toCreatedAt;


    @Override
    public Predicate predicate() {
        BooleanBuilder builder = new BooleanBuilder();
        if (NumberUtils.isParsable(id)) {
            builder.and(orderItem.id.eq(Integer.parseInt(id)));
        }
        if (!StringUtils.isEmpty(stateCode)) {
            builder.and(orderItem.state.code.eq(stateCode));
        }
        if (!StringUtils.isEmpty(clientName)) {
            builder.and(orderItem.client.name.likeIgnoreCase("%" + clientName + "%"));
        }
        if (fromCreatedAt != null) {
            builder.and(orderItem.createdAt.goe(fromCreatedAt));
        }
        if (toCreatedAt != null) {
            builder.and(orderItem.createdAt.loe(toCreatedAt));
        }
        return builder;
    }

    @Override
    protected OrderSpecifier<?> orderBy(Order order, String field) {
        switch (field) {
            case "created":
                return new OrderSpecifier<>(order, orderItem.createdAt);
            case "status":
                return new OrderSpecifier<>(order, orderItem.state.label);
            case "client":
                return new OrderSpecifier<>(order, orderItem.client.name);
            default:
                return new OrderSpecifier<>(order, orderItem.id);
        }
    }
}
