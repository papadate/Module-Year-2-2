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
            System.out.println("0 -> Exit");

            editInput = scanner.nextInt();
            scanner.skip("\\R?");

            switch (editInput)
            {
                case 1 :
                    addDog(scanner, H, D);
                    break;
                case 2 :
                    //modifyDog(scanner, this.dog1List);
                    break;
                case 3 :
                    //delDog();
                    break;
                case 4 :
                    H.displayList(D.getDogList());
                    break;
                case 0 :
                    state = false;
                    H.Exit();
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
                System.out.println("Are you sure, all the information is correct? [y/n]");
                String tempResp = scanner.next();
                if (tempResp.toLowerCase().equals("y")) {
                    System.out.printf("Add to %d th in list\n", recentLength + 1);
                    tempBool = false;
                    temp.setName(tempName);
                    temp.setBreed(tempBreed);
                    temp.setAge(tempAge);
                    tempList[recentLength]= temp;
                    D.setDogList(tempList);
                } else {
                    System.out.println("Please, Filling your information again!");
                }
            }
            H.displayList(tempList);
        }
    }
}
