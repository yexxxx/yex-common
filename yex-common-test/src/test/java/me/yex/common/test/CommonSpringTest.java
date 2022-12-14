package me.yex.common.test;

import me.yex.common.test.feign.payment.PaymentService;
import co.elastic.clients.elasticsearch.ElasticsearchClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author yexy
 * @description
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = CommonTestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommonSpringTest {

    @Autowired
    private Environment env;

    @Autowired
    private ApplicationContext context;

    @Autowired
    private ElasticsearchClient esClient;

    @Autowired
    private PaymentService paymentService;


    @Test
    public void contextLoads() {
        System.out.println(env.toString());
    }

//    @Test
//    public void testEsClient() throws IOException {
//        HealthResponse healthResponse = esClient.cat().health();
//        SearchResponse<Object> results = esClient
//                .search(_0 -> _0.query(_1 -> _1),
//                        Object.class
//                );
//        System.out.println();
//    }
//
//    @Test
//    public void testFeign() {
//        Object znFeignConfiguration = context.getBean("ZnFeignConfiguration");
//        System.out.println(paymentService.demoHello("test"));
//    }

}