import java.util.Arrays;

public class AMCFormula2
{  
  public static double[][] data = new double[301][2];
  
  public static void resetData()
  {
    for (int i=0; i<=300; i++)
    {
      data[i][0] = i/2.0;
      data[i][1] = 0.0;
    }  
  }
  
  public static double factorial(int n)
  {
    long tot = 1;
    for (int i=2; i<=n; i++)
    {
      tot = tot*=i;
    }
    return tot;
  }
  
  public static double comb(int n, int r)
  {
    if (r > (n/2)) r = n-r;
    
    double tot = 1;
    for (int i=0; i<r; i++) tot*= (n-i);
    return tot/factorial(r);
  }
 
  public static double findExpectedScore(int A, int G, double a)
  {
    double p = 0;
    
    for (int m=0; m<=A; m++)
    {
      for (int n=0; n<=G; n++)
      {
        double score = (6*(m+n) + 1.5*(25-A-G));      
        
        double probability = Math.pow(a,m) * Math.pow((1-a), (A-m)) * Math.pow(.2, n) * Math.pow(.8, (G-n));       
        probability = probability* comb(A, m);
        probability = probability* comb(G, n);
        
        p +=  score * probability;        
        data[(int)(score*2)][1] += probability;
      }
    }

    return p;
  }
  
  public static double findProbability(double C)
  {
    double tot = 0.0;
    for (int i=0; i<=300; i++)
    {
      if (data[i][0] >= C) tot+=data[i][1];
    }    
    return tot;
  }
  
  public static void calculateProbability(int A, int G, double C, double a)
  {
    resetData();
    System.out.println("Expected Score: " + findExpectedScore(A, G, a));
    System.out.println("P: "+ 100*findProbability(C) + " %"); 
    
    System.out.print(Arrays.deepToString(data));    
  }
  
  public static void main(String[] args)
  {
    //Input Data
    int A = 15;
    int G = 4;
    double C = 100.0;
    double a = .95;    
    calculateProbability(A, G, C, a);  
  }

}