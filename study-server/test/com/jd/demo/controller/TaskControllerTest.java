package com.jd.demo.controller;

import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.Task;
import com.jd.demo.service.TaskService;
import org.apache.coyote.Response;
import org.databene.contiperf.PerfTest;
import org.databene.contiperf.junit.ContiPerfRule;
import org.junit.Before;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
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
 * task表单元测试
 *
 * @author lufei
 * @date 2023/5/18 14:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@AutoConfigureMockMvc
public class TaskControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Before()
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     * 测试获取任务列表接口
     *
     * @throws Exception
     */
    @Test
    public void testList() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/task/list");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据id获取指定任务接口
     *
     * @throws Exception
     */
    @Test
    public void testInfoId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/task/info/id/1");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据任务id获取指定任务接口
     *
     * @throws Exception
     */
    @Test
    public void testInfoTaskId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/task/info/taskId/1");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据任务名称获取指定任务接口
     *
     * @throws Exception
     */
    @Test
    public void testInfoTaskName() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/task/info/taskName/����1");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试创建任务接口
     *
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {

        String body = "{\"id\":2,\"taskId\":2,\"taskName\":\"测试3\",\"taskEnableStatus\":1}";
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .post("/task/save")
                .contentType(MediaType.APPLICATION_JSON).content(body);

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试更新任务接口
     *
     * @throws Exception
     */
    @Test
    public void testUpdate() throws Exception {
        String body = "{\"id\":2,\"taskId\":6,\"taskName\":\"测试6\",\"taskEnableStatus\":1}";
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .post("/task/update")
                .contentType(MediaType.APPLICATION_JSON).content(body);


        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据id删除指定任务接口
     *
     * @throws Exception
     */
    @Test
    public void testDeleteId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/task/delete/id/1");


        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据任务id删除指定任务接口
     *
     * @throws Exception
     */
    @Test
    public void testDeleteTaskId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/task/delete/taskId/3");


        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据任务名称删除指定任务接口
     *
     * @throws Exception
     */
    @Test
    public void testDeleteTaskName() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/task/delete/taskName/测试3");


        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }



}