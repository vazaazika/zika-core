package br.les.opus.commons.persistence.dialect;

import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.spatial.GeometryType;
import org.hibernate.spatial.dialect.postgis.PostgisDialect;
import org.hibernate.type.StandardBasicTypes;

public class AdaptedPostgisDialect extends PostgisDialect {

	private static final long serialVersionUID = -6400162421363212770L;

	protected void registerTypesAndFunctions() {

		registerColumnType(java.sql.Types.STRUCT, "geometry");

		registerFunction(
				"dimension", new StandardSQLFunction(
				"st_dimension",
				StandardBasicTypes.INTEGER
		)
		);
		registerFunction(
				"geometrytype", new StandardSQLFunction(
				"st_geometrytype", StandardBasicTypes.STRING
		)
		);
		registerFunction(
				"srid", new StandardSQLFunction(
				"st_srid",
				StandardBasicTypes.INTEGER
		)
		);
		registerFunction(
				"envelope", new StandardSQLFunction(
				"st_envelope",
				GeometryType.INSTANCE
		)
		);
		registerFunction(
				"astext", new StandardSQLFunction(
				"st_astext",
				StandardBasicTypes.STRING
		)
		);
		registerFunction(
				"asbinary", new StandardSQLFunction(
				"st_asbinary",
				StandardBasicTypes.BINARY
		)
		);
		registerFunction(
				"isempty", new StandardSQLFunction(
				"st_isempty",
				StandardBasicTypes.BOOLEAN
		)
		);
		registerFunction(
				"issimple", new StandardSQLFunction(
				"st_issimple",
				StandardBasicTypes.BOOLEAN
		)
		);
		registerFunction(
				"boundary", new StandardSQLFunction(
				"st_boundary",
				GeometryType.INSTANCE
		)
		);

		// Register functions for spatial relation constructs
		registerFunction(
				"overlaps", new StandardSQLFunction(
				"st_overlaps",
				StandardBasicTypes.BOOLEAN
		)
		);
		registerFunction(
				"intersects", new StandardSQLFunction(
				"st_intersects",
				StandardBasicTypes.BOOLEAN
		)
		);
		registerFunction(
				"equals", new StandardSQLFunction(
				"st_equals",
				StandardBasicTypes.BOOLEAN
		)
		);
		registerFunction(
				"contains", new StandardSQLFunction(
				"st_contains",
				StandardBasicTypes.BOOLEAN
		)
		);
		registerFunction(
				"crosses", new StandardSQLFunction(
				"st_crosses",
				StandardBasicTypes.BOOLEAN
		)
		);
		registerFunction(
				"disjoint", new StandardSQLFunction(
				"st_disjoint",
				StandardBasicTypes.BOOLEAN
		)
		);
		registerFunction(
				"touches", new StandardSQLFunction(
				"st_touches",
				StandardBasicTypes.BOOLEAN
		)
		);
		registerFunction(
				"within", new StandardSQLFunction(
				"st_within",
				StandardBasicTypes.BOOLEAN
		)
		);
		registerFunction(
				"relate", new StandardSQLFunction(
				"st_relate",
				StandardBasicTypes.BOOLEAN
		)
		);

		// register the spatial analysis functions
		registerFunction("distance", new StDistanceFunction());
		
		registerFunction(
				"buffer", new StandardSQLFunction(
				"st_buffer",
				GeometryType.INSTANCE
		)
		);
		registerFunction(
				"convexhull", new StandardSQLFunction(
				"st_convexhull",
				GeometryType.INSTANCE
		)
		);
		registerFunction(
				"difference", new StandardSQLFunction(
				"st_difference",
				GeometryType.INSTANCE
		)
		);
		registerFunction(
				"intersection", new StandardSQLFunction(
				"st_intersection", new GeometryType()
		)
		);
		registerFunction(
				"symdifference",
				new StandardSQLFunction("st_symdifference", GeometryType.INSTANCE)
		);
		registerFunction(
				"geomunion", new StandardSQLFunction(
				"st_union",
				GeometryType.INSTANCE
		)
		);

		//register Spatial Aggregate function
		registerFunction(
				"extent", new StandardSQLFunction(
				"extent",
				GeometryType.INSTANCE
		)
		);

		//other common functions
		registerFunction(
				"dwithin", new StandardSQLFunction(
				"st_dwithin",
				StandardBasicTypes.BOOLEAN
		)
		);
		registerFunction(
				"transform", new StandardSQLFunction(
				"st_transform",
				GeometryType.INSTANCE
		)
		);
	}
}
