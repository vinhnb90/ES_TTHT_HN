package com.esolutions.esloginlib.lib;import android.content.Context;import android.support.v7.widget.AppCompatSpinner;import android.view.LayoutInflater;import android.view.View;import android.widget.ImageButton;import android.widget.ProgressBar;/** * Created by VinhNB on 10/26/2017. */public class DepartViewEntity {    private final Context context;    private final View viewLayout;    private final AppCompatSpinner spDvi;    private final ImageButton ibtnDownloadDvi;    private final ProgressBar pbarDownloadDvi;    private DepartViewEntity(Builder builder) throws Exception {        this.context = builder.context;        this.viewLayout = LayoutInflater.from(context).inflate(builder.viewLayoutID, null);        this.spDvi = (AppCompatSpinner) viewLayout.findViewById(builder.spDvi);        this.ibtnDownloadDvi = (ImageButton) viewLayout.findViewById(builder.ibtnDownloadDvi);        this.pbarDownloadDvi = (ProgressBar) viewLayout.findViewById(builder.pbarDownloadDvi);    }    public static class Builder {        private Context context;        private int viewLayoutID;        private int spDvi;        private int ibtnDownloadDvi;        private int pbarDownloadDvi;        public Builder(Context context, int viewLayoutID, int spDvi, int ibtnDownloadDvi, int pbarDownloadDvi) {            this.context = context;            this.viewLayoutID = viewLayoutID;            this.spDvi = spDvi;            this.ibtnDownloadDvi = ibtnDownloadDvi;            this.pbarDownloadDvi = pbarDownloadDvi;        }        public DepartViewEntity build() throws Exception {            return new DepartViewEntity(this);        }    }    public Context getContext() {        return context;    }    public View getViewLayout() {        return viewLayout;    }    public AppCompatSpinner getSpDvi() {        return spDvi;    }    public ImageButton getIbtnDownloadDvi() {        return ibtnDownloadDvi;    }    public ProgressBar getPbarDownloadDvi() {        return pbarDownloadDvi;    }}