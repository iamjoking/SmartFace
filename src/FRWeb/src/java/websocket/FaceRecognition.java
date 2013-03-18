/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package websocket;
/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-1.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Iterator;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.metadata.IIOMetadata;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.websocket.MessageInbound;
import org.apache.catalina.websocket.StreamInbound;
import org.apache.catalina.websocket.WebSocketServlet;

import sun.misc.BASE64Encoder;
import sun.misc.BASE64Decoder;


public class FaceRecognition extends WebSocketServlet {

    private static final long serialVersionUID = 1L;
    private volatile int byteBufSize;
    private volatile int charBufSize;

    @Override
    public void init() throws ServletException {
        super.init();
        byteBufSize = getInitParameterIntValue("byteBufferMaxSize", 20971520);
        charBufSize = getInitParameterIntValue("charBufferMaxSize", 20971520);
    }

    public int getInitParameterIntValue(String name, int defaultValue) {
        String val = this.getInitParameter(name);
        int result;
        if(null != val) {
            try {
                result = Integer.parseInt(val);
            }catch (Exception x) {
                result = defaultValue;
            }
        } else {
            result = defaultValue;
        }

        return result;
    }



    @Override
    protected StreamInbound createWebSocketInbound(String subProtocol,
            HttpServletRequest request) {
        return new EchoMessageInbound(byteBufSize,charBufSize);
    }

    private static final class EchoMessageInbound extends MessageInbound {

        public EchoMessageInbound(int byteBufferMaxSize, int charBufferMaxSize) {
            super();
            setByteBufferMaxSize(byteBufferMaxSize);
            setCharBufferMaxSize(charBufferMaxSize);
        }

        @Override
        protected void onBinaryMessage(ByteBuffer message) throws IOException {
            getWsOutbound().writeBinaryMessage(message);
        }

        @Override
        protected void onTextMessage(CharBuffer message) throws IOException {
         //   System.out.println(message);
            BASE64Decoder decoder = new BASE64Decoder();
            StringBuilder baseStringBuffer = new StringBuilder(message);
            baseStringBuffer.delete(0,baseStringBuffer.indexOf(",")+1);
          //  System.err.println(baseStringBuffer);
            ByteBuffer buffer = decoder.decodeBufferToByteBuffer(baseStringBuffer.toString());
            
            java.awt.image.BufferedImage img = javax.imageio.ImageIO.read(new java.io.ByteArrayInputStream(buffer.array()));
            
            String resultString ;
            if(img == null){
                resultString = "Format Not Supported.";
            }else{
                face.FaceRecognitionTask task;
                task = new face.FaceRecognitionTask(img);
                resultString = task.doRecognition();
            }
            
            CharBuffer resultMessage = CharBuffer.wrap(resultString.toCharArray());
            //System.err.println(resultMessage);
            getWsOutbound().writeTextMessage(resultMessage);
            //javax.imageio.ImageIO.write(img, "png", new File("/tmp/1.png"));
            //getWsOutbound().writeTextMessage(message);
        }
    }
}