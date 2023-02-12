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
            String line = "-----------------------------------------------------------------";
            int counterDogList = 0;
            System.out.println(line);
            System.out.format("%7s%16s%16s%20s\n", "Number", "Name", "Age", "Breed");
            System.out.println(line);
            while (counterDogList < length)
            {
                String number = "No." + (counterDogList + 1);
                String Name = List[counterDogList].getName();
                String Age = "" + List[counterDogList].getAge();
                String Breed = List[counterDogList].getBreed();

                System.out.format("%7s%16s%16s%20s\n", number, Name, Age, Breed);

                if (counterDogList == length - 1)
                {
                    System.out.println(line);
                    System.out.println();
                }
                counterDogList++;
            }
        }
    }

    public void displayDog(DogArray D, int givenNum)
    {
        int index = givenNum - 1;
        Dog temp = D.getDogList()[index];

        String line = "-----------------------------------------------------------------";
        System.out.println(line);
        System.out.format("%7s%16s%16s%20s\n", "Number", "Name", "Age", "Breed");

        String number = "No." + givenNum;
        String Name = temp.getName();
        String Age = "" + temp.getAge();
        String Breed = temp.getBreed();
        System.out.format("%7s%16s%16s%20s\n", number, Name, Age, Breed);

        System.out.println(line);
        System.out.println();
    }

    public void defaultDog(DogArray D)
    {
        Dog[] temp = new Dog[10];
        String[] name = {"dog1", "dog2", "dog3", "dog4", "dog5", "dog6"};
        String[] breed = {"Poodle", "Toy-poodle", "Shiba-Inu", "German Shepherd", "Golden Retriever", "???"};
        int[] age = {2,6,3,1,3,1};
        for (int i = 0; i < 6; i++)
        {
            temp[i] = createDog();
            temp[i].setName(name[i]);
            temp[i].setAge(age[i]);
            temp[i].setBreed(breed[i]);
        }
        D.setDogList(temp);
    }
    public Dog createDog()
    {
        return new Dog();
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
            return confirm(scanner);
        }
    }

    public void backward()
    {
        System.out.println("\n----Returning to BACKWARD service----\n");
    }
}
