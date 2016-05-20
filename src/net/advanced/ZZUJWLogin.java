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
package net.advanced;

import org.apache.http.client.fluent.Form;
import org.apache.http.client.fluent.Request;
import sun.nio.cs.ext.GBK;

public class ZZUJWLogin {

    public static void main(String[] args) throws Exception {

        // 登陆教师信息页面
        String str =
                Request.Post("http://jw.zzu.edu.cn/scripts/teainfo.dll/login")
                        .bodyForm(Form.form().add("bianhao", "240187").add("mima", "931116liuyang").build())
                        .execute().returnContent().asString(new GBK());

        System.out.println(str);

        //登陆教评系统页面
        String str1 =
                Request.Post("http://jw.zzu.edu.cn/scripts/jpxx/jpxxcgi.exe/check")
                        .bodyForm(Form.form().add("zhanghao", "240187").add("mima", "931116liuyang").add("shenfen", "tea").build())
                        .execute().returnContent().asString(new GBK());

        System.out.println(str1);
    }
}
