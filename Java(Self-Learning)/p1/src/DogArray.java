public class DogArray
{
    private Dog[] DogList;
    public DogArray()
    {
        this.DogList = new Dog[10];
    }

    public Dog[] getDogList()
    {
        return DogList;
    }

    public Dog[] setDogList(Dog[] tempList)
    {
        Helper H = new Helper();
        int length_old = H.getLength(this.DogList);
        int length_new = H.getLength(tempList);
        if (length_old < length_new)
        {
            for(int i = 0; i < length_new; i++)
            {
                this.DogList[i] = tempList[i];
            }
        }
        else
        {
            for (int i = 0; i < length_old; i++)
            {
               if (i < length_new)
               {
                   this.DogList[i] = tempList[i];
               }
               else
               {
                   this.DogList[i] = null;
               }
            }
        }
        return this.DogList;
    }
}
