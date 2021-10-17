package com.rmit.sept.requests.Services;

import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import com.rmit.sept.requests.Repositories.RequestRepository;
import com.rmit.sept.requests.model.Request;
import com.rmit.sept.requests.model.RequestType;
import com.rmit.sept.requests.model.User;
import com.rmit.sept.requests.services.RequestService;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
public class RequestsServiceTest {
    @Autowired
    private RequestService requestService;

    @MockBean
    private RequestRepository requestRepository;

    private List<Request> requests;
    private Request request1, request2;

    @BeforeEach
    public void setup() {
        request1 = new Request();
        request1.setId(1L);
        request1.setUser(new User());
        request1.setObjectId(1L);
        request1.setRequest("New Book");
        request1.setRequestType(RequestType.NEW_BOOK_LISTING);
        request2 = new Request();
        requests = new ArrayList<>();
        requests.add(request1);
        requests.add(request2);
    }

    @Test
    @DisplayName("Test findAllRequests success") // test for finding all requests
    public void testFindAllRequestsSuccess() throws Exception {
        given(requestRepository.findAll()).willReturn(requests);
        Iterable<Request> allRequests = requestService.findAllRequests();
        Assert.assertNotNull(allRequests);
    }

    @Test
    @DisplayName("Test saveRequest success") // test for saving a request
    public void testSaveRequestSuccess() throws Exception {
        given(requestRepository.save(request1)).willReturn(request1);
        Request approvedRequest = requestService.saveRequest(request1);
        Assert.assertNotNull(approvedRequest);
    }

    @Test
    @DisplayName("Test saveRequest failure") // test for failing to save a request
    public void testSaveRequestFailure() throws Exception {
        given(requestRepository.save(request2)).willReturn(null);
        Request approvedRequest = requestService.saveRequest(request1);
        Assert.assertNull(approvedRequest);
    }
}