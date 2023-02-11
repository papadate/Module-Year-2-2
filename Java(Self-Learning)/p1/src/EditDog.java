import java.util.Scanner;

public class EditDog
{
    public void editDog(DogArray D)
    {
        Helper H = new Helper();
        Scanner scanner = new Scanner(System.in);
        boolean state = true;
        int editInput = -1;

        while (state)
        {
            System.out.println("1 -> Add new Dog in List (Note: Max 10 Dogs for recent service!)");
            System.out.println("2 -> Edit specific Dog in the Dog List");
            System.out.println("3 -> Delete exist Dog in the Dog List");
            System.out.println("4 -> Display recent Dog List");
            System.out.println("0 -> Backward");

            editInput = scanner.nextInt();
            scanner.skip("\\R?");

            switch (editInput)
            {
                case 1 :
                    addDog(scanner, H, D);
                    break;
                case 2 :
                    modifyDog(scanner, H, D);
                    break;
                case 3 :
                    delDog(scanner, H, D);
                    break;
                case 4 :
                    H.displayList(D.getDogList());
                    break;
                case 0 :
                    state = false;
                    H.backward();
                    break;
                default :
                    System.out.println("Unknown Input number");
                    break;
            }
        }
    }

    public static void addDog(Scanner scanner, Helper H, DogArray D)
    {
        int recentLength = H.getLength(D.getDogList());
        System.out.printf("Welcome to creation module! Now, you have %d dogs in the list\n", recentLength);
        if (recentLength == 10)
        {
            System.out.println("Sorry, capacity of list is FULL!");
            return;
        }
        else
        {
            System.out.printf("Note : You can still create %d more dogs from now on\n", 10 - recentLength);
            boolean tempBool = true;
            boolean valid = true;
            Dog[] tempList = D.getDogList();
            Dog temp = H.createDog();
            while (tempBool)
            {
                System.out.println("What's the name you want to give to your new dog?");
                String tempName = scanner.nextLine();
                //scanner.nextLine();
                System.out.println("What's the age of this dog?");
                int tempAge = scanner.nextInt();
                scanner.skip("\\R?");
                //scanner.nextLine();
                System.out.println("What's the breed of this dog?");
                String tempBreed = scanner.nextLine();

                valid = H.confirm(scanner);
                if (valid && (tempAge > 0))
                {
                    System.out.printf("Add to %d th in list\n", recentLength + 1);
                    tempBool = false;
                    temp.setName(tempName);
                    temp.setBreed(tempBreed);
                    temp.setAge(tempAge);
                    tempList[recentLength]= temp;
                    D.setDogList(tempList);
                }
                else
                {
                    if (tempAge <= 0)
                    {
                        System.out.println("Please input a valid age!");
                    }
                    System.out.println("Please, Filling your information again!");
                }
            }
            H.displayList(D.getDogList());
        }
    }

    public static void modifyDog(Scanner scanner, Helper H, DogArray D)
    {
        int numResp = -1;
        boolean state = true;
        boolean valid = true;
        while (state)
        {
            System.out.println("Welcome to Modify Service on DogList");
            System.out.println("There is the recent Dog List, please select the dog (Number) you want to modify?");
            System.out.println("Example : 1 -> SELECT The first dog in your list");
            H.displayList(D.getDogList());
            numResp = scanner.nextInt();
            scanner.skip("\\R?");
            valid = validation_Check(D, H, numResp);
            if (valid)
            {
                Editing(D, H, scanner, numResp);
            }
            else
            {
                System.out.println("Sorry, the number does not exist in your List!");
            }
            state = responseModify(scanner, true);
            if (!state)
            {
                H.backward();
            }
        }
    }

    public static void Editing(DogArray D, Helper H, Scanner scanner, int givenNum)
    {
        String newName;
        int newAge;
        String newBreed;
        boolean state = true;
        Dog[] tempList = D.getDogList();
        Dog tempDog = tempList[givenNum-1];
        while (state)
        {
            System.out.print("Name: " + tempDog.getName() + " -> " + "New Name: ");
            newName = scanner.nextLine();
            System.out.println();
            System.out.print("Age: " + tempDog.getAge() + " -> " + "New Age: ");
            newAge = scanner.nextInt();
            System.out.println();
            scanner.skip("\\R?");
            System.out.print("Breed: " + tempDog.getBreed() + " -> " + "New Breed: ");
            newBreed = scanner.nextLine();
            System.out.println();

            state = H.confirm(scanner);
            if (state && newAge > 0)
            {
                System.out.println("Editing ... ... ...");
                for (int i = 0; i < 3; i++)
                {
                    if (i == 0 && !newName.equals(""))
                    {
                        tempDog.setName(newName);
                    }
                    else if (i == 1 && !newBreed.equals(""))
                    {
                        tempDog.setBreed(newBreed);
                    }
                    else
                    {
                        tempDog.setAge(newAge);
                    }
                }
                tempList[givenNum-1] = tempDog;
                D.setDogList(tempList);
                state = false;
                System.out.println("Edit **Finish**");
            }
            else
            {
                if (newAge <= 0)
                {
                    System.out.println("Please input a valid age!");
                }
                System.out.println("Please, Filling your information again!");
            }
        }
        H.displayList(D.getDogList());
    }
    public static boolean validation_Check(DogArray D, Helper H, int givenNum)
    {
        if (H.getLength(D.getDogList()) >= givenNum)
        {
            return true;
        }
        return false;
    }
    public static boolean responseModify(Scanner scanner)
    {
        System.out.println("Do you want to continue -Modify- DogList? [y/n]");
        String responseForReuse = scanner.nextLine();
        if (responseForReuse.equals("n"))
        {
            return false;
        }
        else if (responseForReuse.equals("y"))
        {
            return true;
        }
        else
        {
            System.out.println("Invalid Input!");
            boolean state = responseModify(scanner);
            return state;
        }
    }
    public static boolean responseModify(Scanner scanner, boolean state)
    {
        System.out.println("Do you want to continue -Modify- DogList? [y/n]");
        String responseForReuse = scanner.nextLine();
        if (responseForReuse.equals("n"))
        {
            state = false;
            return state;
        }
        else if (responseForReuse.equals("y"))
        {
            return state;
        }
        else
        {
            state = responseModify(scanner, state);
            return state;
        }
    }

    public static void delDog(Scanner scanner, Helper H, DogArray D)
    {

    }
}
