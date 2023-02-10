import java.io.*;
import java.util.*;

public class Dog {
    String name;
    String breed;
    int age;
    public Dog() {}
    private Dog(String name)
    {
        this.name = name;
    }
    void eat()
    {

    }
    void run()
    {

    }
    void sleep()
    {
        System.out.printf("%s is sleeping!\n", name);
    }
    void resetName(String givenName)
    {
        this.name = givenName;
    }
    String getName()
    {
        return this.name;
    }

// Show Dog List Block
    public static void showDogList(Dog[] list) {
        int length = getLength(list);
        if (length == 0) {
            System.out.println("No exit dog in system!");
        } else {
            int counterDogList = 0;
            while (counterDogList < length) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("Name is : " + list[counterDogList].name);
                System.out.println("Age is : " + list[counterDogList].age);
                System.out.println("Breed is : " + list[counterDogList].breed);
                if (counterDogList == length - 1) {
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println();
                }
                counterDogList++;
            }
        }

    }

// Edit Dog Block
    public static void editDog()
    {

    }


// Test Case
    public static void test(Scanner scanner, Dog[] list)
    {
        int input = -1;
        System.out.println("1 -> test case 1 : getLength (DogList)");
        input = scanner.nextInt();
        if (input == 1)
        {
            System.out.println("Length is : " + getLength(list));
        }
    }


// Public Function Block
    public static int getLength(Dog[] list)
    {
        int count = 0;
        while (count < list.length)
        {
            if (list[count] == null)
                break;
            else
                count++;
        }
        return count;
    }

    public static Dog createDog(String name)
    {
        Dog dog = new Dog(name);
        return dog;
    }

    public static void defaultDog(Dog[] dogList)
    {
        String[] name = {"baby", "fuckSky", "阿瓜"};
        String[] breed = {"贵宾犬", "泰迪犬", "阿呆"};
        int[] age = {2,6,3};
        for (int i = 0; i < 3; i++)
        {
            dogList[i] = createDog(name[i]);
            dogList[i].breed = breed[i];
            dogList[i].age = age[i];
        }
    }

// Main Block
    public static void main(String[] args)
    {
        Dog[] dogList = new Dog[10];
        defaultDog(dogList);
        boolean state = true;
        while(state)
        {
            state = false;
            Scanner scanner = new Scanner(System.in);
            int inputNum = -1;

            System.out.printf("Hi, this is Dog manager\n");
            System.out.println("1 -> show dog list");
            System.out.println("2 -> Edit dog list");
            System.out.println("Any Num -> Code Test Environment");

            inputNum = scanner.nextInt();
            if (inputNum == 1)
            {
                state = true;
                showDogList(dogList);
            }
            else if (inputNum == 2)
            {

            }
            else
            {
                test(scanner, dogList);
            }
        }
    }
}
