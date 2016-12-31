import processing.core.PApplet;

public class GraphDistribution extends PApplet
{  
  public static double[][] data = new double[301][2];

  public void setup()
  {    
    //Input Data
    int A = 15;
    int G = 4;
    double C = 100.0;
    double a = .95;    

    resetData();
    double expected = findExpectedScore(A, G, a);  

    size(1200, 600);
    background(0);   
    //draw cutoff line
    stroke(255,0,0);
    for (int l=0; l<2; l++)
    {
      line((float)(8*C)+l, 0, (float) (8*C)+l, 600);    
    }

    //draw expected value line
    stroke(0,255,0);
    for (int l=0; l<2; l++)
    {
      line((float)(8*expected)+l, 0, (float) (8*expected)+l, 600);
    }

  }

  public void draw()
  {
    stroke(255);    
    for (int i=0; i<300; i++)
    {
      double x = 4*i;
      double y = 600 - data[i][1]*600;

      for (int l=0; l<2; l++)
      {
        line((float)x+l, (float)y, (float)x+l, 600);
      }

    }
  }

  //--------------------------------------------------------------------------------


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

        double probability = Math.pow(a,m) * Math.pow((1-a), (A-m)) * Math.pow(.5, n) * Math.pow(.5, (G-n));       
        probability = probability* comb(A, m);
        probability = probability* comb(G, n);

        p +=  score * probability;        
        data[(int)(score*2)][1] += probability;
      }
    }

    return p;
  }


  public static void main(String[] args)
  {
    PApplet.main(new String[] {"--present", "AMCFormula2"});
  }

}

