import java.util.*;

public class Launcher
{
    public static void main(String[] args)
    {
        Helper help = new Helper();
        DogArray dogList = new DogArray();
        EditDog Edit = new EditDog();
        help.defaultDog(dogList);
        Scanner scanner = new Scanner(System.in);
        boolean state = true;



        while(state)
        {
            int inputNum;

            System.out.print("Hi, this is Dog manager\n");
            System.out.println("1 -> show dog list");
            System.out.println("2 -> Edit dog list");
            System.out.println("0 -> Exit");
            System.out.println("Any Num -> OverRight@");

            inputNum = scanner.nextInt();
            scanner.skip("\\R?");

            switch (inputNum)
            {
                case 1 :
                    //state = true;
                    help.displayList(dogList.getDogList());
                    break;
                case 2 :
                    //state = true;
                    Edit.editDog(dogList);
                    break;
                case 0 :
                    state = false;
                    help.Exit();
                    break;
                default :
                    System.out.println();
                    System.out.println("************************");
                    System.out.println("Version :        2.03.01");
                    System.out.println("Publish Date : 12/2/2023");
                    System.out.println("Producer :   Zepeng Zhao");
                    System.out.println("************************");
                    System.out.println();
                    break;
            }
        }
    }
}
