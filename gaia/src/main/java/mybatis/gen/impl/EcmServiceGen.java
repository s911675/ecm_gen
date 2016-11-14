package mybatis.gen.impl;

import java.sql.Connection;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import mybatis.gen.base.AbstractEcmBaseGen;
import mybatis.gen.util.EcmVelocityUtil;

public class EcmServiceGen extends AbstractEcmBaseGen {
    public static final String OUTPUT_FILE_SUFFIX = "Service";
    public static final String OUTPUT_FILE_EXTENSION = ".java";
	
	private static final String PROPERTY_ID_OUTPUT_ROOT = "output.root.service";
	private static final String PROPERTY_ID_TEMPLATE = "template.service";
	private static final String PROPERTY_ID_PATH = "pkg.service";
	
	public EcmServiceGen(VelocityContext context, Connection conn, String tableName, String dbName) throws Exception {
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
	    return (String)context.get("serviceFileName");
	}
	
	@Override
	public String getFullOutFileName() {
		return (String)Velocity.getProperty(PROPERTY_ID_OUTPUT_ROOT) + getRelativePath () + getOutFileName();
	}
}
