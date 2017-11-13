package br.les.opus.commons.persistence.filtering;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.text.StrTokenizer;


/**
 * Cria um filtro de dados a ser usado nas consultas
 * @author Diego Cedrim
 */
public class Filter implements Iterable<Clause>{
	
	private FieldValueConverter converter;
	
	private List<Clause> clauses;
	
	private Class<?> entityClass;
	
	/**
	 * Inicia o mapa de cláusulas a partir de uma lista de Strings. Cada posição da lista
	 * deve ter valores do tipo <nome do campo>, <valor do filtro>
	 * @param stringClauses
	 * @return
	 */
	private List<Clause> getClausesFromStringList(List<String> stringClauses) {
		List<Clause> clauses = new ArrayList<>();
		for (String stringClause : stringClauses) {
			ClauseOperator operator = ClauseOperator.getOperator(stringClause);
			
			StrTokenizer tokenizer = new StrTokenizer(stringClause, operator.toString());
			
			if ((operator.isNullComparator() && tokenizer.size() != 1) ||
					(!operator.isNullComparator() && tokenizer.size() != 2)) {
				throw new FilteringException("Filtros devem ser compostos de dois valores no formato <campo><operador><valor>");
			}
			
			String field = tokenizer.nextToken();
			String value = null;
			
			if (!operator.isNullComparator()) {
				if (tokenizer.hasNext()) {
					value = tokenizer.nextToken();
				} else {
					value = "**";
				}
				Object objectValue = converter.convert(field, value, this.entityClass);
				clauses.add(new Clause(field, objectValue, operator));
			} else {
				clauses.add(new Clause(field, null, operator));
			}
			
			
		}
		return clauses;
	}
	
	public Filter(List<String> stringClauses, Class<?> entityClass) {
		this.converter = new FieldValueConverter();
		this.entityClass = entityClass;
		this.clauses = this.getClausesFromStringList(stringClauses);
	}

	@Override
	public Iterator<Clause> iterator() {
		return this.clauses.iterator();
	}

	@Override
	public String toString() {
		return "Filter [clauses=" + clauses + "]";
	}

	public List<Clause> getClauses() {
		return clauses;
	}

	public void setClauses(List<Clause> clauses) {
		this.clauses = clauses;
	}

	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		this.entityClass = entityClass;
	}
	
}
