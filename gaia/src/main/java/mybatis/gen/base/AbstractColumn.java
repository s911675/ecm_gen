package mybatis.gen.base;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.Velocity;

import mybatis.gen.util.EcmVelocityUtil;

public abstract class AbstractColumn extends BaseObject {
	protected String tableCat;
	protected String tableSchem;
	protected String tableName;
	protected String columnName;
	protected String dataType;
	protected String typeName;
	protected String columnSize;
	protected String bufferLength;
	protected String decimalDigits;
	protected String numPrecRadix;
	protected String nullable;
	protected String remarks;
	protected String columnDef;
	protected String sqlDataType;
	protected String sqlDatetimeSub;
	protected String charOctetLength;
	protected String ordinalPosition;
	protected String isNullable;
	protected String scopeCatalog;
	protected String scopeSchema;
	protected String scopeTable;
	protected String sourceDataType;
	protected String isAutoincrement;
	protected String isGeneratedcolumn;
	
	protected String isPK = "NO";
	
	// additional method
	public String getJavaMethodName() throws Exception {
		return EcmVelocityUtil.toCamelCaseU(columnName);
	}

	public String getFieldName() throws Exception {
		return EcmVelocityUtil.toCamelCase(columnName);
	}

	public String getFieldType() throws Exception {
		return EcmVelocityUtil.convertDbType2JavaType(typeName, columnSize);
	}

	public String getDesc () {
		return "isPk=" + getIsPK() + ", size=" + columnSize + ", NULL="+isNullable + ", Default="+columnDef; 
	}
	
	public String getMappedFullName() throws Exception {
	    String mappedFullName = getJavaMethodName();
	    
        Object tmpMappedFullName = Velocity.getProperty(this.getColumnName());
        if(tmpMappedFullName!=null) {
            if (tmpMappedFullName instanceof String) {
                mappedFullName = (String)tmpMappedFullName;
            } else {
                @SuppressWarnings("unchecked")
                List<String> tmpMappedFullNameList = (List<String>)tmpMappedFullName;
                mappedFullName = tmpMappedFullNameList.get(0);
            }
        }
        
        return StringUtils.capitalize(mappedFullName);
    }

	public String getMappedFullNameL() throws Exception {
	    return StringUtils.uncapitalize(getMappedFullName());
	}
	
	// getter, setter
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
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getColumnSize() {
		return columnSize;
	}
	public void setColumnSize(String columnSize) {
		this.columnSize = columnSize;
	}
	public String getBufferLength() {
		return bufferLength;
	}
	public void setBufferLength(String bufferLength) {
		this.bufferLength = bufferLength;
	}
	public String getDecimalDigits() {
		return decimalDigits;
	}
	public void setDecimalDigits(String decimalDigits) {
		this.decimalDigits = decimalDigits;
	}
	public String getNumPrecRadix() {
		return numPrecRadix;
	}
	public void setNumPrecRadix(String numPrecRadix) {
		this.numPrecRadix = numPrecRadix;
	}
	public String getNullable() {
		return nullable;
	}
	public void setNullable(String nullable) {
		this.nullable = nullable;
	}
	public String getRemarks() {
		if (remarks==null) {
			return "";
		}
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getColumnDef() {
		return columnDef;
	}
	public void setColumnDef(String columnDef) {
		this.columnDef = columnDef;
	}
	public String getSqlDataType() {
		return sqlDataType;
	}
	public void setSqlDataType(String sqlDataType) {
		this.sqlDataType = sqlDataType;
	}
	public String getSqlDatetimeSub() {
		return sqlDatetimeSub;
	}
	public void setSqlDatetimeSub(String sqlDatetimeSub) {
		this.sqlDatetimeSub = sqlDatetimeSub;
	}
	public String getCharOctetLength() {
		return charOctetLength;
	}
	public void setCharOctetLength(String charOctetLength) {
		this.charOctetLength = charOctetLength;
	}
	public String getOrdinalPosition() {
		return ordinalPosition;
	}
	public void setOrdinalPosition(String ordinalPosition) {
		this.ordinalPosition = ordinalPosition;
	}
	public String getIsNullable() {
		return isNullable;
	}
	public void setIsNullable(String isNullable) {
		this.isNullable = isNullable;
	}
	public String getScopeCatalog() {
		return scopeCatalog;
	}
	public void setScopeCatalog(String scopeCatalog) {
		this.scopeCatalog = scopeCatalog;
	}
	public String getScopeSchema() {
		return scopeSchema;
	}
	public void setScopeSchema(String scopeSchema) {
		this.scopeSchema = scopeSchema;
	}
	public String getScopeTable() {
		return scopeTable;
	}
	public void setScopeTable(String scopeTable) {
		this.scopeTable = scopeTable;
	}
	public String getSourceDataType() {
		return sourceDataType;
	}
	public void setSourceDataType(String sourceDataType) {
		this.sourceDataType = sourceDataType;
	}
	public String getIsAutoincrement() {
		return isAutoincrement;
	}
	public void setIsAutoincrement(String isAutoincrement) {
		this.isAutoincrement = isAutoincrement;
	}
	public String getIsGeneratedcolumn() {
		return isGeneratedcolumn;
	}
	public void setIsGeneratedcolumn(String isGeneratedcolumn) {
		this.isGeneratedcolumn = isGeneratedcolumn;
	}
	public String getIsPK() {
		return isPK;
	}

	public void setIsPK(String isPK) {
		this.isPK = isPK;
	}

}
