package mybatis;

import java.util.ArrayList;
import java.util.List;

import mybatis.gen.util.EcmVelocityUtil;


/**
 * 중요! 중요! 중요! 중요! 중요! 중요! 중요! 중요!
 * !!!!!!!!!! JAVA 8 이상으로 사용해 주세요~
 * 
 * @author s9116
 *
 */
public class EcmMybatisGenMain {
	
	public static void main(String[] args) throws Exception {
	    String dbType = args[0];
	    String pkgName = args[1];
	    String dbPkgName = args[2];
        String tableName = args[3];
        
        if (dbType == null || "".equals(dbType)) {
            System.out.println("dbType이 정의되어 있지 않습니다.");
            return;
        }
        
        if (pkgName == null || "".equals(pkgName)) {
            System.out.println("모듈명이 정의되어 있지 않습니다.");
            return;
        }
        
        if (dbPkgName == null || "".equals(dbPkgName)) {
            System.out.println("DB 주제영역이 정의되어 있지 않습니다.");
            return;
        }
        
        if (tableName == null || "".equals(tableName)) {
            System.out.println("테이블 명이 정의되어 있지 않습니다.");
            return;
        }
	    
	    String conf = "C:/ECM/workspace/ecm-template/src/main/resources/velocity_ecm.properties";

        List<String> codeGenList = new ArrayList<String> ();
        codeGenList.add("entity");
        codeGenList.add("xml");
        codeGenList.add("xmlTrx");
        codeGenList.add("mapper");
        codeGenList.add("mapperTrx");
        
        EcmVelocityUtil.generate(conf, codeGenList, dbType, pkgName.toLowerCase(), dbPkgName.toLowerCase(), tableName.toUpperCase());
	}

}
