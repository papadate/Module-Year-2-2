public class DogArray
{
    private final Dog[] DogList;
    public DogArray()
    {
        this.DogList = new Dog[10];
    }

    public Dog[] getDogList()
    {
        return DogList;
    }

    public void setDogList(Dog[] tempList)
    {
        Helper H = new Helper();
        int length_old = H.getLength(this.DogList);
        int length_new = H.getLength(tempList);
        if (length_old < length_new)
        {
            if (length_new >= 0) System.arraycopy(tempList, 0, this.DogList, 0, length_new);
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
    }
}
