package com.example.demo.service;

import com.example.demo.entity.Payment;
import com.example.demo.entity.SimCard;
import com.example.demo.payload.PaymentDto;
import com.example.demo.payload.Response;
import com.example.demo.repository.PaymentRepository;
import com.example.demo.repository.SimCardRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService  {

    final PaymentRepository paymentRepository;
    final SimCardRepository simCardRepository;

    public PaymentService(PaymentRepository paymentRepository,
                          SimCardRepository simCardRepository) {
        this.paymentRepository = paymentRepository;
        this.simCardRepository = simCardRepository;
    }


    public Response doTransaction(PaymentDto paymentDto) {
        Optional<SimCard> optionalSimCard = simCardRepository.findByPhoneNumber(paymentDto.getPhoneNumber());
        if(!optionalSimCard.isPresent())
            return new Response("Such phone number was not found!", false);

        double balance = optionalSimCard.get().getBalance();
        double summa = balance + paymentDto.getAmount();
        optionalSimCard.get().setBalance(summa);
        simCardRepository.save(optionalSimCard.get());

        Payment payment = new Payment();
        payment.setSimCard(optionalSimCard.get());
        payment.setAmount(paymentDto.getAmount());
        payment.setPaymentType(paymentDto.getPaymentType());
        payment.setOwner(paymentDto.getOwner());

        Payment paymentCheck = paymentRepository.save(payment);
        return new Response("Successfully completed!", true,paymentCheck);
    }
}
