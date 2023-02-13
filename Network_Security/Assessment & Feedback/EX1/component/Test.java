import java.*;
import java.io.FileInputStream;

public class Test
{
    public static void main(String[] args)
    {
        String fileAddr = "./test_source/test1.txt";
        try
        {
            FileInputStream inputStream = new FileInputStream(fileAddr);
            boolean state = true;
            byte[] iv = new byte[16];

            byte[] content = new byte[16];
            int counter = 0;
            int length = -1;

            length = inputStream.read(iv);
            System.out.println("iv length is :" + length);
            while (state)
            {
                length = inputStream.read(content);
                if (length != -1)
                {
                    System.out.println("Success");
                    System.out.println("length of content is :" + length);
                }
                else
                {
                    System.out.println("File End");
                    state = false;
                }
                counter++;
            }
            inputStream.close();
        } catch (Exception e)
        {
            System.out.println(e);
        }

    }
}
