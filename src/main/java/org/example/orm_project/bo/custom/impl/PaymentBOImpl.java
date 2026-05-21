package org.example.orm_project.bo.custom.impl;

import org.example.orm_project.bo.custom.PaymentBO;
import org.example.orm_project.dao.DAOFactory;
import org.example.orm_project.dao.DAOTypes;
import org.example.orm_project.dao.custom.PaymentDAO;
import org.example.orm_project.entity.Payment;

import java.util.List;

public class PaymentBOImpl implements PaymentBO {

    private final PaymentDAO paymentDAO =
            DAOFactory.getInstance().getDAO(DAOTypes.PAYMENT);

    @Override
    public boolean savePayment(Payment payment) throws Exception {
        return paymentDAO.save(payment);
    }

    @Override
    public boolean updatePayment(Payment payment) throws Exception {
        return paymentDAO.update(payment);
    }

    @Override
    public boolean deletePayment(String paymentId) throws Exception {
        return paymentDAO.delete(paymentId);
    }

    @Override
    public Payment searchPayment(String paymentId) throws Exception {
        return paymentDAO.search(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() throws Exception {
        return paymentDAO.getAll();
    }

    @Override
    public String generateNextPaymentId() throws Exception {
        return paymentDAO.generateNextId();
    }
}