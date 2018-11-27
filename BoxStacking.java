import java.util.Arrays;
import java.util.Random;

public class BoxStacking {
	/* Representation of a box */
	static class Box implements Comparable<Box>{ 

		// height, width, depth 
		int height, width, depth, area; 

		// for simplicity of solution, 
		// always keep w <= d 

		/*Constructor to initialize a box*/
		public Box(int height, int weight, int depth) { 
			this.height = height; 
			this.width = weight; 
			this.depth = depth; 
		} 

		// Override method
		@Override
		public int compareTo(Box o) { 
			return o.area-this.area; 
		} 
	} 
	/* Returns the height of the tallest 
    stack that can be formed with give  
    type of boxes */
	static int maxStackHeight( Box listOfBoxes[], int numberOfBoxes){ 

		// Create an array of boxes holding original box and its 2 rotation dimensions
		Box[] rotationList = new Box[numberOfBoxes*3]; 

		/* New Array of boxes is created -  
        considering all 3 possible rotations,  
        with width always greater than equal 
        to depth */
		for(int i = 0;i < numberOfBoxes;i++){ 
			Box currentBox = listOfBoxes[i]; 

			/* Original Box Dimension*/
			rotationList[3*i] = new Box(currentBox.height, // height
					Math.max(currentBox.width,currentBox.depth),  //width
					Math.min(currentBox.width,currentBox.depth)); //depth

			/* First rotation Dimension*/
			rotationList[3*i + 1] = new Box(currentBox.width, //height
					Math.max(currentBox.height,currentBox.depth),  //width
					Math.min(currentBox.height,currentBox.depth)); //depth

			/* Second rotation Dimension*/
			rotationList[3*i + 2] = new Box(currentBox.depth, //height
					Math.max(currentBox.width,currentBox.height), //width
					Math.min(currentBox.width,currentBox.height)); //depth
		} 
		/*
        System.out.println("All possible combination of boxes after rotation");
        System.out.println("length width depth");
        for (int i = 0; i <rotationList.length ; i++) {
        	System.out.println("Rotation");
          	System.out.println(rotationList[i].height + " " + rotationList[i].width + " " + rotationList[i].depth + "-->");      
        }
		 */

		/* Calculating base area of  
        each of the boxes.*/
		for(int i = 0; i < rotationList.length; i++) {
			rotationList[i].area = rotationList[i].width * rotationList[i].depth; 
		} 

		/* Sorting the Boxes on the bases  
        of Area in non Increasing order.*/
		Arrays.sort(rotationList); 

		int count = 3 * numberOfBoxes; 

		/* Initialize maximumStackHeight values for all  
        indexes. let maximumStackHeight[i] denotes Maximum 
        possible Stack Height with box i on top */

		int[] maximumStackHeight = new int[count];

		// Base case, if there is only one box on the stack, the maximumStackHeight is the box's height   
		for (int i = 0; i < count; i++ ) {
			maximumStackHeight[i] = rotationList[i].height; 
		}

		/* Computing optimized maximumStackHeight[]  
        values in bottom up manner */

		for(int i = 0; i < count; i++){ 
			maximumStackHeight[i] = 0; 
			Box box = rotationList[i]; //rotationList is now sorted in Increasing order
			int val = 0;

			for(int j = 0; j < i; j++){ //Loop runs from i = 1
				Box prevBox = rotationList[j]; 
				/*
				 * If currentBox's width and depth are strictly smaller than previous box's dimension
				 * take the 
				 */
				if(box.width < prevBox.width && box.depth < prevBox.depth){ 
					val = Math.max(val, maximumStackHeight[j]); 
				} 
			} 
			maximumStackHeight[i] = val + box.height; // maximumStackHeight[i] = maximumStackHeight[j] + box[i]. height
		} 

		int max = -1; 

		/* Pick maximum of all msh values */
		for(int i = 0; i < count; i++){ 
			max = Math.max(max, maximumStackHeight[i]); 
		} 

		return max; 
	} 

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random randomGenerator = new Random();
		//Try different number of boxes

		int numberOfBoxes =10000;

		Box[] listOfBoxes = new Box[numberOfBoxes];

		//Instantiate dimensions
		for(int i =0; i <listOfBoxes.length; i++) {

			listOfBoxes[i] = new Box(randomGenerator.nextInt(10000), //height
					randomGenerator.nextInt(10000), //weight
					randomGenerator.nextInt(10000)); //depth
		}

		long start = System.nanoTime();
		System.out.println("The maximum possible "+ 
				"height of stack is " +  
				maxStackHeight(listOfBoxes,numberOfBoxes)); 
		long finished = System.nanoTime();
		long runTime = finished - start;
		System.out.println("runtime: " + runTime);
	}
}

