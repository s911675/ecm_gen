package mybatis.gen.impl;

import java.sql.Connection;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import mybatis.gen.base.AbstractEcmBaseGen;
import mybatis.gen.util.EcmVelocityUtil;

public class EcmEntityEVOGen extends AbstractEcmBaseGen {
    public static final String OUTPUT_FILE_SUFFIX = "EVO";
    public static final String OUTPUT_FILE_EXTENSION = ".java";
	
	private static final String PROPERTY_ID_OUTPUT_ROOT = "output.root.entityEVO";
	private static final String PROPERTY_ID_TEMPLATE = "template.entityEVO";
	private static final String PROPERTY_ID_PATH = "pkg.entityEVO";
	
	public EcmEntityEVOGen(VelocityContext context, Connection conn, String tableName, String dbName) throws Exception {
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
	    return (String)context.get("entityEVOFileName");
	}
	
	@Override
	public String getFullOutFileName() {
		return (String)Velocity.getProperty(PROPERTY_ID_OUTPUT_ROOT) + getRelativePath () + getOutFileName();
	}
}
