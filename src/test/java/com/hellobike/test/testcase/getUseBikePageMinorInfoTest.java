package com.hellobike.test.testcase;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

import static com.hellobike.test.common.FileOperation.fileRead;
import static com.hellobike.test.common.GetXMLParse.getParameterSutie;
import static com.hellobike.test.support.SaveAsFile.saveFile;

public class getUseBikePageMinorInfoTest {

    /**
     * getUseBikePageMinorInfo接口请求数据提供
     * @throws Exception
     */
    @DataProvider(name = "getUseBikePageMinorInfo_dataProvider")
    public Object[] getUseBikePageMinorInfo_dataProvider() {
        //根据请求参数所在路径获取对应的请求数据
        String filePath = fileRead(
                "src/test/java/com/hellobike/test/request/getUseBikePageMinorInfo.json");
        //获取到的请求参数存在JSONObject中，可以给某些变量参数赋值处理
        JSONObject bodyJson = JSONObject.parseObject(filePath);
        try {
            bodyJson.put("token", getParameterSutie("token"));
            bodyJson.put("apiVersion", getParameterSutie("apiVersion"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("请求body:" + bodyJson.toString());
        return new Object[] {bodyJson};
    }

    /**
     * contractBikeSkuTest
     * @param bodyJson
     * @throws Exception
     */
    @Test(dataProvider = "getUseBikePageMinorInfo_dataProvider")
    public void getUseBikePageMinorInfoTest(JSONObject bodyJson) throws Exception {
        CloseableHttpClient httpClients = HttpClientBuilder.create().build();
        //获取接口请求的url
        String url = getParameterSutie("baseUrl");
        //创建http post请求
        HttpPost httpPost = new HttpPost(url);
        //设置请求头信息
        httpPost.setHeader("Content-Type", "application/json");
        httpPost.setHeader("token", getParameterSutie("token"));
        //设置请求body
        httpPost.setEntity(new StringEntity(bodyJson.toString()));
        //定义接口返回
        HttpResponse httpResponse = null;
        try {
            //获取接口返回body
            httpResponse = httpClients.execute(httpPost);
            HttpEntity httpResponseEntity = httpResponse.getEntity();
            System.out.println("响应状态为:" + httpResponse.getStatusLine());
            String responseStr = "";
            if (httpResponseEntity != null) {
                responseStr = EntityUtils.toString(httpResponseEntity);
                System.out.println("响应内容为:" + responseStr);
            }
            String filePath = "src/test/java/com/hellobike/test/response/getUseBikePageMinorInfo_tt.json";
            saveFile(responseStr, filePath);
            JSONObject responseJson = JSONObject.parseObject(responseStr);
            //断言，判断返回值code为0
            Assert.assertEquals(Integer.parseInt(responseJson.getString("code")), 0);


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            httpClients.close();
        }

    }
}
