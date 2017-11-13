package br.les.opus.commons.persistence.filtering;


public enum ClauseOperator {
	EQUALS("="),
	GREATER(">"),
	GREATER_EQUAL(">="),
	LESSER("<"),
	LESSER_EQUAL("<="),
	NOT_EQUALS("!="),
	IS_NULL("=null", true),
	IS_NOT_NULL("!=null", true);
	
	private String operator;
	
	private boolean isNullComparator;
	
	private ClauseOperator(String operator) {
		this.operator = operator;
		this.isNullComparator = false;
	}
	
	private ClauseOperator(String operator, boolean isNullComparator) {
		this.operator = operator;
		this.isNullComparator = isNullComparator;
	}
	
	public boolean isNullComparator() {
		return isNullComparator;
	}
	
	public static ClauseOperator getOperator(String stringClause) {
		if (stringClause.contains("!=null")) {
			return IS_NOT_NULL; 
		} else if (stringClause.contains("=null")) {
			return IS_NULL; 
		} else if (stringClause.contains(">=")) {
			return GREATER_EQUAL; 
		} else if (stringClause.contains(">")) {
			return GREATER; 
		} else if (stringClause.contains("<=")) {
			return LESSER_EQUAL; 
		} else if (stringClause.contains("<")) {
			return LESSER; 
		} else if (stringClause.contains("!=")) {
			return NOT_EQUALS; 
		} else if (stringClause.contains("=")) {
			return EQUALS; 
		}
		throw new FilteringException("Nenhum operador válido foi adicionado. Os operadores são: >=, >, <, <=, =, !=null ou =null");
	}
	
	@Override
	public String toString() {
		return this.operator;
	}
}
