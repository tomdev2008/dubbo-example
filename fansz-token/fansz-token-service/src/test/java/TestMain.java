import com.aliyun.oss.OSSClient;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.fansz.common.provider.constant.ErrorCode;
import com.fansz.common.provider.exception.ApplicationException;
import com.fansz.pub.utils.FileTools;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by allan on 16/2/18.
 */
public class TestMain {
    public static void main(String[] args) throws Exception {
        String roleSessionName = "external-username";
        // 创建一个 Aliyun Acs Client, 用于发起 OpenAPI 请求
        String accessKeyId = "FMPXgkdg2IuDhavQ";
        String accessKeySecret = "z3ijCoPOD0qy9rybjeV7v5AVd333Av";
        String roleArn = "acs:ram::1966541786492265:role/aliyunosstokengeneratorrole";
        long durationSeconds = 3600;
        // 目前只有"cn-hangzhou"这个region可用, 不要使用填写其他region的值
        String region = "cn-hangzhou";
        IClientProfile profile = DefaultProfile.getProfile(region, accessKeyId, accessKeySecret);
        DefaultAcsClient client = new DefaultAcsClient(profile);

        // 创建一个 AssumeRoleRequest 并设置请求参数
        final AssumeRoleRequest request = new AssumeRoleRequest();
        request.setVersion("2015-04-01");
        request.setMethod(MethodType.POST);
        request.setProtocol(ProtocolType.HTTPS);//// 此处必须为 HTTPS

        request.setRoleArn(roleArn);
        request.setRoleSessionName(roleSessionName);
        String policy = FileTools.readStringFromFile("/Users/allan/Works/backend/fansz-backend/fansz-token/fansz-token-service/src/main/resources/policy/bucket_read_write_policy.txt");
        request.setPolicy(policy);
        request.setDurationSeconds(durationSeconds);

        // 发起请求，并得到response
        final AssumeRoleResponse response = client.getAcsResponse(request);

        Map<String, String> respMap = new LinkedHashMap<>();
        respMap.put("access_key_id", response.getCredentials().getAccessKeyId());
        respMap.put("access_key_secret", response.getCredentials().getAccessKeySecret());
        respMap.put("security_token", response.getCredentials().getSecurityToken());
        respMap.put("expiration", response.getCredentials().getExpiration());
        System.out.println(respMap);

        String accessKeyId2 = response.getCredentials().getAccessKeyId();
        String accessKeySecret2 = response.getCredentials().getAccessKeySecret();
        String securityToken2 = response.getCredentials().getSecurityToken();
        // 以杭州为例
        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";

        OSSClient c = new OSSClient(endpoint, accessKeyId2, accessKeySecret2, securityToken2);

        c.putObject("fd-images", "test", new File("/Users/allan/Downloads/1.jpg"));
    }
}
