public class Main {
    public static void main(String[] args)
    {
        System.out.println("Hello world!");
        int a = 10;
        a = a++; // a = 10, a++ 后 a = 11。 但是a只会拿到a的原值 ‘a=10’ 而不是 ‘a=11’
        System.out.println(a);
    }
}