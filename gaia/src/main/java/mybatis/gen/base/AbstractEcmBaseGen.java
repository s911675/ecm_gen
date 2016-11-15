package mybatis.gen.base;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

import mybatis.gen.impl.EcmControllerGen;
import mybatis.gen.impl.EcmEntityEVOGen;
import mybatis.gen.impl.EcmEntityGen;
import mybatis.gen.impl.EcmHandlerGen;
import mybatis.gen.impl.EcmJsGen;
import mybatis.gen.impl.EcmJspGen;
import mybatis.gen.impl.EcmMapperBizGen;
import mybatis.gen.impl.EcmMapperGen;
import mybatis.gen.impl.EcmMapperTrxGen;
import mybatis.gen.impl.EcmMsgGen;
import mybatis.gen.impl.EcmProviderGen;
import mybatis.gen.impl.EcmServiceGen;
import mybatis.gen.impl.EcmServiceImplGen;
import mybatis.gen.impl.EcmXmlBizGen;
import mybatis.gen.impl.EcmXmlGen;
import mybatis.gen.impl.EcmXmlTrxGen;
import mybatis.gen.util.EcmFactory;
import mybatis.gen.util.EcmVelocityUtil;

public abstract class AbstractEcmBaseGen {
	
	protected VelocityContext context;
	protected Connection conn;
	protected String tableName;
	protected String dbName;
	
	/**
	 * Get template file name by property id.
	 * ex) (String)Velocity.getProperty("template.entity")
	 *  ==> "template_ecm/entity.vm"
	 *  
	 * @return (String)Velocity.getProperty(property id of template file name)
	 */
	public abstract String getTemplateFileName ();
	
	/**
	 * Get relative path for output.
	 * Relative path can get by package(when output is java file.) or path(when output is none java file.)
	 * ex) VelocityUtil.pkgProp2Path(context, "pkg.entity");
	 *  ==> "entity/biz/ca"
	 * ex) VelocityUtil.pkgProp2Path(context, "path.xml");
	 *  ==> resource/mybatis/system
	 * 
	 * @return
	 */
	public abstract String getRelativePath ();
	
	/**
	 * Get output file name.
	 * ex) (String)context.get("entityClassName") + OUTPUT_FILE_SUFFIX;
	 * ==> "UserAuth.java"
	 * @return
	 */
	public abstract String getOutFileName ();
	
	/**
	 * Get full output file name.
	 * ex) (String)Velocity.getProperty("output.root.entity") + getRelativePath () + getOutFileName();
	 * ==> C:/outroot/java/entity/biz/ca/UserAuth.java
	 * @return
	 */
	public abstract String getFullOutFileName ();
	
	public AbstractEcmBaseGen (VelocityContext context, Connection conn, String tableName, String dbName) throws Exception {
		if (context == null || conn == null || tableName == null || tableName.trim().equals("") || dbName == null || dbName.trim().equals("")) {
			throw new Exception ("");
		}
		this.context = context;
		this.conn = conn;
		this.tableName = tableName;
		this.dbName = dbName;
	}

    public void createFile() {
		try {
            Template template =  null;
            template = Velocity.getTemplate(getTemplateFileName());
            
            File file = new File(getFullOutFileName());
            File parent = file.getParentFile();
            if(!parent.exists() && !parent.mkdirs()){
                throw new IllegalStateException("Couldn't create directory: " + parent);
            }

            if (!file.exists()) {
				file.createNewFile();
			}

            BufferedWriter bw = new BufferedWriter( new FileWriter(file.getAbsoluteFile()));

            if ( template != null) {
                template.merge(context, bw);
            }

            bw.flush();
            bw.close();

            System.out.println (String.format("##### template = %s\n##### output file = %s ", getTemplateFileName(), getFullOutFileName()));
        } catch( Exception e ) {
            System.out.println(e);
        }
	}
    
