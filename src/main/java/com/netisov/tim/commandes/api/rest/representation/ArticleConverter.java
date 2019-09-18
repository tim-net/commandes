package com.netisov.tim.commandes.api.rest.representation;

import com.netisov.tim.commandes.domain.Article;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ArticleConverter implements Function<Article, ArticleRepresentation> {
    @Override
    public ArticleRepresentation apply(Article article) {
        return ArticleRepresentation.builder()
                .code(article.getCode())
                .family(ArticleFamilyRepresentation.builder()
                        .code(article.getFamily().getCode())
                        .label(article.getFamily().getLabel())
                        .build())
                .build();
    }
}
