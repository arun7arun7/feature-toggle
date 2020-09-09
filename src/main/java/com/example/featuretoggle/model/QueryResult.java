package com.example.featuretoggle.model;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Tuple;
import javax.persistence.TupleElement;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class QueryResult {

    private List<String> columns;
    private List<List<Object>> rows;

    public QueryResult(List<String> columns, List<List<Object>> rows) {
        this.columns = columns;
        this.rows = rows;
    }

    public QueryResult(List<Tuple> tuples) {
        this.columns = constructColumns(tuples);
        this.rows = constructRows(tuples);
    }

    private List<String> constructColumns(List<Tuple> tuples) {
        List<String> columns = new ArrayList<>();
        if (!tuples.isEmpty()) {
            columns = tuples.get(0).getElements().stream().map(TupleElement::getAlias).collect(Collectors.toList());
        }
        return columns;
    }

    private List<List<Object>> constructRows(List<Tuple> tuples) {
        List<List<Object>> rows = new ArrayList<>();
        for (Tuple tuple : tuples) {
            List<Object> row = new ArrayList<>();
            List<TupleElement<?>> elements = tuple.getElements();
            for (TupleElement element : elements) {
                String colName = element.getAlias();
                row.add(tuple.get(colName));
            }
            rows.add(row);
        }
        return rows;
    }
}
