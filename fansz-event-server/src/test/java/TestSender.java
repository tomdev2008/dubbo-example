import com.alibaba.fastjson.serializer.SerializerFeature;
import com.fansz.event.model.PublishPostEvent;
import com.fansz.event.sender.HttpSender;
import com.fansz.pub.constant.InformationSource;
import com.fansz.pub.utils.JsonHelper;

/**
 * Created by allan on 15/11/30.
 */
public class TestSender {
    public static void main(String[] args) throws Exception {
        PublishPostEvent event=new PublishPostEvent();
        event.setSource(InformationSource.NEWSFEEDS);
        String a=JsonHelper.convert2FormatJSONString(event, SerializerFeature.WriteMapNullValue);
        System.out.println(a);
        event=JsonHelper.convertJSONString2Object(a,PublishPostEvent.class);
        System.out.println(event.getSource());
    }
}

