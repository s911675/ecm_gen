package mybatis.gen.meta;

import java.sql.ResultSet;
import java.sql.SQLException;

import mybatis.gen.base.AbstractColumn;

public class OracleColumn extends AbstractColumn {
	public OracleColumn (ResultSet rs) throws SQLException {
		setTableCat( rs.getString("TABLE_CAT") );
		setTableSchem( rs.getString("TABLE_SCHEM") );
		setTableName( rs.getString("TABLE_NAME").toLowerCase() );
		setColumnName( rs.getString("COLUMN_NAME").toLowerCase() );
		setDataType( rs.getString("DATA_TYPE") );
		setTypeName( rs.getString("TYPE_NAME").toLowerCase() );
		setColumnSize( rs.getString("COLUMN_SIZE") );
		setBufferLength( rs.getString("BUFFER_LENGTH") );
		setDecimalDigits( rs.getString("DECIMAL_DIGITS") );
		setNumPrecRadix( rs.getString("NUM_PREC_RADIX") );
		setNullable( rs.getString("NULLABLE") );
		setRemarks( rs.getString("REMARKS") );
		setColumnDef( rs.getString("COLUMN_DEF") );
		setSqlDataType( rs.getString("SQL_DATA_TYPE") );
		setSqlDatetimeSub( rs.getString("SQL_DATETIME_SUB") );
		setCharOctetLength( rs.getString("CHAR_OCTET_LENGTH") );
		setOrdinalPosition( rs.getString("ORDINAL_POSITION") );
		setIsNullable( rs.getString("IS_NULLABLE") );
	}

}
