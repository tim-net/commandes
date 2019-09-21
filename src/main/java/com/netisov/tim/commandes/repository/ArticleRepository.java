package com.netisov.tim.commandes.repository;

import com.netisov.tim.commandes.domain.Article;
import com.netisov.tim.commandes.repository.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepository extends BaseRepository<Article, String> {

    @Query("select a from Article a order by a.label asc")
    List<Article> getAllSortedByLabel();
}
