import java.util.*;
public class Helper
{
    public int getLength(Dog[] List)
    {
        int count = 0;
        while (count < List.length)
        {
            if (List[count] == null)
                break;
            else
                count++;
        }
        return count;
    }

    public void displayList(Dog[] List)
    {
        int length = getLength(List);
        if (length == 0) {
            System.out.println("No exist dog in system!");
        }
        else
        {
            String line = "------------------------------------------------------------------------------";
            int counterDogList = 0;
            System.out.format("%64s\n", line);
            System.out.format("%16s%16s%16s%16s\n", "Number", "Name", "Age", "Breed");
            System.out.format("%64s\n", line);
            while (counterDogList < length)
            {
                String number = "No." + (counterDogList + 1);
                String Name = List[counterDogList].getName();
                String Age = "" + List[counterDogList].getAge();
                String Breed = List[counterDogList].getBreed();

                System.out.format("%16s%16s%16s%16s\n", number, Name, Age, Breed);

                if (counterDogList == length - 1)
                {
                    System.out.format("%64s\n", line);
                    System.out.println();
                }
                counterDogList++;
            }
        }
    }
    public void defaultDog(Dog[] dog1List, DogArray D)
    {
        Dog[] temp = new Dog[10];
        String[] name = {"baby", "fuckSky Zhao", "阿瓜", "Tom", "Ag", "S*t"};
        String[] breed = {"贵宾犬", "泰迪犬", "阿呆", "f", "???", "SSR"};
        int[] age = {2,6,3,7,3,1};
        for (int i = 0; i < 6; i++)
        {
            temp[i] = createDog();
            temp[i].setName(name[i]);
            temp[i].setAge(age[i]);
            temp[i].setBreed(breed[i]);
        }
        D.setDogList(temp);
    }
    public static Dog createDog()
    {
        Dog temp = new Dog();
        return temp;
    }
    public void Exit()
    {
        System.out.println("GoodBye!~~");
    }

    public boolean confirm(Scanner scanner)
    {
        System.out.println("Do you confirm the information is correct? [y/n]");
        String confirm = scanner.nextLine();
        if (confirm.equals("n"))
        {
            return false;
        }
        else if (confirm.equals("y"))
        {
            return true;
        }
        else
        {
            System.out.println("Invalid Input!");
            boolean state = confirm(scanner);
            return state;
        }
    }

    public void backward()
    {
        System.out.println("\n----Returning to BACKWARD service----\n");
    }
}
