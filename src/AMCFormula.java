import java.util.ArrayList;


public class AMCFormula
{
  
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
    return factorial(n)/(factorial(n-r)*(factorial(r)));
  }
  
  public static double numTests(int A, int G, int m, int n)
  {
      double num = 1;
      num/= factorial(m);
      num/= factorial(n);
      num/= factorial(A+G-m-n);
      for (int i=2; i<=25; i++) num*=i;
      num/= factorial(25-A-G);
      return num;
  }

  public static double findProbability(int A, int G, double C, double a)
  {
    double p = 0;
    
    for (int m=0; m<=A; m++)
    {
      for (int n=0; n<=G; n++)
      {
        double score = (6*(m+n) + 1.5*(25-A-G));
        int S = (int)(score/C);       
        
        double probability = Math.pow(a,m) * Math.pow((1-a), (A-m)) * Math.pow(.2, n) * Math.pow(.8, (G-n));       
        double combs = comb(A, m) * (comb(G, n));
        
        p +=  S * probability * combs;
      }
    }

    return p;
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
        double combs = comb(A, m) * (comb(G, n));

        p +=  score * probability * combs;
      }
    }

    return p;
  }
  
  public static void possibleGuesses(int numAnswered, double accuracy, double cutoff)
  {
    System.out.println("NumAnswered: " + numAnswered);
    System.out.println("Accuracy: " + accuracy*100 + "%");
    System.out.println("Cutoff Score: " + cutoff + "\n");
    
    for (int i=0; i<=25-numAnswered; i++)
    {
      System.out.println("NumGuessed: " + i);
      System.out.println("P: "+ 100*findProbability(numAnswered, i, cutoff, accuracy) + " %");
      System.out.println("Expected Score: " + findExpectedScore(numAnswered, i, accuracy));
      System.out.println("---------------------------------------\n");
    }
  }
  
  public static void main(String[] args)
  {
    
    //Input Data
    int A = 15;
    int G = 0;
    double C = 100.0;
    double a = .99;
        
    possibleGuesses(A, a, C);
  }

}
