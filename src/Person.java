import java.security.PublicKey;

public class Person {
    static String name;
    int age;
    public Person(){}
    public Person(String name,int age){
        this.name=name;
        this.age=age;
    }
    public static void printPerson(){
    }
    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }
}
