import java.rmi.*;
import java.rmi.server.*;

public class servidor extends UnicastRemoteObject implements interfaz
{
    private float valor;
    public servidor() throws RemoteException
    {
        this.valor = 0;
    }
    public float integral(int n) throws RemoteException
    {
        float tam_intervalo = (float)1/n;
        float a = 0;
        float b = a + tam_intervalo;
        while(a + tam_intervalo <= 1)
        { 
            valor += (b-a)*Math.pow(((a+b)/2), 2);
            a = b;
            b += tam_intervalo;
        }
        
        return valor;
    }
    public void reinicio() throws RemoteException
    {
        this.valor = 0;
    }
    public static void main(String[] args) throws Exception
    {
        interfaz i = new servidor();
        Naming.bind("server", i);
        System.out.println("Servidor preparado");
    }
}