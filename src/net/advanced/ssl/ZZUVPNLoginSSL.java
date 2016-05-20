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
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
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

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ZZUVPNLoginSSL {

    static {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");

        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");

        System.setProperty("org.apache.commons.logging.simplelog.log.httpclient.wire", "debug");

        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "debug");
    }

    public static CloseableHttpClient httpclient;

    public static String vpnID = "abcd";
    public static String vpnPW = "1234";

    public static RequestConfig localConfig;


    public static void requestVPNPost(String url) throws IOException {
        HttpPost httpPost = new HttpPost(url);

        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        nvps.add(new BasicNameValuePair("username", vpnID));
        nvps.add(new BasicNameValuePair("password", "1234"));
        nvps.add(new BasicNameValuePair("realm", "郑州大学用户REALM"));
        nvps.add(new BasicNameValuePair("tz_offset", "480"));
        nvps.add(new BasicNameValuePair("btnSubmit", "登录"));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

        System.out.println("======================================");
        System.out.println(httpPost.getRequestLine());
        System.out.println(Arrays.toString(httpPost.getAllHeaders()));

        String postBody = EntityUtils.toString(httpPost.getEntity(), "UTF-8");
        System.out.println(postBody);
        System.out.println("======================================");
        CloseableHttpResponse response = httpclient.execute(httpPost);
        try {
            System.out.println("======================================");
            System.out.println(response.getStatusLine());
            System.out.println(Arrays.toString(response.getAllHeaders()));
            System.out.println("======================================");

            HttpEntity entity2 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed
            String htmlPage = EntityUtils.toString(entity2, "GBK");
            //System.out.println(htmlPage);
            EntityUtils.consume(entity2);
        } finally {
            response.close();
        }
    }


    public static void requestVPNGet(String url, boolean showHtml, String encodeStr) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(localConfig);

        System.out.println("======================================");
        System.out.println(httpGet.getRequestLine());
        System.out.println(Arrays.toString(httpGet.getAllHeaders()));
        ;
        System.out.println("======================================");

        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {


            System.out.println("======================================");
            System.out.println(response.getStatusLine());
            System.out.println(Arrays.toString(response.getAllHeaders()));
            System.out.println("======================================");

            HttpEntity entity2 = response.getEntity();
            // do something useful with the response body
            // and ensure it is fully consumed

            if (showHtml) {
                String htmlPage = EntityUtils.toString(entity2, encodeStr);
                System.out.println(htmlPage);
            }

            EntityUtils.consume(entity2);
        } finally {
            response.close();
        }
    }

    public static void main(String[] args) throws Exception {
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
                SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

        RequestConfig globalConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.NETSCAPE)
                .build();

        httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .setDefaultRequestConfig(globalConfig)
                .build();

        RequestConfig localConfig = RequestConfig.copy(globalConfig)
                .setCookieSpec(CookieSpecs.DEFAULT)
                .build();

        try {
            requestVPNGet("https://vpn.zzu.edu.cn", false, "");
            requestVPNPost("https://vpn.zzu.edu.cn/dana-na/auth/url_default/login.cgi");
            requestVPNGet("https://vpn.zzu.edu.cn/dana/home/index.cgi", true, "GBK");

            requestVPNGet("https://vpn.zzu.edu.cn/,DanaInfo=www.zzu.edu.cn,SSO=U+", true, "UTF-8");

            requestVPNGet("https://vpn.zzu.edu.cn/msgs/vmsgisapi.dll/,DanaInfo=www16.zzu.edu.cn+vmsglist?mtype=m&lan=101,102,103", true, "UTF-8");


            requestVPNGet("https://vpn.zzu.edu.cn/dana-na/auth/logout.cgi", false, "");

        } finally {
            httpclient.close();
        }


    }


}