	public void putPerEntityContext () throws Exception {
		if (EcmVelocityUtil.loadedTableSet.get(tableName) == null) {
			EcmVelocityUtil.loadedTableSet.put(tableName, tableName);
		} else {
			return;
		}
		
		AbstractTable table = EcmFactory.getTable(conn, context, tableName, dbName);
		
		List<AbstractColumn> columnList = table.getColumnList();
		List<AbstractColumn> primaryKeyList = table.getPrimaryKeyList();
		List<AbstractColumn> noPrimaryKeyList = table.getNoPrimaryKeyList();
		System.out.println("size of columnList ==> "+ columnList.size());
		
		Set<String> tmpImportSet = new HashSet<String> ();
		for (AbstractColumn tmpColumn : columnList ) {
			String tmpDataType = null;
			try {
				tmpDataType = tmpColumn.getFieldType();
			} catch (Exception ex) {
				ex.printStackTrace();
				// pass
			}
			tmpImportSet.add(tmpDataType);
		}
		
		List<String> importList = new ArrayList<String> ();
		importList.addAll(tmpImportSet);
		
		context.put("table", table);
		context.put("importList", importList);
		context.put("columnList", columnList);
		context.put("primaryKeyList", primaryKeyList);
		context.put("noPrimaryKeyList", noPrimaryKeyList);
		context.put("firstColumn", columnList.get(0));

		String lowerTableName = tableName.toLowerCase();
		String entityName = EcmVelocityUtil.toCamelCaseU(lowerTableName);
		String entityNameL = EcmVelocityUtil.toCamelCase(lowerTableName);

		String bizName = (String)Velocity.getProperty(tableName);
		String bizNameL = "";
		if (bizName==null || bizName.equals("")) {
		    bizName = entityName;
		    bizNameL = entityNameL;
		} else {
		    bizNameL = Character.toLowerCase(bizName.charAt(0)) + bizName.substring(1);
		}
		context.put("bizName", bizName);
		context.put("bizNameL", bizNameL);
		
		context.put("entityName", entityName);
        
		// class name
		context.put("entityClassName", entityName + EcmEntityGen.OUTPUT_FILE_SUFFIX);
		context.put("entityClassNameL", entityNameL + EcmEntityGen.OUTPUT_FILE_SUFFIX);

		context.put("mapperClassName", entityName + EcmMapperGen.OUTPUT_FILE_SUFFIX);
		context.put("mapperClassNameL", entityNameL + EcmMapperGen.OUTPUT_FILE_SUFFIX);

		context.put("mapperTrxClassName", entityName + EcmMapperTrxGen.OUTPUT_FILE_SUFFIX);
		context.put("mapperTrxClassNameL", entityNameL + EcmMapperTrxGen.OUTPUT_FILE_SUFFIX);

		context.put("serviceClassName", bizName + EcmServiceGen.OUTPUT_FILE_SUFFIX);
		context.put("serviceClassNameL", bizNameL + EcmServiceGen.OUTPUT_FILE_SUFFIX);

		context.put("serviceImplClassName", bizName + EcmServiceImplGen.OUTPUT_FILE_SUFFIX);
		context.put("serviceImplClassNameL", bizNameL + EcmServiceImplGen.OUTPUT_FILE_SUFFIX);
		
		context.put("controllerClassName", bizName + EcmControllerGen.OUTPUT_FILE_SUFFIX);
		context.put("controllerClassNameL", bizNameL + EcmControllerGen.OUTPUT_FILE_SUFFIX);

		context.put("entityEVOClassName", bizName + EcmEntityEVOGen.OUTPUT_FILE_SUFFIX);
        context.put("entityEVOClassNameL", bizNameL + EcmEntityEVOGen.OUTPUT_FILE_SUFFIX);

        context.put("mapperBizClassName", bizName + EcmMapperBizGen.OUTPUT_FILE_SUFFIX);
        context.put("mapperBizClassNameL", bizNameL + EcmMapperBizGen.OUTPUT_FILE_SUFFIX);
		
		// file name
		context.put("entityFileName", entityName + EcmEntityGen.OUTPUT_FILE_SUFFIX + EcmEntityGen.OUTPUT_FILE_EXTENSION);
		context.put("mapperFileName", entityName + EcmMapperGen.OUTPUT_FILE_SUFFIX+ EcmMapperGen.OUTPUT_FILE_EXTENSION);
		context.put("mapperTrxFileName", entityName + EcmMapperTrxGen.OUTPUT_FILE_SUFFIX+ EcmMapperTrxGen.OUTPUT_FILE_EXTENSION);

		context.put("xmlFileName", entityName + EcmXmlGen.OUTPUT_FILE_SUFFIX+ EcmXmlGen.OUTPUT_FILE_EXTENSION);
        context.put("xmlTrxFileName", entityName + EcmXmlTrxGen.OUTPUT_FILE_SUFFIX+ EcmXmlTrxGen.OUTPUT_FILE_EXTENSION);
/*
		context.put("serviceFileName", entityName + EcmServiceGen.OUTPUT_FILE_SUFFIX+ EcmServiceGen.OUTPUT_FILE_EXTENSION);
		context.put("serviceImplFileName", entityName + EcmServiceImplGen.OUTPUT_FILE_SUFFIX+ EcmServiceImplGen.OUTPUT_FILE_EXTENSION);
		context.put("controllerFileName", entityName + EcmControllerGen.OUTPUT_FILE_SUFFIX+ EcmControllerGen.OUTPUT_FILE_EXTENSION);


		context.put("jspFileName", entityName + EcmJspGen.OUTPUT_FILE_SUFFIX+ EcmJspGen.OUTPUT_FILE_EXTENSION);
		
		context.put("jsFileName", entityName + EcmJsGen.OUTPUT_FILE_SUFFIX+ EcmJsGen.OUTPUT_FILE_EXTENSION);
		context.put("providerFileName", entityName + EcmProviderGen.OUTPUT_FILE_SUFFIX+ EcmProviderGen.OUTPUT_FILE_EXTENSION);
		context.put("handlerFileName", entityName + EcmHandlerGen.OUTPUT_FILE_SUFFIX+ EcmHandlerGen.OUTPUT_FILE_EXTENSION);
		
		context.put("msgFileName", entityName + EcmMsgGen.OUTPUT_FILE_SUFFIX+ EcmMsgGen.OUTPUT_FILE_EXTENSION);
		
		context.put("entityEVOFileName", entityName + EcmEntityEVOGen.OUTPUT_FILE_SUFFIX + EcmEntityEVOGen.OUTPUT_FILE_EXTENSION);
		context.put("xmlBizFileName", entityName + EcmXmlBizGen.OUTPUT_FILE_SUFFIX + EcmXmlBizGen.OUTPUT_FILE_EXTENSION);
		context.put("mapperBizFileName", entityName + EcmMapperBizGen.OUTPUT_FILE_SUFFIX + EcmMapperBizGen.OUTPUT_FILE_EXTENSION);
*/		

        context.put("serviceFileName", bizName + EcmServiceGen.OUTPUT_FILE_SUFFIX+ EcmServiceGen.OUTPUT_FILE_EXTENSION);
        context.put("serviceImplFileName", bizName + EcmServiceImplGen.OUTPUT_FILE_SUFFIX+ EcmServiceImplGen.OUTPUT_FILE_EXTENSION);
        context.put("controllerFileName", bizName + EcmControllerGen.OUTPUT_FILE_SUFFIX+ EcmControllerGen.OUTPUT_FILE_EXTENSION);


        context.put("jspFileName", bizName + EcmControllerGen.OUTPUT_FILE_SUFFIX + EcmJspGen.OUTPUT_FILE_SUFFIX+ EcmJspGen.OUTPUT_FILE_EXTENSION);
        
        context.put("jsFileName", bizName + EcmControllerGen.OUTPUT_FILE_SUFFIX + EcmJsGen.OUTPUT_FILE_SUFFIX+ EcmJsGen.OUTPUT_FILE_EXTENSION);
        context.put("providerFileName", entityName + EcmProviderGen.OUTPUT_FILE_SUFFIX+ EcmProviderGen.OUTPUT_FILE_EXTENSION);
        context.put("handlerFileName", entityName + EcmHandlerGen.OUTPUT_FILE_SUFFIX+ EcmHandlerGen.OUTPUT_FILE_EXTENSION);
        
        context.put("msgFileName", entityName + EcmMsgGen.OUTPUT_FILE_SUFFIX+ EcmMsgGen.OUTPUT_FILE_EXTENSION);
        
        context.put("entityEVOFileName", bizName + EcmEntityEVOGen.OUTPUT_FILE_SUFFIX + EcmEntityEVOGen.OUTPUT_FILE_EXTENSION);
        context.put("xmlBizFileName", bizName + EcmXmlBizGen.OUTPUT_FILE_SUFFIX + EcmXmlBizGen.OUTPUT_FILE_EXTENSION);
        context.put("mapperBizFileName", bizName + EcmMapperBizGen.OUTPUT_FILE_SUFFIX + EcmMapperBizGen.OUTPUT_FILE_EXTENSION);

	}	
	
	public void generate() throws Exception {
		putPerEntityContext ();
		createFile ();
	}
}
