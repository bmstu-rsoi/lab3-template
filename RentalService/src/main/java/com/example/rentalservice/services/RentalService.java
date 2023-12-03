package com.example.rentalservice.services;

import com.example.rentalservice.dao.Rental;
import com.example.rentalservice.dto.request.PaymentRequest;
import com.example.rentalservice.dto.response.*;
import com.example.rentalservice.enums.RentalStatus;
import com.example.rentalservice.exception.ResourceNotFoundException;
import com.example.rentalservice.dto.request.RentalRequest;
import com.example.rentalservice.repos.RentalRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    @Value("${gw}")
    private String gw;

    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    public List<GetRentalResponse> getUserRentals(String username) throws URISyntaxException, IOException, InterruptedException {
        List<Rental> rentals = rentalRepository.findAllByUsername(username);
        List<GetRentalResponse> rentalResponses = new ArrayList<>();
        for (Rental rental : rentals) {
            rentalResponses.add(new GetRentalResponse(rental, getPayment(rental.getPaymentUid()), getCar(rental.getCarUid())));
        }
        return rentalResponses;
    }

    public GetRentalResponse getRental(UUID rentalUid, String username) throws ResourceNotFoundException, URISyntaxException, IOException, InterruptedException {
        Rental rental = rentalRepository
                .findByRentalUidAndUsername(rentalUid, username)
                .orElseThrow(() -> new ResourceNotFoundException("Object with " + rentalUid + " with username = " + username + " not founded"));
        return new GetRentalResponse(rental, getPayment(rental.getPaymentUid()), getCar(rental.getCarUid()));
    }
    private CarShortResponse getCar(UUID carUid) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(gw + "/cars/" + carUid))
                .GET()
                .build();
        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new CarShortResponse(new ObjectMapper().readValue(httpResponse.body(), CarResponse.class));
    }

    private Integer getCarPrice(UUID carUid) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(gw + "/cars/" + carUid + "/price"))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();

        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(new ObjectMapper().readValue(httpResponse.body(), CarPriceResponse.class).getPrice());
        return new ObjectMapper().readValue(httpResponse.body(), CarPriceResponse.class).getPrice();
    }

    private PaymentResponse getPayment(UUID uuid) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(gw + "/payment/" + uuid))
                .GET()
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new ObjectMapper().readValue(httpResponse.body(), PaymentResponse.class);
    }

    private PaymentResponse createPayment(RentalRequest rentalRequest) throws URISyntaxException, IOException, InterruptedException {
        Integer price = getCarPrice(rentalRequest.getCarUid());
        long daysBetween = Duration.between(rentalRequest.getDateFrom().atStartOfDay(), rentalRequest.getDateTo().atStartOfDay()).toDays();
        Long priceAll = price * daysBetween;

        var objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(new PaymentRequest("PAID", priceAll));

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(gw + "/payment"))
                .headers("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        return new ObjectMapper().readValue(httpResponse.body(), PaymentResponse.class);
    }

    private void cancelPayment(UUID uuid, String username) throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(gw + "/payment"))
                .headers("Content-Type", "application/json", "X-User-Name", username, "paymentUid", uuid.toString())
                .PUT(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpClient client = HttpClient.newBuilder().build();
        HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public Object createRental(String username, RentalRequest rentalRequest) throws URISyntaxException, IOException, InterruptedException {
        PaymentResponse payment = createPayment(rentalRequest);

        Rental rental = new Rental();
        rental.setStatus(RentalStatus.IN_PROGRESS);
        rental.setRentalUid(UUID.randomUUID());
        rental.setCarUid(rentalRequest.getCarUid());
        rental.setUsername(username);
        rental.setPaymentUid(payment.getPaymentUid());
        rental.setDateFrom(rentalRequest.getDateFrom().atTime(0, 0));
        rental.setDateTo(rentalRequest.getDateTo().atTime(0, 0));
        rentalRepository.save(rental);
        return new RentalResponse(rental, payment);
    }

    public void changeRentalStatus(UUID rentalUid, String username, RentalStatus rentalStatus) throws ResourceNotFoundException, URISyntaxException, IOException, InterruptedException {
        Rental rental = rentalRepository
                .findByRentalUidAndUsername(rentalUid, username)
                .orElseThrow(() -> new ResourceNotFoundException("Object with " + rentalUid + " with username = " + username + " not founded"));
        rental.setStatus(rentalStatus);
        System.out.println("Rental status = " + RentalStatus.CANCELED);

        if (rentalStatus.equals(RentalStatus.CANCELED)) {
            cancelPayment(rental.getPaymentUid(), username);
        }

        rentalRepository.save(rental);
    }
}
