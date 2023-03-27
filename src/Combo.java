import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Combo {

    static BigInteger[] finalArr;
    static int num;
    static BigInteger finalAns = BigInteger.ZERO;

    public static void main(String[] args) {

        Scanner input  = new Scanner(System.in);
        System.out.println("Please enter the word");
        String str = input.nextLine();
        str = str.replace(" ", "");
        System.out.println("Please enter the length of the required words");
        num = input.nextInt();
        BigInteger[] arr = new BigInteger[128];
        for(int i=0;i<128;i++){
            arr[i] = BigInteger.ZERO;
        }
        for(int i=0;i<str.length();i++){
            arr[str.charAt(i)] = arr[str.charAt(i)].add(BigInteger.ONE);
        }
        Arrays.sort(arr, Collections.reverseOrder());
        int numOfElements = Arrays.asList(arr).indexOf(BigInteger.ZERO);

        BigInteger[] arr2 = new BigInteger[numOfElements];
        for(int i=0;i<numOfElements;i++){
            arr2[i] = arr[i];
        }

        finalArr = new BigInteger[arr2[0].intValue()+1];

        for(int i=1;i<=arr2[0].intValue();i++){
            int counter = 0;
            for(int j = 0;j<arr2.length;j++){
                if(arr2[j].compareTo(BigInteger.valueOf(i))!=-1){
                    counter++;
                }
            }
            finalArr[i]=BigInteger.valueOf(counter);
        }
        System.out.println("The output is in LaTeX. One may preview it on: https://www.latexlive.com/");
        System.out.println("\\begin{array}{l} ");
        generateArr(num,num,new ArrayList<Integer>());

        System.out.println("\\text{Ans}:"+finalAns);
        System.out.println("\\end{array}");
    }

    public static BigInteger factorial(BigInteger n){
        BigInteger ans = BigInteger.ONE;
        for (BigInteger i= n;i.compareTo(BigInteger.ZERO)==1;i=i.subtract(BigInteger.ONE)){
            ans = ans.multiply(i);
        }
        return ans;
    }

    public static BigInteger combo(BigInteger a, BigInteger b){
        return (factorial(a).divide(factorial(b))).divide(factorial(a.subtract(b)));
    }

    public static void generateArr(int max, int total, ArrayList<Integer> arr){
        if(max==1){
            arr.add(0,total);
            arr.add(0,0);
            finalAns = finalAns.add(printTeX(arr.stream().mapToInt(Integer::intValue).toArray()));
        }else{
            for(int i=0;i*max<=total;i++){
                ArrayList<Integer> arrTemp = (ArrayList<Integer>) arr.clone();
                arrTemp.add(0,i);
                generateArr(max-1,total-i*max,arrTemp);
            }
        }
    }

    public static BigInteger printTeX(int arr[]){
        BigInteger ans = BigInteger.ONE;
        String str = "\\text{Case} \\ ";
        for(int i = 1;i<arr.length;i++){
            for(int j = 0; j<arr[i];j++){
                str+=i+"-";
            }
        }
        str = str.substring(0,str.length()-1) +": ";
        int counter = 0;
        for(int i=arr.length-1;i>0;i--){
            if(arr[i]!=0){
                if(i>= finalArr.length){
                    return BigInteger.ZERO;
                } else {
                    if(arr[i]>finalArr[i].intValue()-counter){
                        return BigInteger.ZERO;
                    }else {
                        str+="{"+(finalArr[i].intValue()-counter)+"\\choose"+arr[i]+"}"+" \\times ";
                        ans = ans.multiply(combo(BigInteger.valueOf((finalArr[i].intValue()-counter)),BigInteger.valueOf(arr[i])));
                        counter+=arr[i];
                    }
                }
            }


        }
        int total =0;
        for(int i = 1;i<arr.length;i++){
            total+=arr[i]*i;
        }
        str += "\\frac{"+total+"!}{";
        ans = ans.multiply(factorial(BigInteger.valueOf(total)));
        for(int i = 1;i<arr.length;i++){
            for(int j = 0; j<arr[i];j++){
                str+=i+"!";
                ans = ans.divide(factorial(BigInteger.valueOf(i)));
            }
        }
        str += "}=";
        str += ans + "\\\\";
        System.out.println(str);
        return ans;
    }

}
