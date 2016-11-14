package mybatis.gen.impl;

import java.sql.Connection;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import mybatis.gen.base.AbstractEcmBaseGen;
import mybatis.gen.util.EcmVelocityUtil;

public class EcmHandlerGen extends AbstractEcmBaseGen {
	public static final String OUTPUT_FILE_SUFFIX = ".eventHandler";
	public static final String OUTPUT_FILE_EXTENSION = ".js";
	
	private static final String PROPERTY_ID_OUTPUT_ROOT = "output.root.handler";
	private static final String PROPERTY_ID_TEMPLATE = "template.handler";
	private static final String PROPERTY_ID_PATH = "path.handler";


	public EcmHandlerGen(VelocityContext context, Connection conn, String tableName, String dbName) throws Exception {
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
	    return (String)context.get("handlerFileName");
	}
	
	@Override
	public String getFullOutFileName() {
		return (String)Velocity.getProperty(PROPERTY_ID_OUTPUT_ROOT) + getRelativePath () + getOutFileName();
	}

}
