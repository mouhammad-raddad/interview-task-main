package com.progressoft.tools;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NormalizerImpl implements Normalizer {
    private static final String COMMA_DELIMITER = ",";



    private final ScoringSummaryImpl summary = new ScoringSummaryImpl();


    @Override
    public ScoringSummary zscore(Path csvPath, Path destPath, String colToStandardize) {

        String path = String.valueOf(csvPath);
        String line = "";
        List<List<String>> lines = new ArrayList<>();

        try{
            BufferedReader br = new BufferedReader(new FileReader((path)));
            while ((line = br.readLine()) != null ){
                String[] values = line.split(",");
                lines.add(Arrays.asList(values));
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        int index = lines.get(0).indexOf(colToStandardize);

        ArrayList<Double> colArr = new ArrayList<Double>();

        for(int i = 1; i< lines.size();i++) {
            colArr.add(Double.valueOf(lines.get(i).get(index)));
        }

         ScoringSummaryImpl summary = new ScoringSummaryImpl();

         summary.setTarArr(colArr);

        BigDecimal mean = summary.mean();
        BigDecimal standardDeviation = summary.standardDeviation();

        ArrayList<String> zlist = new ArrayList<String>();

        BigDecimal zValue;
        BigDecimal zScore;

        zlist.add(colToStandardize+"_z");


        for(int i = 1; i< lines.size();i++) {
            zValue = BigDecimal.valueOf(Long.parseLong(lines.get(i).get(index)));
            zScore = BigDecimal.valueOf((zValue.doubleValue() - mean.doubleValue()) / standardDeviation.doubleValue());
            zlist.add(String.valueOf((zScore.setScale(2, RoundingMode.HALF_EVEN))));
        }


        ArrayList<String> result = new ArrayList<String>();



        for (int i = 0; i < lines.size(); i++)
        {
            lines.get(i).set(index+1,zlist.get(i));
        }


        String[][] stringArray = lines.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);

        System.out.println(Arrays.deepToString(stringArray));


        BufferedWriter br = null;
        try {
            br = new BufferedWriter(new FileWriter(String.valueOf(destPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();

        for (String[] strings : stringArray) {
            for (String string : strings) {
                sb.append(string);
                sb.append(",");
            }
                sb.append("\n");
        }
        try {
            assert br != null;
            br.write(String.valueOf(sb));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return summary;
    }

    @java.lang.Override
    public ScoringSummary minMaxScaling(Path csvPath, Path destPath, String colToNormalize) {


        String path = String.valueOf(csvPath);
        String line = "";
        List<List<String>> lines = new ArrayList<>();

        try{
            BufferedReader br = new BufferedReader(new FileReader((path)));
            while ((line = br.readLine()) != null ){
                String[] values = line.split(",");
                lines.add(Arrays.asList(values));
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        int index = lines.get(0).indexOf(colToNormalize);

        ArrayList<Double> colArr = new ArrayList<Double>();

        for(int i = 1; i< lines.size();i++) {
            colArr.add(Double.valueOf(lines.get(i).get(index)));
        }

        ScoringSummaryImpl summary = new ScoringSummaryImpl();

        summary.setTarArr(colArr);

        BigDecimal min = summary.min();
        BigDecimal max = summary.max();

        ArrayList<String> mmlist = new ArrayList<String>();

        BigDecimal mmValue;
        BigDecimal mmScore;

        mmlist.add(colToNormalize+"_mm");


        for(int i = 1; i< lines.size();i++) {
            mmValue = BigDecimal.valueOf(Long.parseLong(lines.get(i).get(index)));
            mmScore = BigDecimal.valueOf((mmValue.doubleValue() - min.doubleValue()) / (max.doubleValue()-min.doubleValue()));
            mmlist.add(String.valueOf((mmScore.setScale(2, RoundingMode.HALF_EVEN))));
        }


        ArrayList<String> result = new ArrayList<String>();



        for (int i = 0; i < lines.size(); i++)
        {

            lines.get(i).set(index+1,mmlist.get(i));
        }


        String[][] stringArray = lines.stream().map(u -> u.toArray(new String[0])).toArray(String[][]::new);

        System.out.println(Arrays.deepToString(stringArray));


        BufferedWriter br = null;
        try {
            br = new BufferedWriter(new FileWriter(String.valueOf(destPath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();

        for (String[] strings : stringArray) {
            for (String string : strings) {
                sb.append(string);
                sb.append(",");
            }
            sb.append("\n");
        }
        try {
            assert br != null;
            br.write(String.valueOf(sb));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return summary;
    }
}
