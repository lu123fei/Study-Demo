# Study-Demo
Demo设计
demo设计：一、完成一个任务系统开发
二、要求：   1、完成任务的创建、任务详情查询、任务更新、任务删除、任务列表
   2、完成奖品创建、奖品详情查询、奖品更新、奖品删除、奖品列表
   3、完成做任务
   4、完成奖品发放
三、表设计   1、 任务主表包含：任务id、任务名称、任务开始时间、任务结束时间、任务启用状态、任务范围、任务奖品id、任务参与人数
   2、 奖品表：奖品id、奖品名称、奖品数量
   2、 任务明细表：包含人员id、任务id、完成任务时间
   3、 奖励发放明细表：人员id、任务id、奖品id、奖品发放时间

一、数据库设计1. 任务表create table task
(
    id bigint(22) not null auto_increment COMMENT '自增id',
    task_id bigint(22)not null COMMENT '任务id',
    task_name varchar(100) not null COMMENT '任务名称',
    task_start_time date null COMMENT '任务开始时间',
    task_end_time date null COMMENT '任务结束时间',
    task_enable_status smallint(2)  not null COMMENT '任务启用状态 -1删除 0停用 1启用 2屏蔽',
    task_scope varchar(100) null COMMENT '任务范围即参与任务的人员名字',
    task_prize_id int null COMMENT '任务奖品id',
    task_participants_number int null COMMENT '任务参与人数',
    PRIMARY KEY (id),
    UNIQUE KEY (task_id)
)ENGINE=InnoDB AUTO_INCREMENT=4265 DEFAULT CHARSET=utf8 COMMENT='任务表';
2. 奖品表create table prize
(
    id bigint(22) not null auto_increment COMMENT '自增id',
    prize_id bigint(22)not null COMMENT '奖品id',
    prize_name varchar(100)not null COMMENT '奖品名称',
    PRIMARY KEY (id),
    UNIQUE KEY (prize_id)
)ENGINE=InnoDB AUTO_INCREMENT=4265 DEFAULT CHARSET=utf8 COMMENT='奖品表';
3. 任务明细表create table task_details
(
    id bigint(22) not null auto_increment COMMENT '自增id',
    participant_id bigint(22)not null COMMENT '人员id',
    task_id bigint(22)not null COMMENT '任务id',
    task_finish_time date not null COMMENT '任务完成时间',
    PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=4265 DEFAULT CHARSET=utf8 COMMENT='任务明细表';
4. 奖品分发明细表create table prize_distribution_details
(
    id bigint(22) not null auto_increment COMMENT '自增id',
    participant_id bigint(22)not null COMMENT '人员id',
    task_id bigint(22)not null COMMENT '任务id',
    prize_id bigint(22)not null COMMENT '奖品id',
    prize_distribution_time date not null COMMENT '奖品发送时间',
    PRIMARY KEY (id)
)ENGINE=InnoDB AUTO_INCREMENT=4265 DEFAULT CHARSET=utf8 COMMENT='奖品分发明细表';
二、利用mybatis-plus插件自动生成相关包1.mybatis-plus插件代码生成器public class CodeGenerator {

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/study-server/src/main/java");
        gc.setAuthor("lufei");
        gc.setOpen(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        gc.setServiceName("%sService");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://11.80.20.83:3358/b_bpop?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        // dsc.setSchemaName("public");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("zgb");
        dsc.setPassword("jxqtest$!@666NBL");
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(scanner("模块名"));
        pc.setParent("com.jd.demo");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
//         String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                System.out.println(projectPath + "/study-server/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML);
                return projectPath + "/study-server/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();



        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);

        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
//        strategy.setTablePrefix("sys_");//动态调整
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

}
2.application.yml配置server:
  port: 8080
spring:
  datasource:
    url: jdbc:mysql://11.80.20.83:3358/b_bpop?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    driver-class-name: com.mysql.cj.jdbc.Driver
    username: zgb
    password: jxqtest$!@666NBL
mybatis-plus:
  mapper-locations: classpath*:/mapper/**Mapper.xml
三、前期配置1. 全局结果返回类@Data
public class Result {
    private int code;
    private String msg;
    private Object data;
    //定义返回操作成功的结果形式（状态码：200，操作成功的相关信息，返回的数据）
    private static Result success(Object data){
        return success(200,"操作成功",data);
    }
    private static Result success(String msg){
        return success(200,msg,null);
    }
    private static Result success(int code,String msg,Object data){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
    //定义返回操作失败的结果形式（状态码：500，操作失败的相关信息，返回的数据）
    public static Result fail(String msg) {
        return fail(500, msg, null);
    }
    public static Result fail(String msg, Object data) {
        return fail(500, msg, data);
    }
    private static Result fail(Object data){
        return success(500,"操作失败",data);
    }
    public static Result fail(int code, String msg, Object data) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}

2. 全局异常处理类@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = AccessDeniedException.class)
    public Result handler(AccessDeniedException e) {
        log.info("security权限不足：----------------{}", e.getMessage());
        return Result.fail("权限不足");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Result handler(MethodArgumentNotValidException e) {
        log.info("实体校验异常：----------------{}", e.getMessage());
        BindingResult bindingResult = e.getBindingResult();
        ObjectError objectError = bindingResult.getAllErrors().stream().findFirst().get();
        return Result.fail(objectError.getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalArgumentException.class)
    public Result handler(IllegalArgumentException e) {
        log.error("Assert异常：----------------{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = RuntimeException.class)
    public Result handler(RuntimeException e) {
        log.error("运行时异常：----------------{}", e);
        return Result.fail(e.getMessage());
    }
}

3. pom.xml依赖<dependencies>

        <!--springboot相关依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <scope>runtime</scope>
            <optional>true</optional>
        </dependency>
        <!--mybatis-plus相关依赖-->

        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.1</version>
        </dependency>
        <!--mp代码生成器-->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-generator</artifactId>
            <version>3.4.1</version>
        </dependency>
        <dependency>
            <groupId>org.freemarker</groupId>
            <artifactId>freemarker</artifactId>
            <version>2.3.30</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>

        <!--lombok依赖-->
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
        <!--mysql依赖-->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
        </dependency>
        <!--jwt依赖-->
        <dependency>
            <groupId>io.jsonwebtoken</groupId>
            <artifactId>jjwt</artifactId>
            <version>0.9.1</version>
        </dependency>
        <!--springboot测试依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.databene</groupId>
            <artifactId>contiperf</artifactId>
            <version>2.3.4</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.velocity</groupId>
            <artifactId>velocity-engine-core</artifactId>
            <version>2.3</version>
        </dependency>
    </dependencies>
四、接口设计1. 任务表相关接口设计1.1 功能描述查询：根据id、任务id、任务名称查询指定任务详情；获取任务列表
插入：创建任务时，任务关键字段不可为空，且数据库中不可存在重复的id、任务id、任务名称
更新：更新任务时，任务关键字段不可为空，且数据库中不可存在重复的任务id、任务名称
删除：根据id、任务id、任务名称删除指定任务
@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    TaskService taskService;
    //根据id查询任务

    @RequestMapping("/info/id/{id}")
    public Result infoId(@PathVariable Long id){
        Task task = taskService.getById(id);
        if(task!=null){
            return Result.success("查询任务成功",task);
        }else{
            return Result.fail("查询任务失败，无此id的任务");
        }
    }
    //根据任务id查询任务
    @RequestMapping("/info/taskId/{taskId}")
    public Result infoTaskId(@PathVariable Long taskId){
        Task task = taskService.getByTaskId(taskId);
        if(task!=null){
            return Result.success("查询任务成功",task);
        }else{
            return Result.fail("查询任务失败，无此任务id的任务");
        }
    }
    //根据任务名称查询任务
    @RequestMapping("/info/taskName/{taskName}")
    public Result infoTaskName(@PathVariable String taskName){
        Task task = taskService.getByTaskName(taskName);
        if(task!=null){
            return Result.success("查询任务成功",task);
        }else{
            return Result.fail("查询任务失败，无此任务名称的任务");
        }
    }
    //获取任务列表
    @RequestMapping("/list")
    public Result List(){
        List<Task> tasks = taskService.getAllTask();
        return Result.success("查询任务列表成功",tasks);
    }
    //任务的创建
    @RequestMapping("/save")
    public Result save(@Validated @RequestBody Task task){
        if(task.getId() == null || task.getTaskId() == null
                || task.getTaskName() == null || task.getTaskEnableStatus() == null){
            return Result.fail("任务创建失败，缺少相应关键字");

        }else{
            long id = task.getId();
            long taskId = task.getTaskId();
            String taskName = task.getTaskName();
            if(taskService.getById(id)!=null){
                return Result.fail("任务创建失败，已存在此id的任务");
            }else if(taskService.getByTaskId(taskId)!=null){
                return Result.fail("任务创建失败，已存在此任务id的任务");
            }else if(taskService.getByTaskName(taskName)!=null){
                return Result.fail("任务创建失败，已存在此任务名称的任务");
            }else{
                taskService.save(task);
                return Result.success("任务创建成功",task);
            }
        }
    }
    //任务的更新
    @RequestMapping("/update")
    public Result update(@Validated @RequestBody Task task){
        if(task.getId() == null || task.getTaskId() == null
                || task.getTaskName() == null || task.getTaskEnableStatus() == null){
            return Result.fail("任务更新失败，缺少相应的关键字");

        }else{
            long id = task.getId();
            long taskId = task.getTaskId();
            String taskName = task.getTaskName();
            if(taskService.getById(id)==null){
                return Result.fail("任务更新失败，无此id的任务");
            }else if(taskService.getByTaskId(taskId)!=null){
                return Result.fail("任务更新失败，该任务id已存在");
            }else if(taskService.getByTaskName(taskName)!=null){
                return Result.fail("任务更新失败，该任务名称已存在");
            }else{
                taskService.updateById(task);
                return Result.success("任务更新成功",task);
            }
        }
    }
    //根据id删除任务
    @RequestMapping("/delete/id/{id}")
    public Result deleteId(@PathVariable Long id){
        Task task = taskService.getById(id);
        if(task!=null){
            taskService.deleteById(id);
            return Result.success("删除任务成功",task);
        }else{
            return Result.fail("删除任务失败，无此id的任务");
        }
    }
    //根据任务id删除任务
    @RequestMapping("/delete/taskId/{taskId}")
    public Result deleteTaskId(@PathVariable Long taskId){
        Task task = taskService.getByTaskId(taskId);
        if(task!=null){
            taskService.deleteByTaskId(taskId);
            return Result.success("删除任务成功",task);
        }else{
            return Result.fail("删除任务失败，无此任务id的任务");
        }
    }
    //根据任务名称删除任务
    @RequestMapping("/delete/taskName/{taskName}")
    public Result deleteTaskName(@PathVariable String taskName){
        Task task = taskService.getByTaskName(taskName);
        if(task!=null){
            taskService.deleteByTaskName(taskName);
            return Result.success("删除任务成功",task);
        }else{
            return Result.fail("删除任务失败，无此任务名称的任务");
        }
    }
}
1.2 单元测试@RunWith(SpringRunner.class)
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

        String body = "{\"id\":1,\"taskId\":1,\"taskName\":\"测试\",\"taskEnableStatus\":1}";
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
        String body = "{\"id\":1,\"taskId\":2,\"taskName\":\"测试\",\"taskEnableStatus\":1}";
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
                .get("/task/delete/taskName/����3");


        MvcResult response = mockMvc.perform(postRequestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println("result:" + response.getResponse().getContentAsString());
    }
}
2.奖品表相关接口设计2.1 功能描述查询：根据id、奖品id、奖品名称查询指定奖品详情；获取奖品列表
插入：创建奖品时，奖品关键字段不可为空，且数据库中不可存在重复的id、奖品id、奖品名称
更新：更新奖品时，奖品关键字段不可为空，且数据库中不可存在重复的奖品id、奖品名称
删除：根据id、奖品id、奖品名称删除指定任务
@RestController
@RequestMapping("/prize")
public class PrizeController {
    @Autowired
    PrizeService prizeService;
    //根据id查询奖品

    @RequestMapping("/info/id/{id}")
    public Result infoId(@PathVariable Long id){
        Prize prize = prizeService.getById(id);
        if(prize!=null){
            return Result.success("查询奖品成功",prize);
        }else{
            return Result.fail("查询奖品失败，无此id的奖品");
        }
    }
    //根据奖品id查询奖品

    @RequestMapping("/info/prizeId/{prizeId}")
    public Result infoPrizeId(@PathVariable Long prizeId){
        Prize prize = prizeService.getByPrizeId(prizeId);
        if(prize!=null){
            return Result.success("查询奖品成功",prize);
        }else{
            return Result.fail("查询奖品失败，无此奖品id的奖品");
        }
    }
    //根据奖品名称查询奖品

    @RequestMapping("/info/prizeName/{prizeName}")
    public Result infoPrizeName(@PathVariable String prizeName){
        Prize prize = prizeService.getByPrizeName(prizeName);
        if(prize!=null){
            return Result.success("查询奖品成功",prize);
        }else{
            return Result.fail("查询奖品失败，无此奖品名称的奖品");
        }
    }
    //获取奖品列表
    @RequestMapping("/list")
    public Result List(){
        List<Prize> prizes = prizeService.getAllPrize();
        return Result.success("查询奖品列表成功",prizes);
    }
    //奖品的创建
    @RequestMapping("/save")
    public Result save(@Validated @RequestBody Prize prize){
        if(prize.getId() == null || prize.getPrizeId() == null
                || prize.getPrizeName() == null ){
            return Result.fail("奖品创建失败，缺少相应关键字");

        }else{
            long id = prize.getId();
            long prizeId = prize.getPrizeId();
            String prizeName = prize.getPrizeName();
            if(prizeService.getById(id)!=null){
                return Result.fail("奖品创建失败，已存在此id的奖品");
            }else if(prizeService.getByPrizeId(prizeId)!=null){
                return Result.fail("奖品创建失败，已存在此奖品id的奖品");
            }else if(prizeService.getByPrizeName(prizeName)!=null){
                return Result.fail("奖品创建失败，已存在此奖品名称的奖品");
            }else{
                prizeService.save(prize);
                return Result.success("奖品创建成功",prize);
            }
        }
    }
    //奖品的更新
    @RequestMapping("/update")
    public Result update(@Validated @RequestBody Prize prize){
        if(prize.getId() == null || prize.getPrizeId() == null
                || prize.getPrizeName() == null){
            return Result.fail("奖品更新失败，缺少相应的关键字");

        }else{
            long id = prize.getId();
            long prizeId = prize.getPrizeId();
            String prizeName = prize.getPrizeName();
            if(prizeService.getById(id)==null){
                return Result.fail("奖品更新失败，无此id的奖品");
            }else if(prizeService.getByPrizeId(prizeId)!=null){
                return Result.fail("奖品更新失败，该奖品id已存在");
            }else if(prizeService.getByPrizeName(prizeName)!=null){
                return Result.fail("奖品更新失败，该奖品名称已存在");
            }else{
                prizeService.updateById(prize);
                return Result.success("奖品更新成功",prize);
            }
        }
    }
    //根据id删除奖品
    @RequestMapping("/delete/id/{id}")
    public Result deleteId(@PathVariable Long id){
        Prize prize = prizeService.getById(id);
        if(prize!=null){
            prizeService.deleteById(id);
            return Result.success("删除奖品成功",prize);
        }else{
            return Result.fail("删除奖品失败，无此id的奖品");
        }
    }
    //根据奖品id删除奖品
    @RequestMapping("/delete/prizeId/{prizeId}")
    public Result deleteByPrizeId(@PathVariable Long prizeId){
        Prize prize = prizeService.getByPrizeId(prizeId);
        if(prize!=null){
            prizeService.deleteByPrizeId(prizeId);
            return Result.success("删除奖品成功",prize);
        }else{
            return Result.fail("删除奖品失败，无此奖品id的奖品");
        }
    }
    //根据奖品名称删除奖品
    @RequestMapping("/delete/prizeName/{prizeName}")
    public Result deleteByPrizeName(@PathVariable String prizeName){
        Prize prize = prizeService.getByPrizeName(prizeName);
        if(prize!=null){
            prizeService.deleteByPrizeName(prizeName);
            return Result.success("删除奖品成功",prize);
        }else{
            return Result.fail("删除奖品失败，无此奖品名称的奖品");
        }
    }
}
2.2 单元测试@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
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
3.任务明细表相关接口设计3.1 功能描述查询：根据id、任务id、人员id查询指定任务明细；根据任务id、人员id联合查询指定任务明细；获取任务明细列表
插入：完成任务时，任务状态必须处于启用状态，完成任务人员必须在任务范围内，不能重复添加任务明细
@RestController
@RequestMapping("/task-details")
public class TaskDetailsController {
    //根据id查询任务明细
    @Autowired
    TaskDetailsService taskDetailsService;
    @Autowired
    TaskService taskService;
    @RequestMapping("info/id/{id}")
    public Result infoId(@PathVariable Long id){
        TaskDetails taskDetails = taskDetailsService.getById(id);
        if(taskDetails!=null){
            return Result.success("查询任务明细成功",taskDetails);
        }else{
            return Result.fail("查询任务明细失败，无此id的任务明细");
        }
    }
    //根据人员id查询任务明细
    @RequestMapping("/info/participantId/{participantId}")
    public Result infoParticipantId(@PathVariable Long participantId){
        List<TaskDetails> taskDetails = taskDetailsService.getByParticipantId(participantId);
        if(taskDetails.size()!=0){
            return Result.success("查询任务明细成功",taskDetails);
        }else{
            return Result.fail("查询任务明细失败，无此人员id的任务明细");
        }
    }
    //根据任务id查询任务明细
    @RequestMapping("/info/taskId/{taskId}")
    public Result infoTaskId(@PathVariable Long taskId){
        List<TaskDetails> taskDetails = taskDetailsService.getByTaskId(taskId);
        if(taskDetails.size()!=0){
            return Result.success("查询任务明细成功",taskDetails);
        }else{
            return Result.fail("查询任务明细失败，无此任务id的任务明细");
        }
    }

    //根据任务id与人员id联合查询任务明细
    @RequestMapping("/info/taskIdAndParticipantId/{taskId}/{participantId}")
    public Result infoTaskId(@PathVariable Long taskId,@PathVariable Long participantId){
        List<TaskDetails> taskDetails = taskDetailsService.getByTaskIdAndParticipantId(taskId,participantId);
        if(taskDetails.size()!=0){
            return Result.success("查询任务明细成功",taskDetails);
        }else{
            return Result.fail("查询任务明细失败，无此任务id与人员id对应的任务明细");
        }
    }
    //获取任务明细列表
    @RequestMapping("/list")
    public Result List(){
        List<TaskDetails> taskDetails = taskDetailsService.getAllTaskDetails();
        return Result.success("获取任务明细列表成功",taskDetails);
    }
    //完成任务
    @RequestMapping("/save")
    public Result save(@Validated @RequestBody TaskDetails taskDetails){
        if(taskDetails.getId()==null||taskDetails.getTaskId()==null||taskDetails.getParticipantId()==null||taskDetails.getTaskFinishTime()==null){
            return Result.fail("完成任务失败，缺少相应关键字");
        }
        long participantId = taskDetails.getParticipantId();
        long id = taskDetails.getId();
        long taskId = taskDetails.getTaskId();
        Task task = taskService.getByTaskId(taskId);
        String taskScope = task.getTaskScope();
        int taskEnableStatus = task.getTaskEnableStatus();
        if(taskEnableStatus!=1){
            return Result.fail("完成任务失败，任务状态异常");
        }else if(taskScope.contains(String.valueOf(participantId))==false){
            return Result.fail("完成任务失败，该人员不在任务范围内");
        }else if(taskDetailsService.getById(id)!=null){
            return Result.fail("该任务明细已记录");
        }else{
            taskDetailsService.save(taskDetails);
            return Result.success("完成任务成功",taskDetails);
        }
    }
}
3.2 单元测试@RunWith(SpringRunner.class)
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
    TaskService taskService;

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
     * 测试完成奖品分发接口
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
}
4.奖品分发明细表相关接口设计4.1 功能描述查询：根据id、任务id、人员id、奖品id查询指定奖品分发明细；根据任务id、人员id、奖品id联合查询指定奖品分发明细；获取奖品分发明细列表
插入：完成奖品分发时，该人员必须已完成相应的任务，并且分发的奖品与任务的奖品要对应，不可重复创建奖品分发明细
@RestController
@RequestMapping("/prize-distribution-details")
public class PrizeDistributionDetailsController {
    //根据id查询奖品分发明细
    @Autowired
    PrizeDistributionDetailsService prizeDistributionDetailsService;
    @Autowired
    TaskDetailsService taskDetailsService;
    @Autowired
    TaskService taskService;
    @RequestMapping("info/id/{id}")
    public Result infoId(@PathVariable Long id) {
        PrizeDistributionDetails prizeDistributionDetails = prizeDistributionDetailsService.getById(id);
        if (prizeDistributionDetails != null) {
            return Result.success("查询奖品分发明细成功", prizeDistributionDetails);
        } else {
            return Result.fail("查询奖品分发明细失败，无此id的奖品分发明细");
        }
    }

    //根据人员id查询奖品分发明细
    @RequestMapping("info/participantId/{participantId}")
    public Result infoParticipantId(@PathVariable Long participantId) {
        List<PrizeDistributionDetails> prizeDistributionDetails = prizeDistributionDetailsService.getByParticipantId(participantId);
        if (prizeDistributionDetails.size() != 0) {
            return Result.success("查询奖品分发明细成功", prizeDistributionDetails);
        } else {
            return Result.fail("查询奖品分发明细失败，无此人员id的奖品分发明细");
        }
    }

    //根据任务id查询奖品分发明细
    @RequestMapping("info/taskId/{taskId}")
    public Result infoTaskId(@PathVariable Long taskId) {
        List<PrizeDistributionDetails> prizeDistributionDetails = prizeDistributionDetailsService.getByTaskId(taskId);
        if (prizeDistributionDetails.size() != 0) {
            return Result.success("查询奖品分发明细成功", prizeDistributionDetails);
        } else {
            return Result.fail("查询奖品分发明细失败，无此任务id的奖品分发明细");
        }
    }

    //根据奖品id查询奖品分发明细
    @RequestMapping("info/prizeId/{prizeId}")
    public Result infoPrizeId(@PathVariable Long prizeId) {
        List<PrizeDistributionDetails> prizeDistributionDetails = prizeDistributionDetailsService.getByPrizeId(prizeId);
        if (prizeDistributionDetails.size() != 0) {
            return Result.success("查询奖品分发明细成功", prizeDistributionDetails);
        } else {
            return Result.fail("查询奖品分发明细失败，无此奖品id的奖品分发明细");
        }
    }

    //获取奖品分发明细列表
    @RequestMapping("/list")
    public Result list() {
        List<PrizeDistributionDetails> prizeDistributionDetails = prizeDistributionDetailsService.getAllPrizeDistributionDetails();

        return Result.success("查询奖品分发明细成功", prizeDistributionDetails);

    }

    //根据人员id、任务id、奖品id联合查询奖品分发明细
    @RequestMapping("/info/unionQuery/{participantId}/{taskId}/{prizeId}")
    public Result unionQuery(@PathVariable Long participantId, @PathVariable Long taskId, @PathVariable Long prizeId) {
        List<PrizeDistributionDetails> prizeDistributionDetails = prizeDistributionDetailsService.getByUnionQuery(participantId, taskId, prizeId);
        if (prizeDistributionDetails.size() != 0) {
            return Result.success("查询奖品分发明细成功", prizeDistributionDetails);
        } else {
            return Result.fail("查询奖品分发明细失败，无此人员id、任务id、奖品id对应的奖品分发明细");
        }
    }

    //奖品分发
    @RequestMapping("/save")
    public Result save(@Validated @RequestBody PrizeDistributionDetails prizeDistributionDetails) {
        if (prizeDistributionDetails.getId() == null || prizeDistributionDetails.getPrizeId() == null
                || prizeDistributionDetails.getTaskId() == null || prizeDistributionDetails.getParticipantId() == null || prizeDistributionDetails.getPrizeDistributionTime() == null) {
            return Result.fail("奖品分发明细创建失败，缺少相应关键字");

        } else {
            long id = prizeDistributionDetails.getId();
            long prizeId = prizeDistributionDetails.getPrizeId();
            long participantId = prizeDistributionDetails.getParticipantId();
            long taskId = prizeDistributionDetails.getTaskId();
            Task task = taskService.getByTaskId(taskId);
            if(taskDetailsService.getByTaskIdAndParticipantId(taskId,participantId)==null){
                return Result.fail("奖品分发明细创建失败，该人员并未完成此任务");
            }else if(prizeId!=task.getTaskPrizeId()){
                return Result.fail("奖品分发明细创建失败，任务与奖品不对应");
            }else if(prizeDistributionDetailsService.getById(id)!=null){
                return Result.fail("奖品分发明细创建失败，该奖品分发明细已创建");
            }else{
                prizeDistributionDetailsService.save(prizeDistributionDetails);
                return Result.success("奖品分发明细创建成功",prizeDistributionDetails);
            }
        }
    }
}
4.2 单元测试@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
// @AutoConfigureMockMvc是用于自动配置MockMvc
@AutoConfigureMockMvc
public class PrizeDistributionDetailsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    TaskService taskService;

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
五、设置缓存查询：为频繁查询的方法设置缓存，如taskInfoId、prizeInfoId等，设置缓存时设置缓存失效时间为5分钟
插入：无需设置缓存
更新：更新成功时需根据id删除所有相关缓存
删除：删除成功时需根据id删除所有相关缓存
@Configuration
public class JimdbConfig {

    /**
     * jimdb地址
     */
    private final String url;

    /**
     * jimdb客户端
     */
    private final String configId;

    /**
     * 初始化缓存参数
     *
     * @param url      url
     * @param configId configId
     */
    public JimdbConfig(@Value("${jimp.uri}") String url, @Value("${jimp.configId}") String configId) String configId) {
        this.url = url;
        this.configId = configId;
    }

    /**
     * 初始化bean
     *
     * @return Cluster
     */
    @Bean
    public Cluster createJimdbCluster() {
        ConfigLongPollingClientFactory configLongPollingClientFactory = new ConfigLongPollingClientFactory();
        ConfigClient configClient = configLongPollingClientFactory.create();
        ReloadableJimClientFactory reloadableJimClientFactory = new ReloadableJimClientFactory();
        reloadableJimClientFactory.setConfigClient(configClient);
        reloadableJimClientFactory.setJimUrl(url);
        reloadableJimClientFactory.setConfigId(configId);
        return reloadableJimClientFactory.getClient();
    }
}
