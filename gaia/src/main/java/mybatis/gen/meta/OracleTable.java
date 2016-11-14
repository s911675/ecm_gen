package mybatis.gen.meta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import mybatis.gen.base.AbstractColumn;
import mybatis.gen.base.AbstractTable;

public class OracleTable extends AbstractTable {
    /**
     * @param conn
     * @param upperCase
     * @param upperCase2
     */
    public OracleTable(Connection conn, String tableName, String dbName) throws SQLException {
        // TODO Auto-generated constructor stub
	    
		DatabaseMetaData meta = conn.getMetaData();
		
		// 1. Set table information.
		System.out.println(String.format("dbName=%s, tableName=%s", dbName, tableName));
		try (ResultSet rsTables = meta.getTables(null, dbName, tableName, null)) {
			if(rsTables.next()) {
				setTableCat( rsTables.getString("TABLE_CAT") );
				setTableSchem( rsTables.getString("TABLE_SCHEM") );
				setTableName( rsTables.getString("TABLE_NAME").toLowerCase() );
				setTableType( rsTables.getString("TABLE_TYPE") );
				setRemarks( rsTables.getString("REMARKS") );
			}
		} catch (Exception ex) {
			throw ex;
		}

		// 1.1. Table comments
		String tblCommentsSql = "SELECT COMMENTS FROM all_tab_comments WHERE TABLE_NAME = '" + tableName + "'";
		try (
				Statement tblSt = conn.createStatement();
				ResultSet tblRs = tblSt.executeQuery(tblCommentsSql);
		) {
			if(tblRs.next()) {
				setComments(tblRs.getString("COMMENTS"));
			}
		} catch (Exception ex) {
			throw ex;
		}
				
		// 2. set primary key list
		try ( ResultSet rsPK = meta.getPrimaryKeys(null, dbName, tableName) ) {
			while (rsPK.next()) {
				String colNm = rsPK.getString("COLUMN_NAME").toUpperCase();
				primaryKeyMap.put(colNm, colNm);
			}
		} catch (Exception ex) {
			throw ex;
		}

		// 3. set exported key list
//		try ( ResultSet rsFK = meta.getExportedKeys(null, dbName, tableName) ) {
//			
//			while (rsFK.next()) {
//				exportedKeyList.add(rsFK.getString("COLUMN_NAME"));
//			}
//		} catch (Exception ex) {
//			throw ex;
//		}
		
		// 4. set column list info
		try ( ResultSet rsColumnList = meta.getColumns(null, dbName, tableName, null) ) {
		    columnList.clear();
		    primaryKeyList.clear();
		    noPrimaryKeyList.clear();
		    
			while (rsColumnList.next()) {
				AbstractColumn col = new OracleColumn(rsColumnList);
				System.out.println("col ==> "+ col.getColumnName());
				columnList.add(col);
				
				if (primaryKeyMap.get(col.getColumnName().toUpperCase()) != null) {
					col.setIsPK("OK");
					primaryKeyList.add(col);
				} else {
				    col.setIsPK("NO");
				    noPrimaryKeyList.add(col);
				}
			}
			
		} catch (Exception ex) {
			throw ex;
		}
		
		// 4.1. select column comments
		String sql = "SELECT COLUMN_NAME, COMMENTS FROM all_col_comments WHERE TABLE_NAME = '" + tableName + "'";
		Map<String, String> commentMap = new HashMap<String, String> ();
		try (
				Statement colSt = conn.createStatement();
				ResultSet colRs = colSt.executeQuery(sql);
		) {
			while (colRs.next()) {
				commentMap.put(colRs.getString("COLUMN_NAME"), colRs.getString("COMMENTS"));
			}
		} catch (Exception ex) {
			throw ex;
			
		}
		
		// 4.2. update column comments
		if(commentMap.size()!=0) {
			for(AbstractColumn tmpColumn : this.columnList) {
				String comments = commentMap.get(tmpColumn.getColumnName().toUpperCase());
				tmpColumn.setRemarks(comments);
			}
		}
		
		return;
	}
}
