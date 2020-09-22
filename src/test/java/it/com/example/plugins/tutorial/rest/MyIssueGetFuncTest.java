package it.com.example.plugins.tutorial.rest;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.example.plugins.tutorial.rest.MyIssueGet;
import com.example.plugins.tutorial.rest.MyIssueGetModel;
import org.apache.wink.client.Resource;
import org.apache.wink.client.RestClient;

public class MyIssueGetFuncTest {

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void messageIsValid() {

        String baseUrl = System.getProperty("baseurl");
        String resourceUrl = baseUrl + "/rest/myissueget/1.0/message";

        RestClient client = new RestClient();
        Resource resource = client.resource(resourceUrl);

        MyIssueGetModel message = resource.get(MyIssueGetModel.class);

        assertEquals("wrong message","Hello World",message.getMessage());
    }
}
