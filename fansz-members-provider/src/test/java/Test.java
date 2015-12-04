

import com.fansz.members.tools.JsonHelper;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.github.miemiedev.mybatis.paginator.domain.Paginator;
import org.codehaus.jackson.map.ObjectMapper;

/**
 * Created by allan on 15/12/3.
 */
public class Test {

    public static void main(String[] args) throws Exception{
        Paginator p=new Paginator(5,5,1);
        PageList<String> a=new PageList<String>(p);
        a.add("1");
        a.add("2");
        a.add("3");
        ObjectMapper b=new ObjectMapper();
        System.out.println(b.writeValueAsString(a));
    }
}
