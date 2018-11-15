package br.jad.comparator.it;

import br.jad.comparator.commons.ComparatorType;
import br.jad.comparator.domain.ComparatorRecord;
import br.jad.comparator.domain.ComparatorServiceImpl;
import net.minidev.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

/**
 * ComparatorController integration tests
 */
@RunWith(SpringRunner.class)
@WebMvcTest
public class ComparatorControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ComparatorServiceImpl comparatorService;

    @Before
    public void init() {
        when(comparatorService.save(anyLong(), any(ComparatorType.class), anyString()))
                .thenReturn(new ComparatorRecord());
    }

    @Test
    public void saveLeft_WithoutId_ExceptionThrown() throws Exception {
        mockMvc.perform(post("/v1/diff/left")
                .content("eyAiY29kZSI6ICJURVNUMSIgfSwgeyAibmFtZSI6ICJUZXN0IE9uZSIgfQ=="))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void saveLeft_WithoutContent_ExceptionThrown() throws Exception {
        mockMvc.perform(post("/v1/diff/123/left"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveLeft_ValidRequest_Success() throws Exception {
        mockMvc.perform(post("/v1/diff/123/left")
                .content("eyAiY29kZSI6ICJURVNUMSIgfSwgeyAibmFtZSI6ICJUZXN0IE9uZSIgfQ=="))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void saveRight_Withoutid_ExceptionThrown() throws Exception {
        mockMvc.perform(post("/v1/diff/right")
                .content("eyAiY29kZSI6ICJURVNUMSIgfSwgeyAibmFtZSI6ICJUZXN0IE9uZSIgfQ=="))
                .andDo(print())
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void saveRight_WithoutContent_ExceptionThrown() throws Exception {
        mockMvc.perform(post("/v1/diff/123/right"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void saveRight_ValidRequest_Success() throws Exception {
        mockMvc.perform(post("/v1/diff/123/right")
                .content("eyAiY29kZSI6ICJURVNUMSIgfSwgeyAibmFtZSI6ICJUZXN0IE9uZSIgfQ=="))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getDifference_IsEqual_Success() throws Exception {
        JSONObject response = new JSONObject();
        response.put("result", "IS EQUAL");

        when(comparatorService.getDifference(anyLong()))
                .thenReturn(response.toString());

        mockMvc.perform(get("/v1/diff/123")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("{\"result\":\"IS EQUAL\"}"));
    }
}
