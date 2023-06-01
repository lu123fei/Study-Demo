package com.jd.demo.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
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
 * prize表单元测试
 *
 * @author lufei
 * @date 2023/5/18 14:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
// @AutoConfigureMockMvc是用于自动配置MockMvc
@AutoConfigureMockMvc
public class PrizeControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Before()
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     * 测试获取奖品列表接口
     *
     * @throws Exception
     */
    @Test
    public void testList() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/prize/list");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据id查询指定奖品接口
     *
     * @throws Exception
     */
    @Test
    public void testInfoId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/prize/info/id/1");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据奖品id查询指定奖品接口
     *
     * @throws Exception
     */
    @Test
    public void testInfoPrizeId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/prize/info/prizeId/1");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据奖品名称查询指定奖品接口
     *
     * @throws Exception
     */
    @Test
    public void testInfoPrizeName() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/prize/info/prizeName/奖品1");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试奖品创建接口
     *
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        String body = "{\"id\":4,\"prizeId\":5,\"prizeName\":\"测试4\"}";
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .post("/prize/save")
                .contentType(MediaType.APPLICATION_JSON).content(body);


        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试更新奖品接口
     *
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception {
        String body = "{\"id\":4,\"prizeId\":5,\"prizeName\":\"测试5\"}";
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .post("/prize/update")
                .contentType(MediaType.APPLICATION_JSON).content(body);


        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据id删除指定奖品接口
     *
     * @throws Exception
     */
    @Test
    public void testDeleteId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/prize/delete/id/1");


        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据奖品id删除指定奖品接口
     *
     * @throws Exception
     */
    @Test
    public void testDeletePrizeId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/prize/delete/prizeId/3");


        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据奖品名称删除指定奖品接口
     *
     * @throws Exception
     */
    @Test
    public void testDeletePrizeName() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/prize/delete/prizeName/奖品1");


        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

}