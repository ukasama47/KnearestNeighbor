// 1211201118 林優花
//ファイル読み込みのためのimport
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.Math;
import java.util.*;

public class report_3_1211201118 {//////////////////////////////
	// double型2次元配列を表示する関数
	public static void show(double[][] A) {
		for (int i = 0; i < A.length; i++) {
			for (int j = 0; j < A[0].length; j++) {
				System.out.print(A[i][j] + ", ");
			}
			System.out.println();
		}
	}

	// String型1次元配列を表示する関数
	public static void show(String[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + ", ");
		}
		System.out.println();
	}

	// k近傍法
	// X, yを訓練データとし，入力xに対しk近傍法を実行し，予測結果を返す．
	public static String knn(double[][] X, String[] y, int k, double[] x) {
		// ここを適切に完成させる
    String answer = "0";
    double d = 0;
    
    int i_setosa = 0, i_versicolor = 0, i_virginica = 0;
    List <Double> distance = new ArrayList<Double>();
    List <String> name = new ArrayList<String>();
    
    for (int all = 0; all < X.length; all++) { //150-1
      for (int i = 0; i < X[0].length; i++) { //4
        d += Math.pow((X[all][i] - x[i]), 2); // ここだけ
      }
      distance.add(d);
      d = 0;
    }

    //短いものから順番に五個目を取得
    int count = 0;
    double nowmin = 0;
    double min;
    int minindex;
    
    while (count < 5) {
    min = Collections.min(distance);
    minindex = distance.indexOf(min);
    name.add(y[minindex]);
    if (nowmin != distance.get(minindex)) {
      nowmin = distance.get(minindex);
      count++;
    }
    distance.remove(minindex);
    }
    
    for (int index = 0; index < name.size(); index++) {
      if (name.get(index).equals("Iris-setosa")) {
        i_setosa++;
      } else if (name.get(index).equals("Iris-versicolor")) {
        i_versicolor++;
      } else {
        i_virginica++;
      }
    } 
      
    //全ての名前を取得　表示
    //多いもの判定
    //同着の場合はインデックスの小さいもの（setosa > versicolor > virginica）の順に返す
    if (i_setosa > count/2 || (i_setosa == i_versicolor && i_setosa > i_virginica) || (i_setosa == i_virginica && i_setosa > i_versicolor)) {
      answer = "Iris-setosa";
    } else if (i_versicolor > count/2 || i_versicolor == i_virginica && i_versicolor > i_setosa) {
      answer = "Iris-versicolor";
    } else if (i_virginica > count/2) {
      answer = "Iris-virginica";
    }
    System.out.print(answer + " : ");
    System.out.println(Arrays.toString(name.toArray()));
    return answer;
	}

	public static void main(String[] args) {
		// 変数設定
		int n = 150; // 対象数 n
		int m = 4; // 次元数 m
		double[][] X = new double[n][m]; // データ行列 X の宣言
		String[] y = new String[n]; // 教師ラベルの配列

		// データの読込
		try {
			File file = new File("iris.data"); // ファイルのパスを指定する

			// BufferedReaderクラスのreadLineメソッドを使って1行ずつ読み込み表示する
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			String row;

			// ここで X と y にデータを格納する (ここを適切に完成させる)
			// 何らかの処理
      int low = 0;
      int col;
      
			while ((row = bufferedReader.readLine()) != null) {
				// 何らかの処理
        //row = row.trim();
        String[] data = row.split(",");
        if (row.isEmpty()) {
          continue;
        }
        
        for (col = 0; col < m; col++) {   
          X[low][col] = Double.parseDouble(data[col]);
        } 
    
        for (col = m; col < m+1; col++) {
          y[low] = data[col];  
        }
        low++;
      }
      
			fileReader.close(); // ファイルを閉じてリソースを開放する
		} catch (IOException e) {
			e.printStackTrace();
		}

		// データ行列 X と正解ラベル y の表示
		show(X);
		show(y);

		// パラメータ k を定める．
		int k = 5;

		// 以下でleave-one-out交差検証法でk近傍法の性能を確認する．(ここを適切に完成させる)
    // ここでdataから一つ抜き出し、それ以外のデータで予測する。
    double collect = 0;
    double All = X.length;
    double[][] X_minus = new double[n-1][m]; 
    double[] x = new double[m];
		String[] y_minus = new String[n-1]; 
    
    for (int index = 0; index < All; index++) {
      int copylow = 0;
      for (int low = 0; low < X.length; low++) {
        if (low == index) {
          continue;
        }
        for (int col = 0; col < X[0].length; col++) {
          X_minus[copylow][col] = X[low][col]; 
        }
        copylow++;
      }
      int copycol = 0;
      for (int col = 0; col < y.length; col++) {
        if (col == index) {
          continue;
        }
        y_minus[copycol++] = y[col];
      } 
      for (int col = 0; col < X[0].length; col++) {
        x[col] = X[index][col];
      } 
      if(knn(X_minus, y_minus, k, x).equals(y[index])) {
        collect++;
      } 
    }
    System.out.println("Prediction Accuracy = " + collect / All);  
	}
}
