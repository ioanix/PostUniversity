package service;

import domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.DBConnectionDoctorRepository;
import repository.DBConnectionPatientRepository;
import repository.DBConnectionPaymentRepository;
import repository.IRepository;

import java.security.KeyException;
import java.text.ParseException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentServiceTest {

    private IRepository<Payment> paymentIRepository;
    private IRepository<Doctor> doctorIRepository;
    private IRepository<Patient> patientIRepository;
    private ClinicValidator clinicValidator;
    private UndoRedoService undoRedoService;
    private PaymentService paymentService;

    @BeforeEach
    public void setup() {

        paymentIRepository = new DBConnectionPaymentRepository();
        doctorIRepository = new DBConnectionDoctorRepository();
        patientIRepository = new DBConnectionPatientRepository();
        clinicValidator = new ClinicValidator();
        undoRedoService = new UndoRedoService();
        paymentService = new PaymentService(paymentIRepository, doctorIRepository, patientIRepository, clinicValidator, undoRedoService);

    }

    @Test
    void addingAValidPayment_should_saveThatPaymentToRepository() throws KeyException {

        Payment payment = new Payment(9, 21, 1234, "13.02.2020", "13", 50);

        paymentService.addPaymentService(payment.getPatientId(),
                                         payment.getDoctorId(),
                                         payment.getPatientCardNumber(),
                                         payment.getDate(),
                                         payment.getTime(),
                                         payment.getPrice());

        assertEquals(1, paymentIRepository.readAll().size());
    }

//    @Test
//    void addingAPaymentForAPatientThatIsNotInRepository_should_raiseIllegalArgumentException() throws KeyException {
//
//        Payment payment = new Payment(6, 1, 1234, "13.02.2020", "13", 50);
//
//        try {
//
//            paymentService.addPaymentService(payment.getPatientId(),
//                    payment.getDoctorId(),
//                    payment.getPatientCardNumber(),
//                    payment.getDate(),
//                    payment.getTime(),
//                    payment.getPrice());
//
//            fail("Exception not thrown");
//
//        } catch(IllegalArgumentException e) {
//
//
//        }
//    }

//    @Test
//    void addingAPaymentForADoctorThatIsNotInRepository_should_raiseIllegalArgumentException() throws KeyException {
//
//        Payment payment = new Payment(1, 6, 1234, "13.02.2020", "13", 50);
//
//        try {
//
//            paymentService.addPaymentService(payment.getPatientId(),
//                    payment.getDoctorId(),
//                    payment.getPatientCardNumber(),
//                    payment.getDate(),
//                    payment.getTime(),
//                    payment.getPrice());
//
//            fail("Exception not thrown");
//
//        } catch(IllegalArgumentException e) {
//
//
//        }
//    }

    @Test
    void getAll() throws KeyException {

        Payment payment = new Payment(10, 22, 1286, "14.02.2020", "15", 50);
        Payment payment1 = new Payment(11, 22, 1289, "14.02.2020", "15", 50);

        paymentIRepository.create(payment);
        paymentIRepository.create(payment1);

        List<Payment> payments = paymentService.getAll();

        assertEquals(6, payments.size());
    }

    @Test
    void deletePaymentService() throws KeyException {

        paymentService.deletePaymentService(13);

        assertEquals(4, paymentIRepository.readAll().size());
    }

    @Test
    void showPatientsForDoctor() throws KeyException {

        List<PatientsForDoctorViewModel> actualList = paymentService.showPatientsForDoctor();

        assertEquals(5, actualList.get(0).getNumberOfPatients());

    }

    @Test
    void getPaymentsInARangeOfDays() throws KeyException, ParseException {

        Payment payment = new Payment(9, 21, 5555, "12.02.2020", "15", 50);

        paymentIRepository.create(payment);

        List<Payment> actualList = paymentService.getPaymentsInARangeOfDays("12.02.2020", "13.02.2020");

        assertEquals(2, actualList.size());
    }

    @Test
    void deletePaymentsInARangeOfDays() throws KeyException, ParseException {

        Payment payment = new Payment(10, 21, 5555, "10.02.2020", "15", 50);
        Payment payment1 = new Payment(11, 21, 5555, "11.02.2020", "15", 50);

        paymentIRepository.create(payment);
        paymentIRepository.create(payment1);

        paymentService.deletePaymentsInARangeOfDays("10.02.2020", "11.02.2020");

        assertEquals(7, paymentIRepository.readAll().size());

    }
}