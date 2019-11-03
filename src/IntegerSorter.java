import java.io.File;
import java.util.Scanner;

public class IntegerSorter implements Sorter{

    private int[] list, tempList;

    private void sort_method1(){
        for (int i = list.length - 1; i >= 0; i--){
            for (int j = 0; j < i; j++){
                if (list[j] > list[j+1]){
                    int temp = list[j];
                    list[j] = list[j+1];
                    list[j+1] = temp;
                }
            }
        }
    }

    private void sort_method2(){
        for (int i = 0; i < list.length; i++){
            for (int j = i + 1; j < list.length; j++){
                if (list[i] > list[j]){
                    int temp = list[j];
                    list[j] = list[i];
                    list[i] = temp;
                }
            }
        }
    }

    private void sort_method3(int start, int end){
        if (end - start > 1){
            int middle = (start + end) / 2;
            sort_method3(start, middle); //Break down to two sections
            sort_method3(middle, end);
            combineList(start, end);
            //After calling sort_method3 for both sections, we know that each section is already sorted.
            //As a result, we can combine the two sections with combineList.
        }
    }

    private void combineList(int start, int end){
        int length = end - start, middle = (start + end) / 2;
        int pointX = start, pointY = middle, cnt = 0;
        tempList = new int[length]; //Reset the array
        while (pointX < middle && pointY < end){
            if (list[pointX] <= list[pointY]) tempList[cnt++] = list[pointX++];
            else tempList[cnt++] = list[pointY++];

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

    public static void main(String[] args) throws Exception{
        int size = new Scanner(System.in).nextInt();
        if (size < 4 || size > 21) return;
        int[] list = new int[(int)Math.pow(2, size)];
        Scanner SCAN = new Scanner(new File("2power"+ size + ".txt"));
        int temp = 0;
        while (SCAN.hasNext()) list[temp++] = SCAN.nextInt();
        IntegerSorter IS = new IntegerSorter();
        IS.setList(list);
        long start = System.nanoTime();
        IS.sort(2);
        long end = System.nanoTime();
        System.out.println("TIME: " + (end - start) / 1e9 + " seconds");
    }
}
