import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

/**
 * Atribui as informacoes da base de dados aos elementos do tipo Deputities

*/

class Loader {

  private static String lastFile = "";
  private static int lastNOLines = 0;

  /**
   * Dada a base de dados e o numero n de elementos necessario, carrega aleatoriamente
   * um vetor de tamanho n de elementos Deputities
   */

  public static Deputies[] loadRandomList(String filename, int n){
    Deputies[] dep = new Deputies[n];
    LinkedList<Integer> index = new LinkedList<>();
    int nLines;
    if(Loader.lastFile.equals(filename)){
      System.out.println("[+] Using number of lines from last run.");
      nLines = Loader.lastNOLines;
    } else {
      System.out.println("[+] Searching for number of lines...");
      nLines = Loader.numberOfLines(filename);
      Loader.lastFile = filename;
      Loader.lastNOLines = nLines;
    }
    System.out.println("[+] Filling array of indexes...");
    int[] indexpick = new int[nLines];
    for(int i = 1; i <= nLines; i++){
      indexpick[i-1] = i;
    }
    System.out.println("[+] Picking indexes...");
    Random r = new Random();
    int max = nLines-1;
    for(int i = 0; i < n; i++){
      int indexpicked = r.nextInt(max);
      int aux = indexpick[indexpicked];
      indexpick[indexpicked] = indexpick[max];
      indexpick[max] = aux;
      index.add(indexpick[max]);
      max--;
    }
    System.out.println("[+] Sorting array of indexes...");
    Collections.sort(index);
    int picked = 0;
    try {
      BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
      String readLine, read;
      ArrayList<Deputies> depAL = new ArrayList<>();
      int id = 0;
      System.out.println("[+] Loading data...");
      read = br.readLine();
      for(int i = 0; i < n; i++){
        //skipping lines -- readLine = br.readLine();
        int skip;
        if(i != 0) skip = index.get(i)-index.get(i-1)-1;
        else skip = index.get(i)-1;
        for(int j = 0; j < skip; j++){
          br.readLine();
          id++;
        }
        readLine = br.readLine();
        id++;
        //...

        read = Loader.removeComma(readLine);
        if(read.split(",").length != 10){
          System.out.println("[!] Warning: Entry out of label. Skipping...");
          depAL.add(new Deputies(id,"null",0.0)); picked++;
        } else {
          String[] split = read.split(",");
          depAL.add(new Deputies(id, split[1], Integer.parseInt(split[2]), split[3], split[4], split[5], Double.parseDouble(split[9])));
          picked++;
        }
      }

      System.out.println("[+] Shuffling objects...");
      Collections.shuffle(depAL);
      dep = depAL.toArray(dep);

      br.close();
    } catch(IOException e) {
      System.out.println("[!] Error. Shutting down...");
      e.printStackTrace();
    }

    System.out.println("[+] "+picked+" objects loaded.");
    return dep;
  }


  /**
   * Dada a base de dados, retorna um vetor de elementos Deputities obedecendo a
   * ordem em que foram registradas no arquivo
   */

  public static Deputies[] loadLinearList(String filename){
    int nLines;
    if(Loader.lastFile.equals(filename)){
      System.out.println("[+] Using number of lines from last run.");
      nLines = Loader.lastNOLines;
    } else {
      System.out.println("[+] Searching for number of lines...");
      nLines = Loader.numberOfLines(filename);
    }
    Deputies[] dep = new Deputies[nLines];
    int picked = 0;
    try {
      BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
      String readLine, read;
      ArrayList<Deputies> depAL = new ArrayList<>();
      int id = 0;
      System.out.println("[+] Loading data...");
      read = br.readLine();
      for(int i = 0; i < nLines; i++){
        readLine = br.readLine();
        id++;

        read = Loader.removeComma(readLine);
        if(read.split(",").length != 10){
          System.out.println("[!] Warning: Entry out of label. Skipping...");
          depAL.add(new Deputies(id,"null",10000)); picked++;
        } else {
          String[] split = read.split(",");
          depAL.add(new Deputies(id, split[1], Integer.parseInt(split[2]), split[3], split[4], split[5], Double.parseDouble(split[9])));
          picked++;
        }
      }

      dep = depAL.toArray(dep);

      br.close();
    } catch(IOException e) {
      System.out.println("[!] Error. Shutting down...");
      e.printStackTrace();
    }

    System.out.println("[+] "+picked+" objects loaded.");
    return dep;
  }

  /**
   * Dada uma base de dados, retorna a quantidade de linhas do arquivo
   */

  public static int numberOfLines(String filename){
    int count = 0;
    try {
      BufferedReader br = new BufferedReader(new FileReader(new File(filename)));
      String readLine, read;
      read = br.readLine();
      while(br.readLine() != null){
        count++;
      }
      br.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
    return count;
  }


  /**
   * Dada uma linha do arquivo, remove as virgulas que separam as diferentes informacoes
   * a respeito do Deputado. Virgulas que por ventura sejam parte de uma das informacoes
   * serao mantidas

   */
  private static String removeComma(String line){
    boolean remove = false;
    int index = line.indexOf("\"");
    int lastIndex = line.lastIndexOf("\"");
    if(index == -1 || lastIndex == -1){
      return line;
    }
    char[] array = line.toCharArray();
    for(int i = index; i <= lastIndex; i++){
      if(remove && (array[i] == ',')){
        array[i] = ' ';
      } else if(array[i] == '\"'){
        remove = !remove;
      }
    }
    return new String(array);
  }

  public static int getLastNOLines(){
    return Loader.lastNOLines;
  }
}
