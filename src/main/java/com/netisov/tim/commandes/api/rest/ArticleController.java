package com.netisov.tim.commandes.api.rest;

import com.netisov.tim.commandes.api.rest.representation.ArticleConverter;
import com.netisov.tim.commandes.api.rest.representation.ArticleRepresentation;
import com.netisov.tim.commandes.service.ArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Rest controller for operations with articles
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private final Logger log = LoggerFactory.getLogger(ArticleController.class);
    private final ArticleService articleService;
    private final ArticleConverter articleConverter;

    public ArticleController(ArticleService articleService, ArticleConverter articleConverter) {
        this.articleService = articleService;
        this.articleConverter = articleConverter;
    }

    @GetMapping
    public List<ArticleRepresentation> getAll() {
        log.info("REST request to get articles sorted by label");
        return articleService.getAllSortedByLAbel().stream().map(articleConverter).collect(Collectors.toList());
    }
}
