package com.progressoft.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;

public  class ScoringSummaryImpl implements ScoringSummary {


    public ArrayList<Double> getTarArr() {
        return tarArr;
    }

    public void setTarArr(ArrayList<Double> tarArr) {
        this.tarArr = tarArr;
    }

    private ArrayList<Double> tarArr = new ArrayList<Double>();


    @Override
    public BigDecimal mean() {

        int length = tarArr.size();
        double sum = 0.0;

        for(double num : tarArr) {
            sum += num;
        }
        BigDecimal mean = BigDecimal.valueOf(sum/length);

        mean = mean.setScale(0, RoundingMode.HALF_EVEN );
        mean = mean.setScale(2, RoundingMode.HALF_EVEN );

        return mean;
    }

    @Override
    public BigDecimal standardDeviation() {

        double sum = 0.0, standardDeviation = 0.0;
        int length = tarArr.size();

        for(double num : tarArr) {
            sum += num;
        }

        double mean = sum/length;

        for(double num: tarArr) {
            standardDeviation += Math.pow(num - mean, 2);
        }

        BigDecimal sd = BigDecimal.valueOf(Math.sqrt(standardDeviation/length));

        sd = sd.setScale(2, RoundingMode.HALF_EVEN );

        return sd;

    }

    @Override
    public BigDecimal variance() {

        double sum = 0.0,variance = 0;
        int length = tarArr.size();
        for(double num : tarArr) {
            sum += num;
        }
        double mean = sum/length;

        for (int i = 0; i < tarArr.size(); i++) {
            variance += Math.pow(tarArr.get(i) - mean, 2);
        }

        BigDecimal v = BigDecimal.valueOf((variance / length));

        v = v.setScale(0, RoundingMode.HALF_EVEN );
        v = v.setScale(2, RoundingMode.HALF_EVEN );

        return v;
    }

    @Override
    public BigDecimal median() {


        Collections.sort(tarArr);
        int length = tarArr.size();

        if (length % 2 != 0){
            BigDecimal med = BigDecimal.valueOf((double)tarArr.get(length / 2));
            med = med.setScale(2, RoundingMode.HALF_EVEN );
            return med;

        }

        BigDecimal med = BigDecimal.valueOf((double)(tarArr.get((length - 1) / 2) + tarArr.get(length / 2) / 2.0));
        med = med.setScale(2, RoundingMode.HALF_EVEN );
        return med;
    }

    @Override
    public BigDecimal min() {
        double minValue = tarArr.get(0);
        for(int i=1;i<tarArr.size();i++){
            if(tarArr.get(i) < minValue){
                minValue = tarArr.get(i);
            }
        }

        BigDecimal min = BigDecimal.valueOf(minValue);
        min = min.setScale(2, RoundingMode.HALF_EVEN );
        return min;
    }

    @Override
    public BigDecimal max() {
        double maxValue = tarArr.get(0);
        for(int i=1;i < tarArr.size();i++){
            if(tarArr.get(i) > maxValue){
                maxValue = tarArr.get(i);
            }
        }
        BigDecimal max = BigDecimal.valueOf(maxValue);
        max = max.setScale(2, RoundingMode.HALF_EVEN );
        return max;

    }
}
