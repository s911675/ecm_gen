package mybatis.gen.util;

import java.sql.Connection;

import org.apache.velocity.VelocityContext;

import mybatis.gen.base.AbstractEcmBaseGen;
import mybatis.gen.base.AbstractTable;
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
import mybatis.gen.meta.OracleTable;

public class EcmFactory {
    
    /**
     * get code generator.
     * 
     * @param type
     * @param context
     * @param conn
     * @param tableName
     * @param dbName 
     * @return
     * @throws Exception
     */
    public static AbstractEcmBaseGen getCodeGen (String type, VelocityContext context, Connection conn, String tableName, String dbName) throws Exception {
        AbstractEcmBaseGen codeGen = null;
        switch (type) {
            case "entity":
                codeGen = new EcmEntityGen (context, conn, tableName, dbName);
                break;
    
            case "xml":
                codeGen = new EcmXmlGen (context, conn, tableName, dbName);
                break;
            case "xmlTrx": 
                codeGen = new EcmXmlTrxGen (context, conn, tableName, dbName);
                break;
            
            case "mapper": 
                codeGen = new EcmMapperGen (context, conn, tableName, dbName);
                break;
            case "mapperTrx": 
                codeGen = new EcmMapperTrxGen (context, conn, tableName, dbName);
                break;
            
            case "service": 
                codeGen = new EcmServiceGen (context, conn, tableName, dbName);
                break;
            case "serviceImpl": 
                codeGen = new EcmServiceImplGen (context, conn, tableName, dbName);
                break;
            case "controller": 
                codeGen = new EcmControllerGen (context, conn, tableName, dbName);
                break;
    
            case "jsp": 
                codeGen = new EcmJspGen (context, conn, tableName, dbName);
                break;
            
            case "js": 
                codeGen = new EcmJsGen (context, conn, tableName, dbName);
                break;
            case "provider": 
                codeGen = new EcmProviderGen (context, conn, tableName, dbName);
                break;
            case "handler": 
                codeGen = new EcmHandlerGen (context, conn, tableName, dbName);
                break;
    
            case "msg": 
                codeGen = new EcmMsgGen (context, conn, tableName, dbName);
                break;
    
            case "entityEVO": 
                codeGen = new EcmEntityEVOGen (context, conn, tableName, dbName);
                break;
            case "xmlBiz": 
                codeGen = new EcmXmlBizGen (context, conn, tableName, dbName);
                break;
            case "mapperBiz": 
                codeGen = new EcmMapperBizGen (context, conn, tableName, dbName);
                break;

            default:
                throw new Exception ("Invalide type of Code Gen!!!!!!!");
        }
        
        return codeGen;
    }

    /**
     * Get table for database.
     * @param conn
     * @param context 
     * @param tableName
     * @param dbName 
     * @return
     * @throws Exception
     */
    public static AbstractTable getTable (Connection conn, VelocityContext context, String tableName, String dbName) throws Exception {
        String driver = (String)context.get("jdbc.driver");
        
        switch (driver) {
        case "oracle.jdbc.driver.OracleDriver": return new OracleTable (conn, tableName.toUpperCase(), dbName.toUpperCase());

        default: throw new Exception("Not supported database type. type=" + driver);
        }
    }
}
