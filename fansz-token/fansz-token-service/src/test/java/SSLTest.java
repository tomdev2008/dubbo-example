/**
 * Created by allan on 16/1/29.
 */

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.protocol.Protocol;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;

public class SSLTest {
    public static void main(String[] args) throws Exception {
        test();
    }

    public static void convert() throws Exception {
        KeyStore ks = KeyStore.getInstance("jks");
        char[] password = "fanszapp".toCharArray();
        ks.load(new FileInputStream("/Users/allan/fansz1.jks"), password);

        FileOutputStream kos = new FileOutputStream("/Users/allan/key.der");
        Key pvt = ks.getKey("fansz", password);
        kos.write(pvt.getEncoded());
        kos.flush();
        kos.close();
        FileOutputStream cos = new FileOutputStream("/Users/allan/cert.der");
        Certificate pub = ks.getCertificate("fansz");
        cos.write(pub.getEncoded());
        cos.flush();
        cos.close();
    }

    public static void test() throws Exception {
        AuthSSLProtocolSocketFactory factory = new AuthSSLProtocolSocketFactory(null, null, new File("/Users/allan/fansztrusted.jks").toURI().toURL(), "fanszapp");
        Protocol myhttps = new Protocol("https", factory, 443);

        Protocol.registerProtocol("https",
                new Protocol("https", factory, 443));
        HttpClient httpclient = new HttpClient();
        PostMethod httpget = new PostMethod("https://121.43.188.186");
        String queryString = "{\"header\": {\"local\": \"ZH_cn\",\"terminal_type\": \"IOS\"},\"request\": [{\"method\": \"getVerifyCodeForReset\",\"params\": {\"mobile\":\"13764473493\"}}]}";
        StringRequestEntity requestEntity = new StringRequestEntity(queryString, "application/json", "UTF-8");
        httpget.setRequestEntity(requestEntity);
        httpget.setRequestHeader(new Header("Content-Type", "application/json"));
        try {
            httpclient.executeMethod(httpget);
            System.out.println(httpget.getStatusLine());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            httpget.releaseConnection();
        }
    }
}
