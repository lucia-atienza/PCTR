/*javac -cp .:$MPJ_HOME/lib/mpj.jar innerProduct.java
mpjrun.sh -np 9 innerProduct */
import mpi.*;
import java.util.*;
public class innerProduct
{
    public static void main(String[] args) 
    {
        long ini = System.nanoTime();
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();

        int v1[] = new int[8];
        int v2[] = new int[8];
        for(int i = 0; i < 8; i++)
            v1[i]=v2[i]=i;
        int result[] = new int[1];
        int emisor = 0;
        int resultado = 0;
        if(rank == emisor)
        {
            for(int i = 1; i < size; i++)
                MPI.COMM_WORLD.Send(v1, 0, 8, MPI.INT, i, 100);
            for(int i = 1; i < size; i++)
                MPI.COMM_WORLD.Send(v2, 0, 8, MPI.INT, i, 100); 
            
            for(int i = 1; i < size; i++)
            {
                MPI.COMM_WORLD.Recv(result, 0, 1, MPI.INT, i, 100);
                resultado+=result[0];
            }
            System.out.println(resultado);
            long fin = System.nanoTime()-ini;
            System.out.println("Tiempo: " + fin/10e6);
        }
        else
        {
            int v3[] = new int[8];
            int v4[] = new int[8];
            int total[] = new int[1];
            total[0] = 0;
            MPI.COMM_WORLD.Recv(v3, 0, 8, MPI.INT, emisor, 100);
            MPI.COMM_WORLD.Recv(v4, 0, 8, MPI.INT, emisor, 100);
            for(int i = 0; i < 8; i++)
                total[0]+=v3[rank-1]*v4[i];
            MPI.COMM_WORLD.Send(total, 0, 1, MPI.INT, emisor, 100);
        }
        MPI.Finalize();
    }
}

