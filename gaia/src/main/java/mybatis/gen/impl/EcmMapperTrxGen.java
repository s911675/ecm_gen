package mybatis.gen.impl;

import java.sql.Connection;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import mybatis.gen.base.AbstractEcmBaseGen;
import mybatis.gen.util.EcmVelocityUtil;

public class EcmMapperTrxGen extends AbstractEcmBaseGen {
    public static final String OUTPUT_FILE_SUFFIX = "TrxMapper";
    public static final String OUTPUT_FILE_EXTENSION = ".java";
	
	private static final String PROPERTY_ID_OUTPUT_ROOT = "output.root.mapperTrx";
	private static final String PROPERTY_ID_TEMPLATE = "template.mapperTrx";
	private static final String PROPERTY_ID_PATH = "pkg.mapperTrx";
	
	public EcmMapperTrxGen(VelocityContext context, Connection conn, String tableName, String dbName) throws Exception {
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
	    return (String)context.get("mapperTrxFileName");
	}
	
	public String getPackagePath() {
        String pkgMapperTrx = (String)context.get("pkgMapperTrx");
        if (pkgMapperTrx == null || "".equals(pkgMapperTrx)) {
            return "";
        }
        
        return pkgMapperTrx.replaceAll("\\.", "\\/") + "/";
    }
	
	
	@Override
	public String getFullOutFileName() {
		return (String)Velocity.getProperty(PROPERTY_ID_OUTPUT_ROOT) + getPackagePath() + getOutFileName();
	}
}
