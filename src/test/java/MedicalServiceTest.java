import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.repository.PatientInfoRepository;
import ru.netology.patient.service.alert.SendAlertService;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;

public class MedicalServiceTest {

    @Test
    public void sendWarningBlood(){
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("123")).thenReturn(new PatientInfo("123","Ivan","Ivanov",null, new HealthInfo(new BigDecimal(35),new BloodPressure(35,35))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository,sendAlertService);
        medicalService.checkBloodPressure("123",new BloodPressure(77,77));


        Mockito.verify(sendAlertService).send(argumentCaptor.capture());

        Assertions.assertEquals("Warning, patient with id: 123, need help",argumentCaptor.getValue());
    }
    @Test
    public void noSendWarningBlood(){
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("123")).thenReturn(new PatientInfo("123","Ivan","Ivanov",null, new HealthInfo(new BigDecimal(35),new BloodPressure(35,35))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);


        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository,sendAlertService);
        medicalService.checkBloodPressure("123",new BloodPressure(35,35));

        Mockito.verify(sendAlertService, Mockito.times(0)).send("Warning, patient with id: 123, need help");
    }
    @Test
    public void sendWarningTemperature(){
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("123")).thenReturn(new PatientInfo("123","Ivan","Ivanov",null, new HealthInfo(new BigDecimal(38.5),new BloodPressure(36,35))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository,sendAlertService);
        medicalService.checkTemperature("123",new BigDecimal(36.8));

        Mockito.verify(sendAlertService).send(argumentCaptor.capture());

        Assertions.assertEquals("Warning, patient with id: 123, need help",argumentCaptor.getValue());
    }

    @Test
    public void noSendWarningTemperature(){
        PatientInfoRepository patientInfoRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoRepository.getById("123")).thenReturn(new PatientInfo("123","Ivan","Ivanov",null, new HealthInfo(new BigDecimal(36.5),new BloodPressure(35,35))));

        SendAlertService sendAlertService = Mockito.mock(SendAlertService.class);


        MedicalServiceImpl medicalService = new MedicalServiceImpl(patientInfoRepository,sendAlertService);
        medicalService.checkTemperature("123",new BigDecimal(36.8));

        Mockito.verify(sendAlertService, Mockito.times(0)).send("Warning, patient with id: 123, need help");
    }
}
