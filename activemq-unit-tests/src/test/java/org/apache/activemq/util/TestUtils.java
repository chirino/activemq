/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.activemq.util;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.net.ServerSocketFactory;

public class TestUtils {

    public static final int DEFAULT_PORT = 61616;

    public static int findOpenPort() {
        return findOpenPorts(1).get(0);
    }

    public static List<Integer> findOpenPorts(int count) {
        if (count <= 0) {
            return Collections.emptyList();
        }

        List<ServerSocket> sockets = new ArrayList<ServerSocket>(count);
        List<Integer> ports = new ArrayList<Integer>(count);
        List<Integer> safeSet = new ArrayList<Integer>(count);

        // Pre-fill with a sane default set.
        for (int i = 0; i < count; ++i) {
            safeSet.add(DEFAULT_PORT + i);
        }

        try {
            for (int i = 0; i < count; ++i) {
                ServerSocket socket = ServerSocketFactory.getDefault().createServerSocket(0);

                sockets.add(socket);
                ports.add(socket.getLocalPort());
            }
        } catch (IOException e) {
            return safeSet;
        } finally {
            for (ServerSocket socket : sockets) {
                try {
                    socket.close();
                } catch (IOException e) {}
            }
        }

        return ports;
    }
}
