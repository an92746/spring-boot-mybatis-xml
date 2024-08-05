package com.neo.web;

import java.io.*;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.neo.mapper.ForexMapper;
import com.neo.model.*;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.neo.mapper.UserMapper;


@RestController
public class UserController {

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @Autowired
	private UserMapper userMapper;

    @Autowired
    public ForexMapper forexMapper;



    @RequestMapping(value = {"/getAPI"})
    public String getManualMobileUsageOpenApiJob(HttpServletRequest request){

        String date = request.getParameter("date");

        logger.info(">>>>>>>>>>>>>>>>>>>行動支付使用次數手動上傳");
        logger.info(">>>>>>>>>>>>>>>>>>>行動支付使用次數手動上傳完成:{}",date);
        return "ok";
    }


    @RequestMapping(value = {"/getForex"})
    public String getForex() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<Forex> forexList = objectMapper.readValue(new File(System.getProperty("user.dir") + "/src/JsonFile/DailyForeignExchangeRates.json"), new TypeReference<List<Forex>>() {});
            for (Forex forex : forexList) {
                forexMapper.insertForex(forex);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @PostMapping("/getSelectForex")
    public String getSelectForex(@RequestBody SelectForex selectForex) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = null;

        try {

            String StartDate = selectForex.getStartDate().replaceAll("/", "");
            String EndDate = selectForex.getEndDate().replaceAll("/", "");

            logger.info(selectForex.getCurrency() +"~"+ StartDate+"~"+EndDate);
            Forex forex=forexMapper.getSelectForex(StartDate,EndDate,selectForex.getCurrency());
           // List<Forex> forexList = objectMapper.readValue(new File(System.getProperty("user.dir") + "/src/JsonFile/DailyForeignExchangeRates.json"), new TypeReference<List<Forex>>() {});
           // for (Forex forex : forexList) {
                if(forex.getUsd_twd()== null){
                    CustomError errors = new CustomError();
                    errors.setCode("E001");
                    errors.setMessage("日期區間不符");
                    jsonString = objectMapper.writeValueAsString(errors);
                }else {
                    CustomSuccess success = new CustomSuccess();
                    CustomError errors = new CustomError();
                    errors.setCode("0000");
                    errors.setMessage("成功");
                    success.setError(errors);
                    Currency currency = new Currency();
                    currency.setDate(forex.getDate());
                    currency.setUsd(forex.getUsd_twd());
                    success.setCurrency(currency);
                    jsonString = objectMapper.writeValueAsString(success);
                }
            //}


        } catch (IOException e) {
            e.printStackTrace();
        }

        return jsonString;
    }



	@RequestMapping("/getUsers")
	public List<User> getUsers() {
		List<User> users=userMapper.getAll();
		return users;
	}
	
    @RequestMapping("/getUser")
    public User getUser(Long id) {
    	User user=userMapper.getOne(id);
        return user;
    }
    
    @RequestMapping("/add")
    public void save(User user) {
    	userMapper.insert(user);
    }
    
    @RequestMapping(value="update")
    public void update(User user) {
    	userMapper.update(user);
    }
    
    @RequestMapping(value="/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
    	userMapper.delete(id);
    }
    
    
}