package com.safetynet.SafetyNetAlert.unit.dto;

import com.safetynet.SafetyNetAlert.services.dto.DTO;
import com.safetynet.SafetyNetAlert.services.enumerations.DataEntry;
import com.safetynet.SafetyNetAlert.services.enumerations.Datatype;
import com.safetynet.SafetyNetAlert.unit.DataTest;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class DTOTest {

    private DataTest dataTest = new DataTest();;

    DTO dto= new DTO(Datatype.PERSO);;



    @Test
    public void returnCorrectDataWithGetData() throws ParseException {
        dto.dataTypeContent = dataTest.getAllPersonsData();;

        //ACT
        ArrayList firstname = dto.getData(DataEntry.FNAME);
        ArrayList lastname = dto.getData(DataEntry.LNAME);
        ArrayList address = dto.getData(DataEntry.ADDRESS);
        ArrayList city = dto.getData(DataEntry.CITY);
        ArrayList zip = dto.getData(DataEntry.ZIP);
        ArrayList phone = dto.getData(DataEntry.PHONE);
        ArrayList email = dto.getData(DataEntry.EMAIL);

        //ASSERT
        for(int i = 0; i<firstname.size();i++){
            assertEquals(dataTest.getPersonsFirstNameList().get(i), firstname.get(i));
            assertEquals(dataTest.getPersonsLastNameList().get(i), lastname.get(i));
            assertEquals(dataTest.getPersonsAddressList().get(i), address.get(i));
            assertEquals(dataTest.getPersonsCityList().get(i), city.get(i));
            assertEquals(dataTest.getPersonsZipList().get(i), zip.get(i));
            assertEquals(dataTest.getPersonsPhoneList().get(i), phone.get(i));
            assertEquals(dataTest.getPersonsEmailList().get(i), email.get(i));
        }

        assertEquals(4, dto.dataTypeContent.size());
        assertEquals(7, dto.dataTypeContent.get(0).size());
    }

    @Test
    public void returnCorrectAge() throws ParseException {
        dto.dataTypeContent = dataTest.getAllMedrecData();;

        //ACT
        ArrayList ages = dto.getData(DataEntry.AGE);

        //ASSERT
        for(int i = 0; i<ages.size();i++){
            assertEquals(2, ages.get(i).toString().length());
        }

    }


}