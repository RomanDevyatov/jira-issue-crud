package ut.com.example.plugins.tutorial.rest;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import com.example.plugins.tutorial.rest.MyIssueGet;
import com.example.plugins.tutorial.rest.MyIssueGetModel;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.GenericEntity;

public class MyIssueGetTest {

    @Before
    public void setup() {

    }

    @After
    public void tearDown() {

    }

    @Test
    public void messageIsValid() {
        MyIssueGet resource = new MyIssueGet();

        Response response = resource.getMessage();
        final MyIssueGetModel message = (MyIssueGetModel) response.getEntity();

        assertEquals("wrong message","Hello World",message.getMessage());
    }
}
