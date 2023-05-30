import java.rmi.*;
import java.rmi.registry.*;
public class cliente
{
    public static void main(String[] args) throws Exception
    {
        interfaz i = (interfaz)Naming.lookup("//localhost/server");
        System.out.println(i.integral(5));
        i.reinicio();
    }
}