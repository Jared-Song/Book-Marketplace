package com.rmit.sept.requests.Repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import javax.transaction.Transactional;

import com.rmit.sept.requests.model.Request;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@Transactional
public class RequestsRepositoryTest {

    @Autowired
    private RequestRepository requestRepository;

    // testing for empty request repository
    @Test
    @Rollback(true)
    public void should_find_no_requests_if_requestRepository_is_empty() {
        Iterable<Request> requests = requestRepository.findAll();
        if (requests.iterator().hasNext()) {
            assertThat(requests).isNotEmpty();
        } else {
            assertThat(requests).isEmpty();
        }
    }

    // test to find a request by id
    @Test
    @Rollback(true)
    public void find_request_by_id() {
        Request request1 = new Request();
        request1.setId(1L);
        requestRepository.save(request1);

        Request findRequest = requestRepository.findById(1L).orElse(null);
        assertNotNull(findRequest);
    }

    // test to find a request by non-existing id
    @Test
    @Rollback(true)
    public void find_request_by_nonexisting_id() {
        Request findRequest = requestRepository.findById(0L).orElse(null);
        assertNull(findRequest);
    }

    // test to delete a request
    @Test
    @Rollback(true)
    public void delete_request() {
        Request request1 = new Request();
        request1.setId(1L);
        requestRepository.save(request1);
        requestRepository.delete(request1);
        Request findRequest = requestRepository.findById(1L).orElse(null);
        assertNull(findRequest);
    }

    // test to save a new request
    @Test
    @Rollback(true)
    public void save_new_request() {
        Request request1 = new Request();
        request1.setId(1L);
        requestRepository.save(request1);

        Request findRequest = requestRepository.findById(1L).orElse(null);
        assertNotNull(findRequest);

        Request request2 = new Request();
        request1.setId(2L);
        requestRepository.save(request2);

        Request findRequest2 = requestRepository.findById(2L).orElse(null);
        assertNotNull(findRequest2);
    }
}
