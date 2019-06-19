package com.htkj.check;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/admin")
public class AdminCtrl {

    @RequestMapping("/index")
    public String index() {
        return "/index";
    }

    @RequestMapping("/check")
    public String check() {
        return "/check";
    }



    @RequestMapping("/startCheck")
    @ResponseBody
    public String startCheck(){        
        return "";
//        Properties props = new Properties();
//        props.put("bootstrap.servers", "192.168.20.214:9092");//Kafka集群
//        props.put("group.id", "test");
//        props.put("enable.auto.commit", "true");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
//        consumer.subscribe(Arrays.asList("qweasd"));
//        while (true) {
//            ConsumerRecords<String, String> records = consumer.poll(100);
//            for (ConsumerRecord<String, String> record : records)
//                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
//        }
    }
}
