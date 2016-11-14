package mybatis.gen.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;

public class EcmVelocityUtil {
	public static Map<String, String> loadedTableSet = new HashMap<String, String> ();
	
	/**
     * get current date with pattern
     * <p/>
     * ex) String curDate = DateUtil.getCurrentDateString("yyyyMMdd"); //
     * curDate : 20090428
     * 
     * @param pattern
     *            date pattern
     * @return String representing current date (type of pattern)
     */
    public static String getCurrentDate(String pattern) {
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        return df.format(new Date());
    }
	
	/**
	 * Convert database type string to java data type String.
	 * ex) convertDbType2JavaType ("varchar")
	 * ==> "String"
	 * @param type
	 * @param columnSize 
	 * @return
	 * @throws Exception
	 */
	public static final String convertDbType2JavaType (final String type, String columnSize) throws Exception {
		String tmpType = type.toLowerCase();
		if (tmpType.contains("int")) {
			return "Integer";
		} else if (tmpType.contains("varchar") || tmpType.contains("varchar2") || tmpType.contains("char")) {
			return "String";
		} else if (tmpType.contains("date")) {
//			return "Date";
			return "Timestamp";
		} else if (tmpType.contains("timestamp")) {
			return "Timestamp";
		} else if (tmpType.contains("decimal") || tmpType.contains("float")) {
			return "Float";
		} else if (tmpType.contains("double")) {
			return "Double";
		} else if (tmpType.contains("number")) {
		    if (Integer.parseInt(columnSize) < 10) {
		        return "int";
		    } else {
		        return "long";
		    }
		} else {
			throw new Exception("not supported type getDb2JavaType () type="+type);
		}
	}

	/**
	 * Convert string to camelCase string but first character is upperCase.
	 * ex) toCamelCaseU ("user_id")
	 * ==> "UserId"
	 * 
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static final String toCamelCaseU (final String name) {
		String res = "";
		
		String[] a = name.split("_");
		
		for (String tmp : a) {
			res += StringUtils.capitalize(tmp);
		}
		
		return res;
	}
	
	/**
	 * Convert string to camelCase.
	 * ex) toCamelCase ("user_id")
	 * ==> "userId"
	 * 
	 * @param str
	 * @return user_no ==> userNo
	 * @throws Exception
	 */
	public static final String toCamelCase (final String str) {
		return StringUtils.uncapitalize(toCamelCaseU(str));
	}
	
	public static String getProperty( String key ) {
		return StringEscapeUtils.unescapeJava((String)Velocity.getProperty(key));
	}
	
