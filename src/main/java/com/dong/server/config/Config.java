package com.dong.server.config;


import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.dong.server.config.util.JsonUtil;
import com.dong.server.config.viewresolver.Jaxb2MarshallingXmlViewResolver;
import com.dong.server.config.viewresolver.JsonViewResolver;
import com.dong.server.spring.entity.Hello;

@Configuration  //启用配置文件
@ComponentScan("com.dong.server")  // 扫描包路径
@EnableWebMvc    //启用Mvc
@EnableTransactionManagement   //启用事物管理
@EnableAspectJAutoProxy   //启用aop注解
@EnableScheduling    //启用后台事物
//@EnableConfigurationProperties
//@PropertySource("classpath:com/dong/server/db.properties") 
//@PropertySource(value = { "classpath:db.properties","classpath:db2.properties" })
public class Config extends WebMvcConfigurerAdapter{

	
	/**
	 * 默认情况下 用json输出 
	 * 或者调用的时  直接指定方法.json
	 * 第一步是建立它用于通过委托给ContentNegotiationManager，以确定所请求的媒体类型的请求是 ContentNegotiationStrategy 列表的一个实例。
	 * 默认情况下PathExtensionContentNegotiationStrategy被查询(使用URL扩展名，例如. .xls, .pdf,.json.)，
	 * 接着ParameterContentNegotiationStrategy(使用请求参数 ‘format=xls’，例如)，
	 * 其次是HeaderContentNegotiationStrategy(使用HTTP接受头)。
	 */
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {  
        configurer.ignoreAcceptHeader(true).defaultContentType(  
                MediaType.TEXT_HTML); //APPLICATION_JSON
              // MediaType.TEXT_HTML 为jsp  视图解析器处理
    }
	
	
//	public void configureViewResolvers(ViewResolverRegistry registry) {
//		// TODO Auto-generated method stub
//		super.configureViewResolvers(registry);
//		registry.jsp();
//		registry.viewResolver(viewResolver);
//	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		super.addResourceHandlers(registry);
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");  
		registry.addResourceHandler("/html/**").addResourceLocations("/html/");  
	}
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		// TODO Auto-generated method stub
		super.configureDefaultServletHandling(configurer);
		// configurer.enable(); //配置静态文件处理
		 
		
	}
	
	@Bean
	public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
		ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
		resolver.setContentNegotiationManager(manager);

		// Define all possible view resolvers
		List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		resolvers.add(jaxb2MarshallingXmlViewResolver());
		resolvers.add(jsonViewResolver());
		resolvers.add(jspViewResolver());
		//resolvers.add(pdfViewResolver());
		//resolvers.add(excelViewResolver());
		resolver.setOrder(1);
		resolver.setViewResolvers(resolvers);
		return resolver;
		
	}
	
	/*
	 * Configure View resolver to provide XML output Uses JAXB2 marshaller to
	 * marshall/unmarshall POJO's (with JAXB annotations) to XML
	 */
	@Bean
	public ViewResolver jaxb2MarshallingXmlViewResolver() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		marshaller.setClassesToBeBound(Hello.class);
		return new Jaxb2MarshallingXmlViewResolver(marshaller);
	}
	
//	@Bean
//	public ViewResolver pdfViewResolver() {
//		return new PdfViewResolver();
//	}

	/*
	 * Configure View resolver to provide XLS output using Apache POI library to
	 * generate XLS output for an object content
	 */
//	@Bean
//	public ViewResolver excelViewResolver() {
//		return new ExcelViewResolver();
//	}
	
   @Bean  
    public ViewResolver jsonViewResolver() {
    	//MappingJackson2JsonView view = new MappingJackson2JsonView();
        return new JsonViewResolver();  
    }  
   
    @Bean(name = "multipartResolver")
	public StandardServletMultipartResolver resolver() {
		return new StandardServletMultipartResolver();
	}
    
    /**
     * jsp视图解析器的bean
     * @return
     */
    @Bean  
    public ViewResolver jspViewResolver() {  
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();  
        viewResolver.setViewClass(JstlView.class);  
        viewResolver.setPrefix("/WEB-INF/jsp/");  
        viewResolver.setSuffix(".jsp");  
        //可以在JSP页面中通过${}访问beans
        viewResolver.setExposeContextBeansAsAttributes(true);
        return viewResolver;  
    }
    
    
	 /*
     * PropertySourcesPlaceHolderConfigurer Bean only required for @Value("{}") annotations.
     * Remove this bean if you are not using @Value annotations for injecting properties.
     */
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
      PropertySourcesPlaceholderConfigurer config  = new PropertySourcesPlaceholderConfigurer();
       
