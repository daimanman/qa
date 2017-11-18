import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import com.man.erpcenter.common.utils.ObjectUtil;
import com.man.erpcenter.sales.biz.util.HttpRequestUtil;
import com.man.erpcenter.sales.biz.util.YhHttpUtil;


public class TestRR {

	
	
	@Test
	public void test01() throws Exception{
		ObjectUtil util = new ObjectUtil();
		Map<String,String> cookie = util.getHeadersMap("RR_c.properties");
		Map<String,Object> params = util.getHttpParam("RR_q.properties");
		System.out.println(params);
		for(int i =1;i<3;i++){
			int offset = 656970+ (i-1)*10;
			params.put("offset",offset);
			String content = YhHttpUtil.sendHttpPost("http://browse.renren.com/sAjax.do", params, cookie);
			IOUtils.write(content, new FileOutputStream(new File("E:\\a"+i+".html")));
		}
		System.out.println("is ok ---");
	}
	
}
