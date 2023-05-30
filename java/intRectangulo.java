import java.util.concurrent.Callable;
import java.util.Scanner;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.Future;
import java.util.concurrent.*;
import java.util.ArrayList;
import java.util.List;

public class intRectangulo implements Callable<Double>
{
    Double a0, b0;
    public intRectangulo(double a, double b)
    {
        a0 = a;
        b0 = b;
    }

    @Override
    public Double call() throws Exception 
    {
        return (b0 - a0) * (3 * a0* a0 + 2);
    }
    public static void main(String[] args) throws Exception
    {
        Scanner entrada = new Scanner(System.in);
        double a, b, nIntervalos;
        System.out.println("Introduce numero de subintervalos: ");
        nIntervalos = entrada.nextDouble();
        System.out.println("Introduce a: ");
        a = entrada.nextDouble();
        System.out.println("Introduce b: ");
        b = entrada.nextDouble();
        double tamIntervalo = (double)(b - a)/nIntervalos;
        ExecutorService executor = (ThreadPoolExecutor) Executors.newFixedThreadPool((int)nIntervalos);
        List<Future<Double>> resultList = new ArrayList<>();
        for (int i = 0; i < nIntervalos-1; i++) 
        {
            Future<Double> result = executor.submit(new intRectangulo(a+tamIntervalo*i, a+tamIntervalo*(i+1)));
            resultList.add(result);
        }
        executor.awaitTermination(5, TimeUnit.SECONDS);

        double number = 0;
        for (int i = 0; i < resultList.size(); i++) 
        {
            Future<Double> result = resultList.get(i);
            try {
                number += result.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Total de la integral: " + number);
        entrada.close();
        executor.shutdown();
    }
}