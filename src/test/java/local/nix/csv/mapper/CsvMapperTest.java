package local.nix.csv.mapper;
import local.nix.csv.mapper.data.PersonalData;
import local.nix.csv.mapper.mapper.CsvMapper;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CsvMapperTest {

    @Test
    public void tryToInitializeOneEntity() throws Exception{
        List<PersonalData> personalDataList = CsvMapper.getMappedObjects("data.csv");
        PersonalData firstObject = personalDataList.get(0);
        assertEquals("Mike", firstObject.getName());
        assertEquals(27, firstObject.getAge());
        assertEquals(PersonalData.Gender.MALE, firstObject.getGender());
        assertEquals("janitor", firstObject.getOccupation());


        PersonalData person2 = personalDataList.get(1);
        assertEquals("Beth", person2.getName());
        assertEquals(23, person2.getAge());
        assertEquals(PersonalData.Gender.FEMALE, person2.getGender());
        assertEquals("recruiter", person2.getOccupation());


    }
}