    /**
     * Create velocity merged output file.
     * 
     * @param context
     * @param outFileName
     * @param templateFileName
     */
    public static void createFile(VelocityContext context, String outFileName, String templateFileName) {
		try {
            Template template =  null;

            template = Velocity.getTemplate(templateFileName);
            
            File file = new File(outFileName);
            File parent = file.getParentFile();
            if(!parent.exists() && !parent.mkdirs()){
                throw new IllegalStateException("Couldn't create dir: " + parent);
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

            System.out.println (String.format("##### File created!! template=$s, output file = %s ", templateFileName,outFileName));
        } catch( Exception e ) {
            System.out.println(e);
        }
	}
    
	/**
	 * Put path string to context.
	 * aaa.bbb ==> aaa/bbb
	 * @param context
	 * @param pkgPathCtxNm
	 * @param propertyNm
	 */
	public static void setPathContext (VelocityContext context, String pkgPathCtxNm, String propertyNm ) {
		String pkg = (String)Velocity.getProperty(propertyNm);
		String pkgPath = pkg.replaceAll("\\.", "\\/") + "/";
		context.put(pkgPathCtxNm, pkgPath);
	}

	/**
	 * aaa.bbb ==> aaa/bbb
	 * @param context
	 * @param propertyNm
	 * @return
	 */
	public static String pkgProp2Path (VelocityContext context, String propertyNm ) {
		return ((String)Velocity.getProperty(propertyNm)).replaceAll("\\.", "\\/") + "/";
	}

	@SuppressWarnings("unchecked")
	public static List<String> getTableNameListFromProperty (String tableNameListId) throws Exception {
	    List<String> tableNameList = null;
        Object tableName = Velocity.getProperty(tableNameListId);
        if (tableName instanceof String) {
            tableNameList = new ArrayList<String> ();
            tableNameList.add((String)tableName);
        } else {
            tableNameList = (List<String>)tableName;
        }
        
        if (tableNameList==null || tableNameList.size()==0) {
            throw new Exception ("No tableNameList exist in property file.");
        }
        
        return tableNameList;
	}

	@SuppressWarnings("unchecked")
	public static List<String> getTableNameListFromProperty (VelocityContext context, String tableNameListId) throws Exception {
	    List<String> tableNameList = null;
	    Object tableName = Velocity.getProperty(tableNameListId);
	    if (tableName instanceof String) {
	        tableNameList = new ArrayList<String> ();
	        tableNameList.add((String)tableName);
	    } else {
	        tableNameList = (List<String>)tableName;
	    }
	    
	    if (tableNameList==null || tableNameList.size()==0) {
	        throw new Exception ("No tableNameList exist in property file.");
	    }
	    
	    return tableNameList;
	}
	
	public static VelocityContext makeCommonContext (String conf) {
		Velocity.init(conf);
		
		VelocityContext context = new VelocityContext();
		
        // Author information.
		context.put("author", EcmVelocityUtil.getProperty("author"));
		context.put("createDate", getCurrentDate("yyyy.MM.dd"));

		// module name
		context.put("modulePkgName", (String)Velocity.getProperty("module.pkg.name"));

		// package ST
		// entity package path
		context.put("pkgEntity", (String)Velocity.getProperty("pkg.entity"));
		context.put("pkgMapper", (String)Velocity.getProperty("pkg.mapper"));
		context.put("pkgMapperTrx", (String)Velocity.getProperty("pkg.mapperTrx"));
		context.put("pkgService", (String)Velocity.getProperty("pkg.service"));
		context.put("pkgServiceImpl", (String)Velocity.getProperty("pkg.serviceImpl"));
		context.put("pkgController", (String)Velocity.getProperty("pkg.controller"));
		
		context.put("pkgEntityEVO", (String)Velocity.getProperty("pkg.entityEVO"));
		context.put("pkgMapperBiz", (String)Velocity.getProperty("pkg.mapperBiz"));
		// package END
		
		// path
		setPathContext (context, "pathJsp", "path.jsp" ); // used in controller.

		return context;
	} // makeCommonContext

	/**
     * Get jdbc connetion
     * @return
     * @throws Exception
     */
    public static Connection getConnection () throws Exception {
        String driver = (String)Velocity.getProperty("jdbc.driver");
        String url = (String)Velocity.getProperty("jdbc.url");
        String user = (String)Velocity.getProperty("jdbc.user");
        String pwd = (String)Velocity.getProperty("jdbc.pwd");
        
        Class.forName(driver);
        
        Connection conn = DriverManager.getConnection( url, user, pwd);
        
        System.out.println(String.format("\n!!!!!Connected database for driver=%s, url=%s, user=%s", driver, url, user));
        
        return conn;
    } // getConnection ()

	/**
     * Get jdbc connetion
     * @return
     * @throws Exception
     */
    public static Connection getConnection (VelocityContext context) throws Exception {
        String driver = (String)context.get("jdbc.driver");
        String url = (String)context.get("jdbc.url");
        String user = (String)context.get("jdbc.user");
        String pwd = (String)context.get("jdbc.pwd");
        
        Class.forName(driver);
        
        Connection conn = DriverManager.getConnection(url, user, pwd);
        
        System.out.println(String.format("\n!!!!!Connected database for driver=%s, url=%s, user=%s", driver, url, user));
        
        return conn;
    } // getConnection ()
    
    public static void generatePerTable(List<String> codeGenList, VelocityContext context, Connection conn, String tableName, String schem) throws Exception {
        
        for(String type : codeGenList) {
            System.out.println("\n$$$$$$ Generating " + type.toUpperCase() + " for " + tableName + " table!");
            EcmFactory.getCodeGen(type, context, conn, tableName, schem).generate();
        }
    }
    
    public static void generate (String conf, List<String> codeGenList) throws Exception {
        try {
            System.out.println("@@@@@@@@@ Start!!!!!!");
            // 1. make common context
            Velocity.init(conf);
            
            VelocityContext context = EcmVelocityUtil.makeCommonContext(conf);
            
            String driver = (String)Velocity.getProperty("jdbc.driver");
            context.put("jdbc.driver", driver);
            
            List<String> tableNameList = EcmVelocityUtil.getTableNameListFromProperty("tableNameList");
            
            try (Connection conn = EcmVelocityUtil.getConnection()) {
                String dbName = (String)Velocity.getProperty("jdbc.dbName");
                
                System.out.println("!!!!!!! Start generating.");
                if(tableNameList != null) {
                    for(String tmpTableName : tableNameList) {
                        EcmVelocityUtil.generatePerTable (codeGenList, context, conn, tmpTableName, dbName);
                    }
                }
                System.out.println("\n!!!!!!! generated successfully.");
    
            } catch (Exception ex) {
                throw ex;
            }
    
            Velocity.init();
        
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("\n@@@@@@@@@ End!!!!!!");
        }
        
        return;
    }

    /**
     * @param conf
     * @param codeGenList
     * @param subject
     * @param tableName
     */
    public static void generate(String conf, List<String> codeGenList, String dbType, String pkgName, String dbPkgName, String tableName) {
        try {
            System.out.println("@@@@@@@@@ Start!!!!!!");
            
            // 1. make common context
            Velocity.init(conf);
            
            VelocityContext context = EcmVelocityUtil.makeCommonContext(conf);
            
            String driver = (String)Velocity.getProperty("jdbc2.driver");
            String url = (String)Velocity.getProperty("jdbc2.url");
            String user = (String)Velocity.getProperty("jdbc2.user");
            String pwd = (String)Velocity.getProperty("jdbc2.pwd");
            String schem = ((String)Velocity.getProperty("jdbc2.schem")).toUpperCase();
            
            if ("db1".equals(dbType)) {
                driver = (String)Velocity.getProperty("jdbc1.driver");
                url = (String)Velocity.getProperty("jdbc1.url");
                user = (String)Velocity.getProperty("jdbc1.user");
                pwd = (String)Velocity.getProperty("jdbc1.pwd");
                schem = ((String)Velocity.getProperty("jdbc1.schem")).toUpperCase();
            }
            
            context.put("jdbc.driver", driver);
            context.put("jdbc.url", url);
            context.put("jdbc.user", user);
            context.put("jdbc.pwd", pwd);
            context.put("jdbc.schem", schem);
            context.put("jdbc.type", dbType);
            context.put("modulePkgName", pkgName);
            context.put("dbPkgName", dbPkgName);
            
            if ("prototype.sample".equals(pkgName)) {
                context.put("pkgEntity", (String)Velocity.getProperty("pkg.entity") +"."+ dbPkgName);
                context.put("pkgMapper", (String)Velocity.getProperty("pkg.mapper") +"."+ pkgName +".dao."+ dbType);
                context.put("pkgMapperTrx", (String)Velocity.getProperty("pkg.mapperTrx") + "."+ dbPkgName +".dao."+ dbType);
            } else {
                context.put("pkgEntity", (String)Velocity.getProperty("pkg.entity") +"."+ dbPkgName);
                context.put("pkgMapper", (String)Velocity.getProperty("pkg.mapper") +".biz."+ pkgName +".dao");
                context.put("pkgMapperTrx", (String)Velocity.getProperty("pkg.mapperTrx") + ".repository."+ dbPkgName);
            }                
            
            
            try (Connection conn = EcmVelocityUtil.getConnection(context)) {                
                System.out.println("!!!!!!! Start generating.");
                
                DatabaseMetaData databaseMetadata = conn.getMetaData();
                
                String types[] = { "TABLE" };
                String searchTable = null;
                if (!"ALL".equals(tableName)) {
//                    searchTable = tableName +"%";
                    searchTable = tableName;
                }
                
                ResultSet rs = databaseMetadata.getTables(null, schem, searchTable, types);
                while (rs.next()) {
                    String rsTableName = rs.getString("TABLE_NAME");
                    
                    EcmVelocityUtil.generatePerTable(codeGenList, context, conn, rsTableName, schem);
                }
                    
                
                System.out.println("\n!!!!!!! generated successfully.");
    
            } catch (Exception ex) {
                throw ex;
            }
    
            Velocity.init();
        
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("\n@@@@@@@@@ End!!!!!!");
        }
        
        return;
    }
}
