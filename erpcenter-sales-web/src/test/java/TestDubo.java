import java.util.Arrays;
import java.util.Map;

import org.junit.Test;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.alibaba.dubbo.rpc.service.GenericService;
import com.dev.ym.po.Dog;
import com.dev.ym.service.DemoService;
import com.man.erpcenter.sales.util.DubboCallbackUtil;

public class TestDubo {

	@Test
	public void testGet() {
		Object result = DubboCallbackUtil.invoke("com.dev.ym.service.DemoService", "sayHello", Arrays.asList(1),
				"172.17.26.156:2183", "1.1.dev");
		System.out.println(result);
	}

	@Test
	public void testGet1(){
		ApplicationConfig application = new ApplicationConfig();
		application.setName("erpcenter");
		 RegistryConfig registryConfig = new RegistryConfig();
		 registryConfig.setAddress("127.0.0.1:2183");
		 registryConfig.setCheck(false);
		 registryConfig.setProtocol("zookeeper");
		 
		ReferenceConfig<GenericService> refConfig = new ReferenceConfig<GenericService>();
		refConfig.setInterface(DemoService.class);
		refConfig.setVersion("1.1.dev");
		refConfig.setGeneric(true);
		refConfig.setRegistry(registryConfig);
		refConfig.setApplication(application);
		refConfig.setCheck(false);
		
		
		GenericService service = refConfig.get();
		Object o = service.$invoke("showHello", new String[]{"java.lang.Integer"},new Object[]{12});
		
		Object os = service.$invoke("dogs", new String[]{"java.lang.Integer"},new Object[]{6});
		
		Object o2 = service.$invoke("sayHello", new String[]{"int"},new Object[]{12});
		
		Dog dog = new Dog();
		dog.setId(12);
		dog.setName("DOG007");
		Object o3 = service.$invoke("showDog", new String[]{"com.dev.ym.po.Dog"},new Object[]{dog});
		
		System.out.println(o);
		System.out.println(os);
		System.out.println(o2);
		System.out.println(o3);
		
		
		
	}
}
