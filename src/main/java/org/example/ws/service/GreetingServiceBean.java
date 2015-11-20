package org.example.ws.service;

import org.example.ws.model.Greeting;
import org.example.ws.repository.GreetingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import java.util.Collection;

@Service
public class GreetingServiceBean implements GreetingService {


    /**
     * The <code>CounterService</code> captures metrics for Spring Actuator.
     */

    /**
     * The Spring Data repository for Greeting entities.
     */
    @Autowired
    private GreetingRepository greetingRepository;

    @Override
    public Collection<Greeting> findAll() {


        Collection<Greeting> greetings = greetingRepository.findAll();


        return greetings;
    }


    public Greeting findOne(Long id) {


        Greeting greeting = greetingRepository.findOne(id);


        return greeting;
    }

    @Override

    public Greeting create(Greeting greeting) {

        // Ensure the entity object to be created does NOT exist in the
        // repository. Prevent the default behavior of save() which will update
        // an existing entity if the entity matching the supplied id exists.
        if (greeting.getId() != null) {
            // Cannot create Greeting with specified ID value

            throw new EntityExistsException(
                    "The id attribute must be null to persist a new entity.");
        }

        Greeting savedGreeting = greetingRepository.save(greeting);


        return savedGreeting;
    }

    @Override
    public Greeting update(Greeting greeting) {

        Greeting greetingToUpdate = findOne(greeting.getId());
        if (greetingToUpdate == null) {
            // Cannot update Greeting that hasn't been persisted
            throw new NoResultException("Requested entity not found.");
        }

        greetingToUpdate.setText(greeting.getText());
        Greeting updatedGreeting = greetingRepository.save(greetingToUpdate);

        return updatedGreeting;
    }

    @Override

    public void delete(Long id) {

        greetingRepository.delete(id);

    }

}
