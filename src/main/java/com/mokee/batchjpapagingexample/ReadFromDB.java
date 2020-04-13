package com.mokee.batchjpapagingexample;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.orm.JpaNativeQueryProvider;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.BeanWrapperFieldExtractor;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
public class ReadFromDB {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    @Bean
    public Job readUser() throws Exception {
        return jobBuilderFactory.get("readUser")
                .incrementer(new RunIdIncrementer())
                .flow(step1())
                .end()
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<User, User>chunk(10)
                .reader(reader())
                .writer(writer())
                .build();
    }

    @Bean
    public ItemReader<User> reader() throws Exception {
       /* String sqlQuery = "select * from batch_user";

        JpaPagingItemReader<User> reader = new JpaPagingItemReader<>();

        //creating a native query provider as it would be created in configuration
        JpaNativeQueryProvider<User> queryProvider = new JpaNativeQueryProvider<>();
        queryProvider.setSqlQuery(sqlQuery);
        queryProvider.setEntityClass(User.class);
        queryProvider.afterPropertiesSet();

        // reader.setParameterValues(Collections.<String, Object>singletonMap("limit", 2));
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setPageSize(30);
        reader.setQueryProvider(queryProvider);
        reader.afterPropertiesSet();
        reader.setSaveState(true);*/

        return new CustomJpaItemReader();
    }


    @Bean
    public FlatFileItemWriter<User> writer() {
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<>();
        writer.setResource(new FileSystemResource("files/users.txt"));
        writer.setLineAggregator(new DelimitedLineAggregator<User>() {{
            setDelimiter("|");
            setFieldExtractor(new BeanWrapperFieldExtractor<User>() {{
                setNames(new String[]{"id", "username", "password", "age"});
            }});
        }});
        return writer;
    }


}
