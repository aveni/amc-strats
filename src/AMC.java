/*
 * This program creates and scores AMC-style tests according to
 * specified parameters. It also runs Monte Carlo simulations 
 * to return the average score of several tests, and the percentage of 
 * tests that meet a qualifying score.
 * Author: Abhinav Venigalla
 */


import java.util.ArrayList;
import java.util.Arrays;

public class AMC
{
  
  //counts the number of correct answers in a test
  public static int numCorrect(int[] test)
  {
    int tot=0;
    for (int i: test)
    {
      if (i==1) tot++;
    }
    return tot;
  }
  
  //counts the number of incorrect answers in a test
  public static int numWrong(int[] test)
  {
    int tot=0;
    for (int i: test)
    {
      if (i==-1) tot++;
    }
    return tot;
  }
  
  //counts the number of blank answers in a test
  public static int numBlank(int[] test)
  {
    int tot=0;
    for (int i: test)
    {
      if (i==0) tot++;
    }
    return tot;
  }
  
  //scores an AMC-style test according to the function:
  //score = 6*(numCorrect) + 1.5*(numBlank) + 0*(numWrong)
  public static double scoreTest(int[] test)
  {
    double tot=0.0;   
    for (int i: test)
    {
      if (i==1) tot+=6;
      if (i==0) tot+=1.5;
    }    
    return tot;
  }
  
  //returns whether a test score is above a desired cutoff
  public static boolean qualified(double cutoff, double score)
  {
    return score>=cutoff;
  }
  
  /*
   * Creates a "test" of 25 questions, implemented as an int array with 25 elements. (1=correct, -1=wrong, 0=blank)
   * Based on accuracy and numAnswered, the first numAnswered elements in the
   * array are randomly set to either 1 or -1 (right or wrong)
   * The next numGuesses elements in the array are set to 1 or -1 with a 1/5 probability of being correct, 4/5 probability of being incorrect. 
   * Any remaining elements in the array are left set to 0 (blank)
   */
  public static int[] createTest(int numAnswered, int numGuesses, double accuracy)
  {
    int[] test = new int[25];
    
    for (int i=0; i<numAnswered; i++)
    {
      if (Math.random()<=accuracy) test[i] = 1;
      else test[i] = -1;
    }
    
    for (int i=numAnswered; i<numAnswered+numGuesses; i++)
    {
      if (Math.random()<=.2) test[i] = 1;
      else test[i] = -1;
    }
    
    return test;
  }
  
  /*
   * Runs a number of Monte-Carlo simulations to calculate the probability of reaching a cutoff.
   * Returns a data set of double arrays, where each double array holds a possible score on the test,
   * and the number of times that score was reached. 
   */
  public static ArrayList<double[]> runSimulation(int numTrials, int numAnswered, int numGuesses, double accuracy)
  {
    ArrayList<double[]> data = new ArrayList<double[]>();
    ArrayList<Double> possibleScores= new ArrayList<Double>();
    int[] test = new int[25];
    double score = 0.0;
    
    for (int i=0; i<numTrials; i++)
    {
      test = createTest(numAnswered, numGuesses, accuracy);
      score = scoreTest(test);
      if (possibleScores.contains(score))
      {
        for (double[] a: data)
        {
          if (a[0] == score)
          {
            a[1]++; break;
          }
        }
      }
      
      else
      {
        possibleScores.add(score);
        data.add(new double[]{score,1});
      }
    }
    
    return data;
  }
  
  public static void guesses2(int numTrials, int numAnswered, int numGuessed, double cutoff)
  {
    
    for (int i=85; i<=95; i++)
    {
      ArrayList<double[]> data = runSimulation(numTrials, numAnswered, numGuessed, i/100.0);
      data2(data, cutoff);
    }
  }
  
  public static void data2(ArrayList<double[]> data, double cutoff)
  {
    double tot=0;
    int numWins = 0;
    int numTests = 0;
    for (double[] a: data)
    {
      tot += (a[0] * a[1]);
      numTests += a[1];
      if (qualified(cutoff, a[0])) numWins+=(int)a[1];
    }
    System.out.println((double)numWins/numTests);
  }
  
  /*
   * Analyzes the data set returned by runSimulation.
   * Calculates the expected value of the test score, as well as the percentage of simulated tests 
   * that achieved a score over the cutoff. Prints out the results. 
   */
  public static void analyzeData(ArrayList<double[]> data, double cutoff)
  {
    double tot=0;
    int numWins = 0;
    int numTests = 0;
    for (double[] a: data)
    {
      tot += (a[0] * a[1]);
      numTests += a[1];
      if (qualified(cutoff, a[0])) numWins+=(int)a[1];
    }
    
    System.out.println("Average Score: " + tot/numTests);
    System.out.println("Percent Qualified: " + 100.0*numWins/numTests + "%");
  }
  
  //Runs several test simulations (with increasing values of numGuessed) and analyzes each of them.
  public static void possibleGuesses(int numTrials, int numAnswered, double accuracy, double cutoff)
  {
    System.out.println("NumTrials: " + numTrials);
    System.out.println("NumAnswered: " + numAnswered);
    System.out.println("Accuracy: " + accuracy*100 + "%");
    System.out.println("Cutoff Score: " + cutoff + "\n");
    
    for (int i=0; i<=25-numAnswered; i++)
    {
      ArrayList<double[]> data = runSimulation(numTrials, numAnswered, i, accuracy);
      System.out.println("NumGuessed: " + i);
      analyzeData(data, cutoff);
      System.out.println("---------------------------------------\n");
    }
  }
  
  public static void main(String[] args)
  {


    //Input Data
    int numTrials = 100000;
    int numAnswered = 15;
    //int numGuessed = 0;
    double accuracy = .90;
    double cutoff = 100.0;

    possibleGuesses(numTrials, numAnswered, accuracy, cutoff);
    //guesses2(numTrials, numAnswered, 0, cutoff);

    
    /*
    int[] test = createTest(15, 2, .96);    
    System.out.println(Arrays.toString(test));
    System.out.println("NumCorrect = " + numCorrect(test));
    System.out.println("NumWrong = " + numWrong(test));
    System.out.println("NumBlank = " + numBlank(test));
    System.out.println("Score = " + scoreTest(test));
    System.out.println("Qualified for AIME? " + qualified(100, scoreTest(test)));
*/

    
/*
    ArrayList<double[]> data = runSimulation(numTrials, numAnswered, numGuessed, accuracy);
    for (double[] a: data)
    {
      System.out.println(Arrays.toString(a));
    }
    
    System.out.println("\nNumTrials: " + numTrials);
    System.out.println("NumAnswered: " + numAnswered);
    System.out.println("NumGuessed: " + numGuessed);
    System.out.println("Accuracy: " + accuracy*100 + "%");
    System.out.println("Cutoff Score: " + cutoff + "\n");
    analyzeData(data, cutoff);
*/
    
  }

}
