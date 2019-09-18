package com.netisov.tim.commandes.service;

import com.netisov.tim.commandes.domain.Article;
import com.netisov.tim.commandes.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ArticleService {
    private final ArticleRepository articleRepository;

    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public List<Article> getAllSortedByLAbel() {
        return articleRepository.getAllSortedByLabel();
    }
}
