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
import org.json.JSONObject;
import sun.nio.cs.ext.GBK;

public class FluentQuickStart {

    public static void main(String[] args) throws Exception {
        // The fluent API relieves the user from having to deal with manual
        // deallocation of system resources at the cost of having to buffer
        // response content in memory in some cases.

        String ret = Request.Get("http://localhost:8080/FactNWeb/FactN?n=1000&json=true")
                .execute().returnContent().asString();

        JSONObject js = new JSONObject(ret);

        String result = js.getString("FactNByBigInteger");

        System.out.println();

        String str =
                Request.Post("http://jw.zzu.edu.cn/scripts/teainfo.dll/login")
                        .bodyForm(Form.form().add("bianhao", "240187").add("mima", "931116liuyang").build())
                        .execute().returnContent().asString(new GBK());

        //str = new String(str.getBytes(),"gb2312");

        //System.out.println(str);
    }
}
