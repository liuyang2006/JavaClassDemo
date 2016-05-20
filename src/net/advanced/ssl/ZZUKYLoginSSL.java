/*
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */
package net.advanced.ssl;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import sun.nio.cs.ext.GBK;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZZUKYLoginSSL {

    public static CloseableHttpClient httpclient;

    public static String vpnID;
    public static String vpnPW;

    public static void step1() throws IOException {
        HttpGet httpget = new HttpGet("http://login.zzu.edu.cn/");

        System.out.println("Executing request " + httpget.getRequestLine());

        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            HttpEntity entity = response.getEntity();


            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());
            System.out.println(EntityUtils.toString(entity, new GBK()));
            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
    }

    public static void step2ForGetVCJPG() throws IOException {
        HttpGet httpget = new HttpGet("http://ky.zzu.edu.cn/keyans/zzjlogin3d.dll/zzjgetimg?ids=2344");

        System.out.println("Executing request " + httpget.getRequestLine());

        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            HttpEntity entity = response.getEntity();
            System.out.println("----------------------------------------");
            System.out.println(response.getStatusLine());

            InputStream is = entity.getContent();
            File file = new File("zzuvpnvcode.jpg");
            FileOutputStream fileout = new FileOutputStream(file);
            /**
             * 根据实际运行效果 设置缓冲区大小
             */
            byte[] buffer = new byte[128];
            int ch = 0;
            while ((ch = is.read(buffer)) != -1) {
                fileout.write(buffer, 0, ch);
            }
            is.close();
            fileout.flush();
            fileout.close();


            EntityUtils.consume(entity);
        } finally {
            response.close();
        }
    }

    public static boolean step3ForSSLPostLoginForm(String str) throws IOException {
        boolean flag;
        HttpPost httpPost = new HttpPost("https://ky.zzu.edu.cn/keyanss/zzjlogin8.dll/login");
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("uid", "1300236"));
        nvps.add(new BasicNameValuePair("pw", "99094018"));
        nvps.add(new BasicNameValuePair("verc", str));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed

            //System.out.println(EntityUtils.toString(entity2, new GBK()));

            //match string 用户名：xoqe；密码：1234

            String htmlPage = EntityUtils.toString(entity2, new GBK());
            System.out.println(htmlPage);

            Pattern pattern = Pattern.compile("用户名：(.*)；密码：(\\d{4})");
            Matcher matcher = pattern.matcher(htmlPage);
            if (matcher.find()) {
                vpnID = matcher.group(1);
                vpnPW = matcher.group(2);
                System.out.printf("VPN id = %s\n", vpnID);
                System.out.printf("VPN pw = %s\n", vpnPW);
                flag = true;
            } else {
                System.out.println("Verified code input error.");
                flag = false;
            }
            EntityUtils.consume(entity2);
        } finally {
            response2.close();
        }
        return flag;
    }


    public static void prepareSSLConfig() throws CertificateException, NoSuchAlgorithmException, KeyStoreException, IOException, KeyManagementException {
        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadTrustMaterial(new File("myzzuky.keystore"), "99094018".toCharArray(),
                        new TrustSelfSignedStrategy())
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.getDefaultHostnameVerifier());
        httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
    }

    public static void main(String[] args) throws Exception {

        prepareSSLConfig();
        try {
            step1(); //access http://login.zzu.edu.cn/

            do {
                step2ForGetVCJPG(); //access for verify code at http://ky.zzu.edu.cn/keyans/zzjlogin3d.dll/zzjgetimg?ids=2344
                Runtime.getRuntime().exec("open zzuvpnvcode.jpg"); //open verify code
                Scanner scanner = new Scanner(System.in);
                System.out.printf("Input the verified code:");
                String str = scanner.nextLine();

                if (step3ForSSLPostLoginForm(str))
                    break; //post uid,pw,verified code to https://ky.zzu.edu.cn/keyanss/zzjlogin8.dll/login for VPN id and pw
            } while (true);
        } finally {
            httpclient.close();
        }


    }

}
