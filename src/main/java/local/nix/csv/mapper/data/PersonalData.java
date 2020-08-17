package local.nix.csv.mapper.data;
import local.nix.csv.mapper.annotation.Column;

public class PersonalData {

    @Column("name")
    private String name;

    @Column("age")
    private int age;

    @Column("gender")
    private Gender gender;

    @Column("occupation")
    private String occupation;

    public enum Gender {MALE, FEMALE}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }


}
