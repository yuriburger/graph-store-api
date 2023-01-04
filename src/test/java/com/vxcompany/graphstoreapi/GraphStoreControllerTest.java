package com.vxcompany.graphstoreapi;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
public class GraphStoreControllerTest {

    private MockMvc mvc;
    @Mock
    private InMemory store;

    @InjectMocks
    private GraphStoreController graphStoreController;

    @BeforeEach
    public void setup() throws JSONException {
        mvc = MockMvcBuilders.standaloneSetup(graphStoreController)
                .build();
    }

    @Test
    public void canRetrieveByIdWhenExists() throws Exception {
        JSONObject user1 = new JSONObject();
        user1.put("id","1");
        user1.put("username","johnny");
        user1.put("age","28");

        // given
        given(store.Get("user", "1"))
                .willReturn(user1);

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/user/1")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(user1.toString());
    }

    @Test
    public void throwsExceptionWhenIdDoesNotExist() throws Exception {
        // given
        given(store.Get("user", "2"))
                .willThrow(new KeyNotFoundException(""));

        // when
        MockHttpServletResponse response = mvc.perform(
                        get("/user/2")
                                .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
        assertThat(response.getContentAsString()).isEmpty();
    }
}
