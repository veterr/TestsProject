package codingsamples.justfortest;

import codingsamples.justfortest.model.JobPosition;
import codingsamples.justfortest.model.Person;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
    @Override
    public Optional<JobPosition> findCurrentJobPosition(Person person) {
        return Optional.empty();
    }
}
