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
public class EcmCodeGenMain {
	
	public static void main(String[] args) throws Exception {
        String conf = "C:/ECM/workspace/ecm-template/src/main/resources/velocity_ecm.properties";

        List<String> codeGenList = new ArrayList<String> ();
        codeGenList.add("service");
        codeGenList.add("serviceImpl");
        codeGenList.add("controller");
        codeGenList.add("jsp");
        codeGenList.add("js");
        codeGenList.add("provider");
        codeGenList.add("handler");
        codeGenList.add("msg");
        
        codeGenList.add("entityEVO");
        codeGenList.add("xmlBiz");
        codeGenList.add("mapperBiz");
        
        EcmVelocityUtil.generate(conf, codeGenList);
	}

}
