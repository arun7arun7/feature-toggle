package com.example.featuretoggle.repository.secondary.custom;

import com.example.featuretoggle.model.QueryResult;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import java.util.Map;


@Repository
public class SecondarySourceReadRepository {

    private EntityManager entityManager;

    @Autowired
    public SecondarySourceReadRepository(@Qualifier("secondaryEM") EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public QueryResult execute(String queryString, Map<String, Object> params) {
        Query query = this.entityManager.createNativeQuery(queryString, Tuple.class).unwrap(org.hibernate.query.Query.class);
        for (String key : params.keySet()) {
            query.setParameter(key, params.get(key));
        }
        query.setMaxResults(2);
        return new QueryResult(query.getResultList());
    }

}
