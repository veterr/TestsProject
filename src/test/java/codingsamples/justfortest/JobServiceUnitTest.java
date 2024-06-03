package codingsamples.justfortest;

import codingsamples.justfortest.model.JobPosition;
import codingsamples.justfortest.model.Person;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.when;

public class JobServiceUnitTest {
 
    @Mock
    private JobService jobService;

    @InjectMocks
    private SomeOtherService someOtherService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenDefaultMethod_whenCallRealMethod_thenNoExceptionIsRaised() {
        Person person = new Person();

        when(jobService.findCurrentJobPosition(person))
              .thenReturn(Optional.of(new JobPosition()));

        doCallRealMethod().when(jobService)
          .assignJobPosition(
            Mockito.any(Person.class),
            Mockito.any(JobPosition.class)
        );

        assertFalse(jobService.assignJobPosition(person, new JobPosition()));
        when(jobService.assignJobPosition(Mockito.any(Person.class), Mockito.any(JobPosition.class)))
                .thenReturn(false);
        assertFalse(someOtherService.callAssignJobPosition());
    }
}