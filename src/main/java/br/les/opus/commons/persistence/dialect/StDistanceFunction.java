package br.les.opus.commons.persistence.dialect;

import java.util.List;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;

public class StDistanceFunction extends StandardSQLFunction {
	

	public StDistanceFunction() {
		super("st_distance", StandardBasicTypes.DOUBLE);
	}

	
	@SuppressWarnings("rawtypes")
	@Override
	public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor sessionFactory) {
		final StringBuilder buf = new StringBuilder();
		buf.append( getName() ).append( '(' );
		for ( int i = 0; i < arguments.size(); i++ ) {
			
			/**
			 * If the argument is a string (representing a column name) we do
			 * nothing. If the arguments is a WKT (any question mark), we must wrap it with 
			 * ST_GeogFromText function. This will prevent the query to return 
			 * cartesian distance. What we want is a spheroidal minimum distance between 
			 * two geographies in meters
			 */
			String arg = arguments.get( i ).toString();
			if (!arg.equals("?")) {
				buf.append( arguments.get( i ) );
			} else {
				buf.append("ST_GeogFromText(" + arguments.get( i ) +  ")");
			}
			
			if ( i < arguments.size() - 1 ) {
				buf.append( ", " );
			}
		}
		return buf.append( ')' ).toString();
	}

	
}
