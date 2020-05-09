import com.computerGo.App;
import com.computerGo.base.redis.RedisUtil;
import com.computerGo.controller.URController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * @ClassName computerGo
 * @Description TODO
 * @Author QQ163
 * @Date 2020/4/17 7:55
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {App.class},webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class computerGo {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void yml(){
        String a = "http://39.96.68.53:70/computerGo/auto.jpg";
        String path = a.replaceAll("http://39.96.68.53:70","/photo");
        System.out.println(path.substring(path.lastIndexOf("/")+1));
        System.out.println(path.substring(0, path.lastIndexOf("/")));
    }

    @Autowired
    private URController urController;
    @Test
    public void get(){
    //    redisUtil.set("aaaaa1","CG");
        System.out.println(redisUtil.get("computerGO1"));
    }
}
