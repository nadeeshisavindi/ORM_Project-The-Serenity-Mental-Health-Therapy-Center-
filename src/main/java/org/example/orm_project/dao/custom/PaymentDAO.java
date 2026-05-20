package org.example.orm_project.dao.custom;

import org.example.orm_project.dao.CrudDAO;
import org.example.orm_project.entity.Payment;

import java.util.List;

public interface PaymentDAO extends CrudDAO<Payment> {


    List<Payment> getPendingPayments() throws Exception;


    List<Payment> getPaymentsByRegistrationId(String registrationId) throws Exception;
}