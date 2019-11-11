/*
Name: Ethan Zhang
Course: ICS4U1-01
Teacher: Mr. Radulovic
Date finished: November 4, 2019
Assignment: Efficiency of Sorting Algorithms

The following program simulates 3 different types of sorting algorithms:
- Bubble Sort (Method 1)
- Reverse Bubble Sort (Method 2)
- Merge Sort (Method 3)
After sorting the given list (read from a file), the program outputs the amount of time
it took for the sort to finish. The given list has a size between 2^4 and 2^24, inclusive.
*/

import java.io.File;
import java.util.Scanner;

public class IntegerSorter implements Sorter{

    private int[] list; //Used for sort methods

    public static void main(String[] args) throws Exception{
        int sortNum = 1; //CHANGE SORT METHOD HERE
        File file = new File("COPY FILE PATH HERE");
        Scanner SCAN = new Scanner(file);
        int values = 0; //Used to find size of the file
        while (SCAN.hasNextInt()){
            SCAN.nextInt(); //Keep on reading integers from the file until none are left
            values++;
        }
        int[] getList = new int[values]; //Reserve a list for values from input
        SCAN.close(); //Reset the scanner
        SCAN = new Scanner(file); //Pointer moves back to beginning
        int temp = 0;
        while (SCAN.hasNext()) getList[temp++] = SCAN.nextInt(); //Store the values
        IntegerSorter IS = new IntegerSorter(); //Used to call the methods for sorting
        IS.setList(getList); //Assign the values

        long start = System.nanoTime();
        IS.sort(sortNum);
        long end = System.nanoTime();
        double nano = 1000000000;
        System.out.println("TIME: " + (end - start) / nano + " seconds");
        //Shows the time it took to compile (in seconds)
        //System.out.println(IS.toString());
    }

    //This method is used to swap two elements, given their indices
    private void swap(int a, int b){
        int temp = list[a];
        list[a] = list[b];
        list[b] = temp;
    }

    private void sort_method1(){
        /*
        General idea of this sort method:
        For every loop, compare the consecutive elements, and place the smaller element in front of the
        larger one. After one loop, the last element is guaranteed to be the largest element. As a result,
        we only loop through the n-1 elements for the next loop, and so on. The complexity of this program
        is around O(N^2), since we have a nested for loop, with each looping through the entire array.
         */
        for (int i = list.length - 1; i > 0; i--){
            //"i" represents the number of times we loop through the array
            for (int j = 0; j < i; j++){
                //"j" represents the index of the element
                if (list[j] > list[j + 1]) swap(j, j + 1); //Swap if not in order
            }
        }
    }

    private void sort_method2(){
        /*
        General idea for this method:
        For each loop, we always compare the (constantly updated) first element with the next element,
        which is looped through as well. We compare each pair of elements, and we place the smaller
        element in the first slot. As a result, after each loop, the first element is guaranteed to
        be the smallest element of the entire list. For the second loop, the second element is the
        second smallest element, and so on. The complexity is also O(N^2).
         */
        for (int i = 0; i < list.length; i++){
            //"i" represents the first index
            for (int j = i + 1; j < list.length; j++){
                //"j" represents the second index
                if (list[i] > list[j]) swap(i, j); //Swap if not in order
            }
        }
    }

    private void sort_method3(int start, int end){
        /*
        General idea of this method:
        Split the entire array into smaller pieces, preferably splitting into half each time.
        After that, first sort each piece, then join them together. The complexity of this
        method is O(N log N), since splitting the entire array would result in log N sub-arrays.
        (log here means log base 2)
         */
        if (end - start > 1){
            //The size of the subarray passed in is still larger than 1
            int middle = (start + end) / 2; //Break down to two sections
            sort_method3(start, middle);
            sort_method3(middle, end);
            combineList(start, end);
            //After calling sort_method3 for both sections, we know that each section is already sorted.
            //As a result, we can combine the two sections with combineList.
        }
    }

    private void combineList(int start, int end){
        int length = end - start, middle = (start + end) / 2;
        int pointX = start, pointY = middle, cnt = 0;
        int[] tempList = new int[length]; //Reset the array
        while (pointX < middle && pointY < end){
            //Not all the elements are added to the sorted array
            if (list[pointX] <= list[pointY]) tempList[cnt++] = list[pointX++];
            else tempList[cnt++] = list[pointY++];
            //Takes the smaller element and adds it to the array
            //At the same time, update the array indices
        }
        //Clear up the leftover values
        while (pointX < middle) tempList[cnt++] = list[pointX++];
        while (pointY < end) tempList[cnt++] = list[pointY++];
        //Copy the sorted values to the original array
        for (int i = 0; i < length; i++) list[i + start] = tempList[i];
    }

    @Override
    public void setList(int[] list){
        this.list = list;
    }

    @Override
    public int[] getList(){
        return this.list;
    }

    @Override
    public void sort(int type){
        if (type == 1) sort_method1();
        if (type == 2) sort_method2();
        if (type == 3) sort_method3(0, list.length);
    }

    @Override
    public String toString(){
        String ans = "";
        for (int i: list) ans += i + " ";
        return ans;
    }
}
