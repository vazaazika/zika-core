package br.les.opus.commons.persistence.filtering;

import java.util.StringTokenizer;

import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class Clause {

	private String field;
	
	private String queryingField;
	
	private Object value;
	
	private ClauseOperator operator;
	
	public void createAliases(Criteria criteria) {
		if (!field.contains(".")) {
			return;
		}
		StringTokenizer tokenizer = new StringTokenizer(field, ".");
		String lastAlias = "";
		String lastToken = null;
		int index = 1;
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			if (tokenizer.hasMoreTokens()) {
				String nextAlias = "alias" + index++;
				criteria.createAlias(lastAlias + token, nextAlias);
				lastAlias = nextAlias + ".";
			}
			lastToken = token;
		}
		this.queryingField = lastAlias + lastToken;
	}
	
	
	/**
	 * Diz se o valor da clausula pode ser usado com um like ou não
	 * @return
	 */
	public boolean isLikeable() {
		if (value instanceof String) {
			String stringValue = (String)value;
			return stringValue.contains("*");
		}
		return false;
	}
	
	public Criterion toCrierion() {
		if (operator.equals(ClauseOperator.IS_NULL)) { 
			return Restrictions.isNull(queryingField);
		} else if (operator.equals(ClauseOperator.EQUALS) && isLikeable()) { 
			return Restrictions.ilike(queryingField, this.getSQLValue());
		} else if (operator.equals(ClauseOperator.EQUALS) && !isLikeable()) {
			return Restrictions.eq(queryingField, this.value);
		} else if (operator.equals(ClauseOperator.GREATER)) {
			return Restrictions.gt(queryingField, this.value);
		} else if (operator.equals(ClauseOperator.GREATER_EQUAL)) {
			return Restrictions.ge(queryingField, this.value);
		} else if (operator.equals(ClauseOperator.LESSER)) {
			return Restrictions.lt(queryingField, this.value);
		} else if (operator.equals(ClauseOperator.LESSER_EQUAL)) {
			return Restrictions.le(queryingField, this.value);
		} else if (operator.equals(ClauseOperator.IS_NOT_NULL)) {
			return Restrictions.isNotNull(queryingField);
		} else if (operator.equals(ClauseOperator.NOT_EQUALS)) {
			return Restrictions.ne(queryingField, this.value);
		}
		throw new FilteringException("Nenhum Criterion associado ao operador " + this);
	}
	
	/**
	 * Remove os asteriscos da string colocando % no lugar
	 * @param value
	 * @return
	 */
	private String replaceAsterisks(String value) {
		String newValue = value;
		int lastIndex = value.length() - 1;
		if (value.charAt(0) == '*') {
			newValue = "%" + newValue.substring(1, lastIndex + 1);
		}
		if (value.charAt(lastIndex) == '*') {
			newValue = newValue.substring(0, lastIndex) + "%"; 
		}
		return newValue;
	}
	
	public String getSQLValue() {
		return replaceAsterisks((String)this.value);
	}
	
	public Clause(String field, Object value, ClauseOperator operator) {
		this.field = field;
		this.value = value;
		this.operator = operator;
		this.queryingField = field;
	}
	

	public String getField() {
		return field;
	}

	public ClauseOperator getOperator() {
		return operator;
	}

	@Override
	public String toString() {
		return "Clause [field=" + field + ", value=" + value + ", operator="
				+ operator + "]";
	}
}
