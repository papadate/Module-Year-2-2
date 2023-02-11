import java.util.*;

public class Launcher
{
    public static void main(String[] args)
    {
        Helper help = new Helper();
        DogArray dogList = new DogArray();
        EditDog Edit = new EditDog();
        help.defaultDog(dogList.getDogList(), dogList);
        Scanner scanner = new Scanner(System.in);
        boolean state = true;



        while(state)
        {
            int inputNum = -1;

            System.out.printf("Hi, this is Dog manager\n");
            System.out.println("1 -> show dog list");
            System.out.println("2 -> Edit dog list");
            System.out.println("0 -> Exit");
            System.out.println("Any Num -> Code Test Environment");

            inputNum = scanner.nextInt();
            scanner.skip("\\R?");

            switch (inputNum)
            {
                case 1 :
                    state = true;
                    help.displayList(dogList.getDogList());
                    break;
                case 2 :
                    state = true;
                    Edit.editDog(dogList);
                    break;
                case 0 :
                    state = false;
                    //Exit();
                    break;
                default :
                    state = true;
                    //test(scanner,);
                    break;
            }
        }
    }
}
