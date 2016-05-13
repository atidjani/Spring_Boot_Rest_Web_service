package com.hmitchang.memberservice;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import com.hmitchang.memberservice.model.Member;
import com.hmitchang.memberservice.service.MemberService;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Josh Long
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class MemberServiceTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;


    private HttpMessageConverter mappingJackson2HttpMessageConverter;
   

    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    private MemberService memberService;


    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream().filter(
                hmc -> hmc instanceof MappingJackson2HttpMessageConverter).findAny().get();

        Assert.assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.memberService.saveMember(new Member(0,"user1","user1",new Date(),12345));
        this.memberService.saveMember(new Member(0,"user2","user2",new Date(),12345));

    }
    
    
  //--------- GET ------------
    @Test
    public void getMember() throws Exception {
        mockMvc.perform(get("/members/1")
                .content(this.json(new Member()))
                .contentType(contentType))
                .andExpect(status().isOk())
        		.andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.firstName", is("user1")))
                .andExpect(jsonPath("$.lastName", is("user1")));
        
        //get a non existent member
        mockMvc.perform(get("/members/50")
                .content(this.json(new Member()))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }
    
  //--------- GET ALL ------------
    @Test
    public void getAllMembers() throws Exception {
        mockMvc.perform(get("/members"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$", hasSize(memberService.getAllMembers().size())));
    }
    
    
  //--------- ADD ------------
    @Test
    public void saveMember() throws Exception {
    	Member newMember = new Member(0,"user3","user3",new Date(),123456);
    	
    	this.mockMvc.perform(post("/members")
    			.contentType(contentType)
    			.content(json(newMember)))
    			.andExpect(status().isCreated())
    			.andExpect(content().contentType(contentType))
    			.andExpect(jsonPath("$.postalCode", is(123456)))
    			.andExpect(jsonPath("$.firstName", is("user3")));
    		
    }
    
  //--------- DELETE ------------
    @Test
    public void deleteMember() throws Exception {
    	Member newMember = new Member(0,"user3","user3",new Date(),123456);
    	
    	//save the new member
    	long id = this.memberService.saveMember(newMember);
    	
    	//delete it
    	this.mockMvc.perform(delete("/members/"+ id))
    			.andExpect(status().isOk());
    	//check if it is truly deleted
    	this.mockMvc.perform(get("/members/" + id))
    			.andExpect(status().isNotFound());
    	
    	//delete a non existent member
    	this.mockMvc.perform(delete("/members/100"))
		.andExpect(status().isNotFound());
    		
    }
    
    //--------- UPDATE ------------
    @Test
    public void updateMember() throws Exception {
    	Member newMember = new Member(0,"user3","user3",new Date(),123456);
    	
    	//save the new member
    	long id = this.memberService.saveMember(newMember);
    	
    	newMember.setPostalCode(10000);
    	
    	//update it
    	this.mockMvc.perform(put("/members/"+ id)
    			.contentType(contentType)
    			.content(json(newMember)))
    			.andExpect(status().isOk())
    			.andExpect(jsonPath("$.postalCode", is(10000)));

    	
    	//update a non existent member
    	this.mockMvc.perform(delete("/members/100")
				.contentType(contentType)
				.content(json(newMember)))
				.andExpect(status().isNotFound());
    		
    }
    
        
    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}

