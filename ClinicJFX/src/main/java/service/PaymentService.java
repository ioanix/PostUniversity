package service;

import domain.*;
import repository.IRepository;

import java.security.KeyException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class PaymentService {

    private IRepository<Payment> paymentIRepository;
    private IRepository<Doctor> doctorIRepository;
    private IRepository<Patient> patientIRepository;
    private ClinicValidator clinicValidator;
    private UndoRedoService undoRedoService;


    public PaymentService(IRepository<Payment> paymentsIRepository, IRepository<Doctor> doctorIRepository, IRepository<Patient> patientIRepository, ClinicValidator clinicValidator, UndoRedoService undoRedoService) {

        this.paymentIRepository = paymentsIRepository;
        this.doctorIRepository = doctorIRepository;
        this.patientIRepository = patientIRepository;
        this.clinicValidator = clinicValidator;
        this.undoRedoService = undoRedoService;
    }

    public void addPaymentService(int patientId, int doctorId, int patientCardNumber, String date, String time, double price) throws KeyException {

//        if (this.patientIRepository.read(patientId) == null) {
//
//            throw new IllegalArgumentException("The patient id does not exist");
//        }
//
//        if (this.doctorIRepository.read(doctorId) == null) {
//
//            throw new IllegalArgumentException("The doctor id does not exist");
//        }

        Payment payment = new Payment(patientId, doctorId, patientCardNumber, date, time, price);

        for(Payment p : paymentIRepository.readAll()) {

            if(p.getPatientId() == payment.getPatientId() && p.getPatientCardNumber() != payment.getPatientCardNumber()) {

                throw new IllegalArgumentException("You have entered the wrong card number for this patient");
            }
        }

        this.clinicValidator.validatePayment(payment);

        this.paymentIRepository.create(payment);

        this.undoRedoService.addToUndo(new AddOperation<>(paymentIRepository, payment));

    }


    public List<Payment> getAll() {

        return this.paymentIRepository.readAll();
    }


    public void deletePaymentService(int id) throws KeyException {

        this.undoRedoService.addToUndo(new DeleteOperation<>(paymentIRepository, paymentIRepository.read(id)));

        this.paymentIRepository.delete(id);
    }


    public List<PatientsForDoctorViewModel> showPatientsForDoctor() {

        Map<Doctor, List<Patient>> patientsForDoctor = new HashMap<>();

        for (Payment payment : paymentIRepository.readAll()) {

            Doctor doctor = doctorIRepository.read(payment.getDoctorId());
            Patient patient = patientIRepository.read(payment.getPatientId());

            if (!patientsForDoctor.containsKey(doctor)) {

                List<Patient> patientsList = new ArrayList<>();
                patientsList.add(patient);

                patientsForDoctor.put(doctor, patientsList);

            } else {

                patientsForDoctor.get(doctor).add(patient);
            }
        }

        Reason reason;
        List<PatientsForDoctorViewModel> result = new ArrayList<>();

        for (Doctor doctor : patientsForDoctor.keySet()) {

            int time = 0;
            int price = 0;

            List<Patient> patients = patientsForDoctor.get(doctor);

            for (Patient patient : patients) {

                String choice = patient.getConsultationReason();

                switch (choice) {

                    case "consultation":
                        reason = new Consultation();
                        time += reason.getVisitTime();
                        price += reason.getVisitPrice();
                        break;

                    case "prescriptions":
                        reason = new Prescriptions();
                        time += reason.getVisitTime();
                        price += reason.getVisitPrice();
                        break;

                    case "treatment":
                        reason = new Treatment();
                        time += reason.getVisitTime();
                        price += reason.getVisitPrice();
                        break;
                }
            }

            PatientsForDoctorViewModel patientsForDoctorViewModel = new PatientsForDoctorViewModel(doctor.getId(), doctor.getFirstName(), doctor.getLastName(), patients.size(), time, price);
            result.add(patientsForDoctorViewModel);

        }

        result.sort((r1, r2) -> {

            if (r1.getNumberOfPatients() < r2.getNumberOfPatients()) {

                return 1;

            } else if (r1.getNumberOfPatients() > r2.getNumberOfPatients()) {

                return -1;

            } else {

                return 0;
            }

        });

        for (int i = 0; i < result.size() - 1; i++) {

            for (int j = i + 1; j < result.size(); j++) {

                PatientsForDoctorViewModel p1 = result.get(i);
                PatientsForDoctorViewModel p2 = result.get(j);

                if (result.get(i).getNumberOfPatients() == result.get(j).getNumberOfPatients()) {

                    if (p1.getDoctorId() > p2.getDoctorId()) {

                        PatientsForDoctorViewModel temp = p1;
                        p1 = p2;
                        p2 = temp;

                        result.set(i, p1);
                        result.set(j, p2);

                    }
                }
            }

        }

        return result;
    }

    public List<Payment> getPaymentsInARangeOfDays(String start, String end) throws ParseException {

        List<Payment> payments = new ArrayList<>();

        Date startDate = new SimpleDateFormat("dd.MM.yyyy").parse(start);
        Date endDate = new SimpleDateFormat("dd.MM.yyyy").parse(end);

        for (Payment payment : paymentIRepository.readAll()) {

            Date transactionDate = new SimpleDateFormat("dd.MM.yyyy").parse(payment.getDate());

            if (!(transactionDate.before(startDate) || transactionDate.after(endDate))) {

                payments.add(payment);
            }
        }

        return payments;
    }

    public void deletePaymentsInARangeOfDays(String start, String end) throws ParseException, KeyException {

        Date startDate = new SimpleDateFormat("dd.MM.yyyy").parse(start);
        Date endDate = new SimpleDateFormat("dd.MM.yyyy").parse(end);

        for (Payment payment : paymentIRepository.readAll()) {

            Date transactionDate = new SimpleDateFormat("dd.MM.yyyy").parse(payment.getDate());

            if (!(transactionDate.before(startDate) || transactionDate.after(endDate))) {

                paymentIRepository.delete(payment.getId());
            }
        }

    }

}
