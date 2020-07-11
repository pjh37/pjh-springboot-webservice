package com.pjh.share.domain.group;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pjh.share.api.GroupApiController;
import com.pjh.share.service.GroupService;
import com.pjh.share.web.dto.GroupCreateRequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GroupApiControllerTest {
    @LocalServerPort
    private int port;


    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private GroupRepository groupRepository;

    @Before
    public void setup(){
        mvc= MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @After
    public void tearDown() throws Exception{
        groupRepository.deleteAll();
    }

    @Test
    @WithMockUser(roles="USER")
    public void 그룹_생성요청()throws Exception{
        String title="스프링 모임";
        String des="스프링 고수만";
        GroupCreateRequestDto request=new GroupCreateRequestDto();
        request.setTitle(title);
        request.setDescription(des);
        request.setTotalNum(5);
        request.setCurrentNum(0);
        request.setFile(null);
        request.setPassword(null);

        String url="http://localhost:"+port+"/api/group";


        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper()
                        .writeValueAsString(request))).andExpect(status().isOk());
    }

}
