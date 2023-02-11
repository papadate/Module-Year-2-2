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
            int counterDogList = 0;
            while (counterDogList < length)
            {
                System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                System.out.println("No." + (counterDogList+1));
                System.out.println("Name is : " + List[counterDogList].getName());
                System.out.println("Age is : " + List[counterDogList].getAge());
                System.out.println("Breed is : " + List[counterDogList].getBreed());
                if (counterDogList == length - 1)
                {
                    System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    System.out.println();
                }
                counterDogList++;
            }
        }
    }
    public void defaultDog(Dog[] dog1List, DogArray D)
    {
        Dog[] temp = new Dog[10];
        String[] name = {"baby", "fuckSky", "阿瓜"};
        String[] breed = {"贵宾犬", "泰迪犬", "阿呆"};
        int[] age = {2,6,3};
        for (int i = 0; i < 3; i++)
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
}
