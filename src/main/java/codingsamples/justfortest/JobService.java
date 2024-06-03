package codingsamples.justfortest;

import codingsamples.justfortest.model.JobPosition;
import codingsamples.justfortest.model.Person;

import java.util.Optional;

public interface JobService {
 
    Optional<JobPosition> findCurrentJobPosition(Person person);
    
    default boolean assignJobPosition(Person person, JobPosition jobPosition) {
        if(!findCurrentJobPosition(person).isPresent()) {
            person.setCurrentJobPosition(jobPosition);
            
            return true;
        } else {
            return false;
        }
    }
}