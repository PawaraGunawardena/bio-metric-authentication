package logicComponent;

public class Calculation {
	
	
	public static void main(String [] args){
		long[] itemArray = {1000,2000,2500,1325,4500};
		long average2 = returnAverage(itemArray);
		long variance2  =returnVariance(itemArray,average2);
		long standardDeviation = standardDeviation(variance2);
		
		System.out.println(average2+" "+variance2+" "+standardDeviation);
	}
	public Calculation(){
		
	} 
	public static long returnAverage(long[] arrayItems){
		long Total =0;
		long average;
		for(int x=0 ; x < arrayItems.length ; x++){
			Total += arrayItems[x];
			System.out.println(arrayItems[x]);
		}
		average = Total/arrayItems.length;
		
		return average;
	}
	
	public static long returnVariance(long[] arrayItems, long average){
		long variance;
		long squardTotal = 0;
		for(int x=0 ; x < arrayItems.length ; x++){
			squardTotal += arrayItems[x]*arrayItems[x];
		}
		long squardAverage = squardTotal/arrayItems.length;
		variance = squardAverage - (average*average);
		
		return variance;
	}
	public static long standardDeviation(long variance){
		long standardDeviation;
		standardDeviation= (long) Math.sqrt(variance);
		return standardDeviation;
	}
}
