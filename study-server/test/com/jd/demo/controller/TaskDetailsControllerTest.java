package com.jd.demo.controller;

import com.alibaba.fastjson.JSON;
import com.jd.demo.common.lang.Result;
import com.jd.demo.entity.TaskDetails;
import com.jd.demo.service.TaskDetailsService;
import com.jd.demo.service.TaskService;
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
import org.springframework.stereotype.Component;
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

import java.io.UnsupportedEncodingException;
import java.time.LocalDate;


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
public class TaskDetailsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    TaskDetailsService taskDetailsService;
    @Before()
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    /**
     * 测试获取任务明细列表接口
     *
     * @throws Exception
     */
    @Test
    public void testList() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/task-details/list");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据人员id获取指定任务明细接口
     *
     * @throws Exception
     */
    @Test
    public void testInfoParticipantId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/task-details/info/participantId/1");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据任务id获取指定任务明细接口
     *
     * @throws Exception
     */
    @Test
    public void testInfoTaskId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/task-details/info/taskId/1");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试根据任务id和人员id获取指定任务明细接口
     *
     * @throws Exception
     */
    @Test
    public void testInfoTaskIdAndParticipantId() throws Exception {
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .get("/task-details/info/taskIdAndParticipantId/1/1");

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试完成任务接口
     *
     * @throws Exception
     */
    @Test
    public void testSave() throws Exception {
        String body = "{\"id\":5,\"taskId\":1,\"participantId\":6,\"taskFinishTime\":\"2023-05-15\"}";
        MockHttpServletRequestBuilder postRequestBuilder = MockMvcRequestBuilders
                .post("/task-details/save")
                .contentType(MediaType.APPLICATION_JSON).content(body);

        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }

    /**
     * 测试并发完成任务接口
     *
     * @throws Exception
     */
    @Test
    public void testConcurrentSave() throws Exception {

        TaskDetails taskDetails_1 = new TaskDetails(3L,1L,2L,LocalDate.now());
        TaskDetails taskDetails_2 = new TaskDetails(4L,2L,2L,LocalDate.now());
        TaskDetails taskDetails_3 = new TaskDetails(5L,3L,2L,LocalDate.now());
        TaskDetails taskDetails_4 = new TaskDetails(6L,4L,2L,LocalDate.now());
        TaskDetails taskDetails_5 = new TaskDetails(6L,5L,2L,LocalDate.now());
        TaskDetails taskDetails_6 = new TaskDetails(8L,6L,2L,LocalDate.now());

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                Result result_1 = taskDetailsService.saveTaskDetails(taskDetails_1);
                System.out.println(Thread.currentThread().getName()+"创建结果为："+result_1.getMsg());
            }
        },"线程1");

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Result result_1 = taskDetailsService.saveTaskDetails(taskDetails_1);
                System.out.println(Thread.currentThread().getName()+"创建结果为："+result_1.getMsg());
            }
        },"线程2");

        thread1.start();
        thread2.start();
//        new Thread(()->{
//            Result result_2 = taskDetailsService.saveTaskDetails(taskDetails_2);
//            System.out.println(Thread.currentThread().getName()+"创建结果为："+result_2.getMsg());
//        },"线程2").start();
//        new Thread(()->{
//            Result result_3 = taskDetailsService.saveTaskDetails(taskDetails_3);
//            System.out.println(Thread.currentThread().getName()+"创建结果为："+result_3.getMsg());
//        },"线程3").start();
//        new Thread(()->{
//            Result result_4 = taskDetailsService.saveTaskDetails(taskDetails_4);
//            System.out.println(Thread.currentThread().getName()+"创建结果为："+result_4.getMsg());
//        },"线程4").start();
//        new Thread(()->{
//            Result result_5 = taskDetailsService.saveTaskDetails(taskDetails_5);
//            System.out.println(Thread.currentThread().getName()+"创建结果为："+result_5.getMsg());
//        },"线程5").start();
    }


}