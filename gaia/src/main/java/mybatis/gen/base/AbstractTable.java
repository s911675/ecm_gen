package mybatis.gen.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class AbstractTable extends BaseObject {
    protected String tableCat;
    protected String tableSchem;
    protected String tableName;
    protected String tableType;
    protected String remarks;
    protected String typeCat;
    protected String typeSchem;
    protected String typeName;
    protected String selfReferencingColName;
    protected String refGeneration;
    
    // remarks 가 예상한 동작을 안함.
    protected String comments;

    protected Map<String, String> primaryKeyMap = new HashMap<String, String> ();
    protected List<AbstractColumn> primaryKeyList = new ArrayList<AbstractColumn> ();
    protected List<AbstractColumn> noPrimaryKeyList = new ArrayList<AbstractColumn> ();
    protected List<String> exportedKeyList = new ArrayList<String> ();
    protected List<AbstractColumn> columnList = new ArrayList<AbstractColumn> ();

    public String getTableCat() {
        return tableCat;
    }

    public void setTableCat(String tableCat) {
        this.tableCat = tableCat;
    }

    public String getTableSchem() {
        return tableSchem;
    }

    public void setTableSchem(String tableSchem) {
        this.tableSchem = tableSchem;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableType() {
        return tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getTypeCat() {
        return typeCat;
    }

    public void setTypeCat(String typeCat) {
        this.typeCat = typeCat;
    }

    public String getTypeSchem() {
        return typeSchem;
    }

    public void setTypeSchem(String typeSchem) {
        this.typeSchem = typeSchem;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getSelfReferencingColName() {
        return selfReferencingColName;
    }

    public void setSelfReferencingColName(String selfReferencingColName) {
        this.selfReferencingColName = selfReferencingColName;
    }

    public String getRefGeneration() {
        return refGeneration;
    }

    public void setRefGeneration(String refGeneration) {
        this.refGeneration = refGeneration;
    }
    
    public List<AbstractColumn> getPrimaryKeyList() {
        return primaryKeyList;
    }
    public void setPrimaryKeyList(List<AbstractColumn> primaryKeyList) {
        this.primaryKeyList = primaryKeyList;
    }
    
    public List<String> getExportedKeyList() {
        return exportedKeyList;
    }
    public void setExportedKeyList(List<String> exportedKeyList) {
        this.exportedKeyList = exportedKeyList;
    }
    public List<AbstractColumn> getColumnList() {
        return columnList;
    }
    public void setColumnList(List<AbstractColumn> columnList) {
        this.columnList = columnList;
    }

    public String getComments() {
        if (comments==null) {
            return "";
        }
        
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
    public List<AbstractColumn> getNoPrimaryKeyList() {
        return noPrimaryKeyList;
    }

    public void setNoPrimaryKeyList(List<AbstractColumn> noPrimaryKeyList) {
        this.noPrimaryKeyList = noPrimaryKeyList;
    }
}
