package com.stmu.android.stockhelper;

import java.util.Date;
import java.util.UUID;

/**
 * Created by drodr on 3/22/2017.
 */
public class Stock {

    String mTitle;
    int mId;
    String mPrice;
    String mPCT;
    String mName;
    String mDate;
    String mChange;
    String mOpen;
    String mClose;
    String mLow;
    String mHigh;
    String mFiftyTwoWeekLow;
    String mFiftyTwoWeekHigh;
    String mMktCap;
    String mVolume;
    String mOneYearTarget;
    String mAvgVol;
    String pe;
    String eps;

    public String getPe() {
        return pe;
    }
    public void setPe(String pe) {
        this.pe = pe;
    }

    public String getEps() {
        return eps;
    }
    public void setEps(String eps) {
        this.eps = eps;
    }

    public String getOneYearTarget() {
        return mOneYearTarget;
    }
    public void setOneYearTarget(String oneYearTarget) {
        mOneYearTarget = oneYearTarget;
    }

    public String getAvgVol() {
        return mAvgVol;
    }
    public void setAvgVol(String avgVol) {
        mAvgVol = avgVol;
    }

    public String getMktCap() {
        return mMktCap;
    }
    public void setMktCap(String mktCap) {
        mMktCap = mktCap;
    }

    public String getVolume() {
        return mVolume;
    }
    public void setVolume(String volume) {
        mVolume = volume;
    }

    public Stock(){
    }
    public Stock(int id){
        mId = id;
    }


    public void setTitle(String title) {
        mTitle = title;
    }
    public String getTitle() {
        return mTitle;
    }

    public void setID(int id) {
        mId = id;
    }
    public int getId() {
        return mId;
    }

    public void setPrice(String price){
        mPrice = price;
    }
    public String getPrice(){
        return mPrice;
    }

    public void setPCT(String pct){
        mPCT = pct;
    }
    public String getPCT(){
        return mPCT;
    }

    public void setName(String name){
        mName = name;
    }
    public String getName(){
        return mName;
    }

    public void setDate(String date){
        mDate = date.toString();
    }
    public String getDate(){
        return mDate;
    }

    public void setChange(String change){
        mChange = change;
    }
    public String getChange(){
        return mChange;
    }

    public String getOpen() {
        return mOpen;
    }
    public void setOpen(String open) {
        mOpen = open;
    }

    public String getLow() {
        return mLow;
    }
    public void setLow(String low) {
        mLow = low;
    }

    public String getClose() {
        return mClose;
    }
    public void setClose(String close) {
        mClose = close;
    }

    public String getHigh() {
        return mHigh;
    }
    public void setHigh(String high) {
        mHigh = high;
    }

    public String getFiftyTwoWeekLow() {
        return mFiftyTwoWeekLow;
    }
    public void setFiftyTwoWeekLow(String fiftyTwoWeekLow) {
        mFiftyTwoWeekLow = fiftyTwoWeekLow;
    }

    public String getFiftyTwoWeekHigh() {
        return mFiftyTwoWeekHigh;
    }
    public void setFiftyTwoWeekHigh(String fiftyTwoWeekHigh) {
        mFiftyTwoWeekHigh = fiftyTwoWeekHigh;
    }


}
