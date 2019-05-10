package com.example.demo.repository.helper;

import com.example.demo.model.Person;
import com.example.demo.repository.filter.FilterPerson;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author KarlsCode.
 */

public class PersonRepositoryImpl implements PersonRepositoryQueries {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Person> filter(FilterPerson filterPerson) {
        final StringBuilder sb = new StringBuilder();
        final Map<String, Object> params = new HashMap<>();
        sb.append(" SELECT bean FROM Person bean JOIN bean.phones phone WHERE 1=1 ");

        preencherNomeSeNecessario(filterPerson, sb, params);
        preencherCpfSeNecessario(filterPerson, sb, params);
        preencherDddSeNecessario(filterPerson, sb, params);
        preencherNumeroTelefoneSeNecessario(filterPerson, sb, params);

        Query query = manager.createQuery(sb.toString(), Person.class);
        preencherParametrosDaQuery(params, query);

        return query.getResultList();
    }

    private void preencherNumeroTelefoneSeNecessario(FilterPerson filterPerson, StringBuilder sb, Map<String, Object> params) {
        if(StringUtils.hasText(filterPerson.getPhone())) {
            sb.append(" AND phone.number = :number ");
            params.put("number", filterPerson.getPhone());
        }
    }

    private void preencherDddSeNecessario(FilterPerson filterPerson, StringBuilder sb, Map<String, Object> params) {
        if(StringUtils.hasText(filterPerson.getDdd())) {
            sb.append(" AND phone.ddd = :ddd ");
            params.put("ddd", filterPerson.getDdd());
        }
    }

    private void preencherCpfSeNecessario(FilterPerson filterPerson, StringBuilder sb, Map<String, Object> params) {
        if(StringUtils.hasText(filterPerson.getCpf())) {
            sb.append(" AND bean.cpf LIKE :cpf ");
            params.put("cpf", "%" + filterPerson.getCpf() + "%");
        }
    }

    private void preencherNomeSeNecessario(FilterPerson filterPerson, StringBuilder sb, Map<String, Object> params) {
        if(StringUtils.hasText(filterPerson.getName())) {
            sb.append(" AND bean.name LIKE :name ");
            params.put("name", "%" + filterPerson.getName() + "%");
        }
    }

    private void preencherParametrosDaQuery(Map<String, Object> params, Query query) {
        for(Map.Entry<String, Object> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
    }
}
