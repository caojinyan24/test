/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package kafka.kafka_stream;

//import org.apache.kafka.clients.consumer.ConsumerConfig;
//import org.apache.kafka.common.serialization.Serdes;
//import org.apache.kafka.streams.KafkaStreams;
//import org.apache.kafka.streams.StreamsConfig;
//import org.apache.kafka.streams.kstream.ForeachAction;
//import org.apache.kafka.streams.kstream.KStream;
//
//import java.io.FileNotFoundException;
//import java.io.PrintWriter;
//import java.util.Properties;
//
///**
// * Demonstrates, using the high-level KStream DSL, how to implement the WordCount program
// * that computes a simple word occurrence histogram from an input text.
// * <p>
// * In this example, the input stream reads from a topic named "streams-file-input", where the values of messages
// * represent lines of text; and the histogram output is written to topic "streams-wordcount-output" where each record
// * is an updated count of a single word.
// * <p>
// * Before running this example you must create the input topic and the output topic (e.g. via
// * bin/kafka-topics.sh --create ...), and write some data to the input topic (e.g. via
// * bin/kafka-console-producer.sh). Otherwise you won't see any data arriving in the output topic.
// */
//public class WordCountDemo {
//
//    public static void main(String[] args) throws Exception {
//
//        Properties props = new Properties();
//        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "streams-wordcount1");//一个stream应用指定一个topic
//        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//        props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
//
//        // setting offset reset to earliest so that we can re-run the demo code with the same pre-loaded data
//        // Note: To re-run the demo, you need to use the offset reset tool:
//        // https://cwiki.apache.org/confluence/display/KAFKA/Kafka+Streams+Application+Reset+Tool
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
//
//        KStreamBuilder builder = new KStreamBuilder();
//
//        KStream<String, String> source = builder.stream("topic1");//input topic
//        processMessage(source);
//
//        KafkaStreams streams = new KafkaStreams(builder, props);
//        streams.start();
//    }
//
//    private static void processMessage(KStream<String, String> source) throws FileNotFoundException {
//        final PrintWriter printWriter = new PrintWriter(WordCountDemo.class.getResource("").getPath() + "outputfile");
//        source.foreach(new ForeachAction<String, String>() {
//            @Override
//            public void apply(String key, String value) {
//                printWriter.append("|").append(key).append("|").append("   ").append("|").append(value).append("|").append("\n");
//                printWriter.flush();
//            }
//        });
//        source.print();
//    }
//}
