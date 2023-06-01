package com.jd.demo.controller;

import com.jd.demo.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


/**
 * taskDetails表单元测试
 *
 * @author lufei
 * @date 2023/5/18 14:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
// @AutoConfigureMockMvc是用于自动配置MockMvc
@AutoConfigureMockMvc
public class PrizeDistributionDetailsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;
    @Before()
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }



    /**
     * 测试获取奖品分发明细列表接口
     *
     * @throws Exception
     */
    @Test
    public void testList() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/prize-distribution-details/list");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据人员id获取指定奖品分发明细接口
     *
     * @throws Exception
     */
    @Test
    public void testInfoParticipantId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/prize-distribution-details/info/participantId/1");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据任务id获取指定奖品分发明细接口
     *
     * @throws Exception
     */
    @Test
    public void testInfoTaskId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/prize-distribution-details/info/taskId/1");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据奖品id获取指定奖品分发明细接口
     *
     * @throws Exception
     */
    @Test
    public void testInfoPrizeId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/prize-distribution-details/info/prizeId/1");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试完成奖品分发接口
     *
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        String body = "{\"id\":4,\"taskId\":2,\"participantId\":2,\"prizeId\":2,\"prizeDistributionTime\":\"2023-05-15\"}";
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .post("/prize-distribution-details/save")
                .contentType(MediaType.APPLICATION_JSON).content(body);

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }
}