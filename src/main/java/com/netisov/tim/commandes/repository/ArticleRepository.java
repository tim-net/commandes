package com.netisov.tim.commandes.repository;

import com.netisov.tim.commandes.domain.Article;
import com.netisov.tim.commandes.repository.base.BaseRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends BaseRepository<Article, String> {
}
