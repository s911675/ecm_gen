package gaia.gen;

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
public class EcmCodeGenCaMain {
	
	public static void main(String[] args) throws Exception {
//	     String dbType = args[0];
//      String pkgName = args[1];
//        String tableName = args[2];

        String dbType = "db2";
        String pkgName = "ca";
        String dbPkgName = "commonadmin";
        String tableName = "tca_auth_tgt";
        
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
        
        String conf = "C:/ECM/workspace/gaia/src/main/resources/velocity_ecm_ca.properties";

        List<String> codeGenList = new ArrayList<String> ();
//        codeGenList.add("service");
//        codeGenList.add("serviceImpl");
//        codeGenList.add("controller");
//        codeGenList.add("jsp");
        codeGenList.add("js");
        codeGenList.add("provider");
        codeGenList.add("handler");
        codeGenList.add("msg");
        
//        codeGenList.add("entityEVO");
//        codeGenList.add("xmlBiz");
//        codeGenList.add("mapperBiz");

//        EcmVelocityUtil.generate(conf, codeGenList, dbType, pkgName.toLowerCase(), dbPkgName.toLowerCase(), tableName.toUpperCase());
        
        EcmVelocityUtil.generate(conf, codeGenList);
	}

}
