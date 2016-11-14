package mybatis.gen.impl;

import java.sql.Connection;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import mybatis.gen.base.AbstractEcmBaseGen;
import mybatis.gen.util.EcmVelocityUtil;

public class EcmEntityGen extends AbstractEcmBaseGen {
    public static final String OUTPUT_FILE_SUFFIX = "VO";
    public static final String OUTPUT_FILE_EXTENSION = ".java";
	
	private static final String PROPERTY_ID_OUTPUT_ROOT = "output.root.entity";
	private static final String PROPERTY_ID_TEMPLATE = "template.entity";
	private static final String PROPERTY_ID_PATH = "pkg.entity";
	
	public EcmEntityGen(VelocityContext context, Connection conn, String tableName, String dbName) throws Exception {
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
	    return (String)context.get("entityFileName");
	}
		   
    public String getPackagePath() {
        String pkgEntity = (String)context.get("pkgEntity");
        if (pkgEntity == null || "".equals(pkgEntity)) {
            return "";
        }
        
        return pkgEntity.replaceAll("\\.", "\\/") + "/";
    }
	
	@Override
	public String getFullOutFileName() {
		return (String)Velocity.getProperty(PROPERTY_ID_OUTPUT_ROOT) + getPackagePath() + getOutFileName();
	}
}
