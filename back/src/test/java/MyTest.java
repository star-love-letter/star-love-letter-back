import cn.conststar.wall.exception.ExceptionMain;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Arrays;

public class MyTest {
    @Test
    public void Test(){
        System.out.println(Arrays.toString(ImageIO.getReaderFormatNames()));
    }
}
