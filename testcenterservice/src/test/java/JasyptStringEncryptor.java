import com.zoctan.api.Application;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * jasypt 用于加密配置文件 https://github.com/ulisesbocchio/jasypt-spring-boot
 *
 * @author Zoctan
 * @date 2018/05/27
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class JasyptStringEncryptor {

  @Resource(name = "myStringEncryptor")
  private StringEncryptor stringEncryptor;

  @Test
  public void encode() {
    final String name = this.stringEncryptor.encrypt("AGqbroDL");
    System.out.println(this.stringEncryptor.decrypt("VuLn6kkmZXt1402C9w9xUA=="));
    final String password = this.stringEncryptor.encrypt("0NjH#FJv=sOjDy!B");

    System.err.println("name = " + name);
    System.err.println("password = " + password);
  }
}