//      config.setPlaceholderPrefix(PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_PREFIX);
//      config.setPlaceholderSuffix(PlaceholderConfigurerSupport.DEFAULT_PLACEHOLDER_SUFFIX);
//      config.setValueSeparator(PlaceholderConfigurerSupport.DEFAULT_VALUE_SEPARATOR);  
        String filePathdb = "db.properties";//src/main/resources/
        String filePathdbtwo ="db2.properties";
        //config.setLocation(new ClassPathResource(filePath,Config.class.getClassLoader()));
        config.setLocations(new ClassPathResource[]{new ClassPathResource(filePathdb,Config.class.getClassLoader()),new ClassPathResource(filePathdbtwo,Config.class.getClassLoader())});
        
        return config;
    }
    
   
    
   @Autowired
   private Environment environment;
   
   @Value("${db.url}")
   private String dburl;
    

    /**
     * 数据库1配置
     */
    
    public DataSource  getdataSource(
            @Value("${db.url}")String url,
            @Value("${db.user}")String username,
            @Value("${db.password}")String password,
            @Value("${db.driver}")String driverClassName
            ){

    	 DriverManagerDataSource dataSource = new DriverManagerDataSource();
         dataSource.setUrl(url.trim());
         dataSource.setUsername(username.trim());
         dataSource.setPassword(password.trim());
         dataSource.setDriverClassName(driverClassName.trim());
      return dataSource;
    }
    
    
   
    
    /**
     * 数据库2 配置
     * @param url
     * @param username
     * @param password
     * @param driverClassName
     * @return
     */
   
    public DataSource  getdataSource2(
            @Value("${db2.url}")String url,
            @Value("${db2.user}")String username,
            @Value("${db2.password}")String password,
            @Value("${db2.driver}")String driverClassName
            ){
    	
    	DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url.trim());
        dataSource.setUsername(username.trim());
        dataSource.setPassword(password.trim());
        dataSource.setDriverClassName(driverClassName.trim());
        return dataSource;
    }

    
    
    @Bean
    public DynamicDataSource getDynamicDataSource(){
    	DynamicDataSource ss = new DynamicDataSource();
    	
    	 Map<Object, Object> map = new HashMap<Object, Object>();
    	// try {
    		
//			InputStream db = new BufferedInputStream(new FileInputStream("db.properties"));
//			InputStream db2 = new BufferedInputStream(new FileInputStream("db2.properties"));
//			Properties p1 = new Properties();
//			Properties p2 = new Properties();
//			p1.load(db);
//			p2.load(db2);
//			
//			map.put("dataSource", getdataSource(p1.getProperty("db.url"),p1.getProperty("db.user"),p1.getProperty("db.password"),p1.getProperty("db.driver")));
//	    	 
//	     	DataSource datasource = getdataSource2(p2.getProperty("db2.url"),p2.getProperty("db2.user"),p2.getProperty("db2.password"),p2.getProperty("db2.driver"));
    		 map.put("dataSource", getdataSource("jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true","root","root","com.mysql.jdbc.Driver"));
        	 
        	String url = this.dburl;
        	Environment wnvir = this.environment;
    	    	
    	    DataSource datasource = getdataSource2("jdbc:mysql://localhost:3306/testtwo?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true","root","root","com.mysql.jdbc.Driver");
	    	
	     	map.put("dataSource2", datasource);
	    	ss.setTargetDataSources(map);
	    	ss.setDefaultTargetDataSource(datasource);
	    	
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	 catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	 
    	 
    	return ss;
    	
    }
	

    
    @Bean(name="sqlSessionFactory")
    //@Scope(value=ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public SqlSessionFactoryBean  sqlSessionFactory(DataSource ds){
        SqlSessionFactoryBean  factory = new SqlSessionFactoryBean();

        factory.setDataSource(ds);
        
        return factory;
    }

    //用来配置，MyBatis 扫包范围，从而为我们创建Dao层的实现
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer configurer = new MapperScannerConfigurer();

        configurer.setBasePackage("com.dong.server.spring.dao");
        //方法名即为BeanName
        configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
       // configurer.setBeanName("txmanager");
        return configurer;
    }

  //用来在 MyBatis 环境中控制数据库事物的，使用即在你的service 方法上加 @Transactional 即可
    @Bean//(name="txmanager")
    public PlatformTransactionManager  dataSourceTransactionManager(DataSource ds){
        DataSourceTransactionManager  tm  = new DataSourceTransactionManager();
        tm.setDataSource(ds);
        
        return tm;
    }
    
   
}
