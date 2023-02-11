import java.util.*;

public class Dog1 {
    Dog1[] dog1List = new Dog1[10];
    String name;
    String breed;
    int age;
    public Dog1() {}
    private Dog1(String name)
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
    public static void showDogList(Dog1[] list) {
        int length = getLength(list);
        if (length == 0) {
            System.out.println("No exit dog in system!");
        } else {
            int counterDogList = 0;
            while (counterDogList < length) {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("No." + (counterDogList+1));
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
    public void editDog(Scanner scanner, Dog1[] dog1List)
    {
        boolean state = true;
        int editInput = -1;

        while (state)
        {
            System.out.println("1 -> Add new Dog in List (Note: Max 10 Dogs for recent service!)");
            System.out.println("2 -> Edit specific Dog in the Dog List");
            System.out.println("3 -> Delete exist Dog in the Dog List");
            System.out.println("4 -> Display recent Dog List");
            System.out.println("0 -> Exit");

            editInput = scanner.nextInt();

            switch (editInput)
            {
                case 1 :
                    addDog(scanner, this.dog1List);
                    break;
                case 2 :
                    modifyDog(scanner, this.dog1List);
                    break;
                case 3 :
                    delDog();
                    break;
                case 4 :
                    showDogList(this.dog1List);
                    break;
                case 0 :
                    state = false;
                    Exit();
                    break;
                default :
                    System.out.println("Unknown Input number");
                    break;
            }
        }
    }

    public static void addDog(Scanner scanner, Dog1[] dog1List)
    {
        System.out.printf("Welcome to creation module! Now, you have %d dogs in the list\n", getLength(dog1List));
        if (getLength(dog1List) == 10)
        {
            System.out.println("Sorry, capacity of list is FULL!");
            return;
        }
        else
        {
            System.out.printf("Note : You can still create %d more dogs from now on\n", 10 - getLength(dog1List));
            boolean tempBool = true;
            while (tempBool)
            {
                System.out.println("What's the name you want to give to your new dog?");
                String tempName = scanner.nextLine();
                scanner.nextLine();
                Dog1 temp = createDog(tempName);
                System.out.println("What's the age of this dog?");
                int tempAge = scanner.nextInt();
                scanner.nextLine();
                System.out.println("What's the breed of this dog?");
                String tempBreed = scanner.nextLine();
                System.out.println("Are you sure, all the information is correct? [y/n]");
                String tempResp = scanner.next();
                if (tempResp.toLowerCase().equals("y")) {
                    tempBool = false;
                    temp.age = tempAge;
                    temp.breed = tempBreed;
                    dog1List[getLength(dog1List) + 1] = temp;
                } else {
                    System.out.println("Please, Filling your information again!");
                }
            }
        }
    }

    public static void modifyDog(Scanner scanner, Dog1[] dog1List)
    {

    }

    public static void delDog()
    {

    }

// Test Case
    public static void test(Scanner scanner, Dog1[] list)
    {
        boolean state = true;
        int testInput = -1;
        while (state)
        {
            System.out.println("1 -> test case 1 : getLength (DogList)");
            System.out.println("0 -> Exit");
            testInput = scanner.nextInt();

            switch (testInput)
            {
                case 1 :
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println("Length is : " + getLength(list));
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    break;
                case 0 :
                    System.out.println("Exit Tech Test Folder... ...");
                    state = false;
                    break;
                default :
                    System.out.println("Unknown input capture!");
                    break;
            }
        }
    }


// Public Function Block
    public static int getLength(Dog1[] list)
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

    public static Dog1 createDog(String name)
    {
        Dog1 dog1 = new Dog1(name);
        return dog1;
    }

    public static void defaultDog(Dog1[] dog1List)
    {
        String[] name = {"baby", "fuckSky", "阿瓜"};
        String[] breed = {"贵宾犬", "泰迪犬", "阿呆"};
        int[] age = {2,6,3};
        for (int i = 0; i < 3; i++)
        {
            dog1List[i] = createDog(name[i]);
            dog1List[i].breed = breed[i];
            dog1List[i].age = age[i];
        }
    }

    public static void Exit()
    {
        System.out.println("GoodBye!~~");
    }

// Main Block
    public void launcher()
    {
        //Dog[] dogList = new Dog[10];
        defaultDog(this.dog1List);
        boolean state = true;
        while(state)
        {
            state = false;
            Scanner scanner = new Scanner(System.in);
            int inputNum = -1;

            System.out.printf("Hi, this is Dog manager\n");
            System.out.println("1 -> show dog list");
            System.out.println("2 -> Edit dog list");
            System.out.println("0 -> Exit");
            System.out.println("Any Num -> Code Test Environment");

            inputNum = scanner.nextInt();

            switch (inputNum)
            {
                case 1 :
                    state = true;
                    showDogList(this.dog1List);
                    break;
                case 2 :
                    state = true;
                    editDog(scanner, this.dog1List);
                    break;
                case 0 :
                    state = false;
                    Exit();
                    break;
                default :
                    state = true;
                    test(scanner, this.dog1List);
                    break;
            }
        }
    }
}
