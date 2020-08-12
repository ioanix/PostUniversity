//package service;
//
//import domain.ClinicValidator;
//import domain.Doctor;
//import domain.Entity;
//import domain.UndoableRedoableOperation;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import repository.IRepository;
//import repository.JSONFileRepository;
//
//import java.security.KeyException;
//import java.util.Stack;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//class UndoRedoServiceTest {
//
//    private UndoRedoService undoRedoService;
//    private IRepository<Doctor> doctorIRepository;
//    private ClinicValidator clinicValidator;
//    private DoctorService doctorService;
//    private Stack<UndoableRedoableOperation<? extends Entity>> undoStack;
//    private Stack<UndoableRedoableOperation> redoStack;
//
//    @BeforeEach
//    public void setup() {
//
//        doctorIRepository = new JSONFileRepository<>("doctors_test.txt", Doctor.class);
//        clinicValidator = new ClinicValidator();
//        doctorService = new DoctorService(doctorIRepository, clinicValidator, undoRedoService);
//        undoRedoService = new UndoRedoService();
//        undoStack = undoRedoService.getUndoStack();
//        redoStack = undoRedoService.getRedoStack();
//    }
//
//    @Test
//    void undo() throws KeyException {
//
//        Doctor doctor = new Doctor("dd4", "d4", 32);
//
//        doctorIRepository.create(doctor);
////        doctorService.addDoctorService(doctor.getFirstName(), doctor.getLastName(), doctor.getAge());
//
//        undoRedoService.undo();
//
//        assertEquals(0, undoStack.size());
//        //assertEquals(1, redoStack.size());
//
//    }
//
//    @Test
//    void redo() {
//    }
//}