package mybatis.gen.impl;

import java.sql.Connection;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import mybatis.gen.base.AbstractEcmBaseGen;
import mybatis.gen.util.EcmVelocityUtil;

public class EcmXmlTrxGen extends AbstractEcmBaseGen {
	
	public static final String OUTPUT_FILE_SUFFIX = "Trx";
	public static final String OUTPUT_FILE_EXTENSION = ".xml";
	
	private static final String PROPERTY_ID_OUTPUT_ROOT = "output.root.xmlTrx";
	private static final String PROPERTY_ID_TEMPLATE = "template.xmlTrx";
	private static final String PROPERTY_ID_PATH = "path.xmlTrx";
	
	public EcmXmlTrxGen(VelocityContext context, Connection conn, String tableName, String dbName) throws Exception {
		super(context, conn, tableName, dbName);
	}
	
	@Override
	public String getTemplateFileName() {
		return (String)Velocity.getProperty(PROPERTY_ID_TEMPLATE);
	}

	@Override
	public String getRelativePath () {
		return EcmVelocityUtil.pkgProp2Path(context, PROPERTY_ID_PATH);

	}

	@Override
	public String getOutFileName() {
	    return (String)context.get("xmlTrxFileName");
	}
	
	public String getDbType() {
	    String jdbcType = (String)context.get("jdbc.type");
	    if ("db1".equals(jdbcType)) {
	        jdbcType = "oracle_db1";
	    } else if ("db2".equals(jdbcType)) {
	        jdbcType = "oracle_db2";
	    }
	    
        return jdbcType + "/";
    }
    
	public String getDbPkgName() {
        String dbPkgName = (String)context.get("dbPkgName");
        if (dbPkgName == null || "".equals(dbPkgName)) {
            return "";
        }
        
        return dbPkgName + "/";
    }
	
	@Override
	public String getFullOutFileName() {
		return (String)Velocity.getProperty(PROPERTY_ID_OUTPUT_ROOT) + getDbType() + getDbPkgName() + getOutFileName();
	}

}
