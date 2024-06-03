package codingsamples.justfortest;

import codingsamples.justfortest.model.JobPosition;
import codingsamples.justfortest.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SomeOtherService {

    @Autowired
    private JobService jobService;

    public boolean callAssignJobPosition() {
        return jobService.assignJobPosition(new Person(), new JobPosition());
    }
}
